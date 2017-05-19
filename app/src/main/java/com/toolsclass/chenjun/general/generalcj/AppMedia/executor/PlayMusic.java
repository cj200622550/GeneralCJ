package com.toolsclass.chenjun.general.generalcj.AppMedia.executor;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.toolsclass.chenjun.general.generalcj.AppMedia.model.Music;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.NetworkUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.Preferences;
import com.toolsclass.chenjun.general.generalcj.R;


/**
 * Created by hzwangchenyan on 2017/1/20.
 */
public abstract class PlayMusic implements IExecutor<Music> {
    private Activity mActivity;
    protected Music music;
    private int mTotalStep;
    protected int mCounter = 0;

    public PlayMusic(Activity activity, int totalStep) {
        mActivity = activity;
        mTotalStep = totalStep;
    }

    @Override
    public void execute() {
//        checkNetwork(); // 检测网络
    }

    private void checkNetwork() {
        boolean mobileNetworkPlay = Preferences.enableMobileNetworkPlay();
        if (NetworkUtils.isActiveNetworkMobile(mActivity) && !mobileNetworkPlay) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(R.string.media_tips);
            builder.setMessage(R.string.media_play_tips);
            builder.setPositiveButton(R.string.media_play_tips_sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Preferences.saveMobileNetworkPlay(true);
                    getPlayInfoWrapper();
                }
            });
            builder.setNegativeButton(R.string.media_cancel, null);
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            getPlayInfoWrapper();
        }
    }

    private void getPlayInfoWrapper() {
        onPrepare();
        getPlayInfo();
    }

    protected abstract void getPlayInfo();

    protected void checkCounter() {
        mCounter++;
        if (mCounter == mTotalStep) {
            onExecuteSuccess(music);
        }
    }
}
