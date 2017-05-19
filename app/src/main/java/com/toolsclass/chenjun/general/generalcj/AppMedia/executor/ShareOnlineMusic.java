package com.toolsclass.chenjun.general.generalcj.AppMedia.executor;

import android.content.Context;
import android.content.Intent;

import com.toolsclass.chenjun.general.generalcj.AppMedia.http.HttpCallback;
import com.toolsclass.chenjun.general.generalcj.AppMedia.http.HttpClient;
import com.toolsclass.chenjun.general.generalcj.AppMedia.model.DownloadInfo;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.ToastUtils;
import com.toolsclass.chenjun.general.generalcj.R;

/**
 * 分享在线歌曲
 * Created by hzwangchenyan on 2016/1/13.
 */
public abstract class ShareOnlineMusic implements IExecutor<Void> {
    private Context mContext;
    private String mTitle;
    private String mSongId;

    public ShareOnlineMusic(Context context, String title, String songId) {
        mContext = context;
        mTitle = title;
        mSongId = songId;
    }

    @Override
    public void execute() {
        onPrepare();
        share();
    }

    private void share() {
        // 获取歌曲播放链接
        HttpClient.getMusicDownloadInfo(mSongId, new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null) {
                    onFail(null);
                    return;
                }

                onExecuteSuccess(null);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.media_share_music, mContext.getString(R.string.app_name),
                        mTitle, response.getBitrate().getFile_link()));
                mContext.startActivity(Intent.createChooser(intent, mContext.getString(R.string.media_share)));
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
                ToastUtils.show(R.string.media_unable_to_share);
            }
        });
    }
}
