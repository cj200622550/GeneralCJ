package com.toolsclass.chenjun.general.generalcj.Setting.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.FileUtils;
import com.toolsclass.chenjun.general.generalcj.Config.ShareConfig;
import com.toolsclass.chenjun.general.generalcj.Libs.sharelibrary.Share;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.UtilityActivity.MainActivity;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;
import com.toolsclass.chenjun.general.generalcj.view.UISwitchButton;

import ren.solid.library.fragment.base.BaseFragment;

import static com.toolsclass.chenjun.general.generalcj.Config.ShareConfig.SHARE_MOBILE_SCEN_FILE_SIZE;
import static com.toolsclass.chenjun.general.generalcj.R.id.ll_mobile_file_size;

/**
 * 作者：陈骏 on 2017/5/17 10:36
 * QQ：200622550
 */

public class MediaFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_mobile_network_play;
    private UISwitchButton switch_mobile_network_play;
    private TextView tv_mobile_network_download;
    private UISwitchButton switch_mobile_network_download;
    private TextView tv_msg;
    private CardView cv_mobile_file_size;
    private TextView tv_mobile_file_size;
    private boolean isMobileNetworkPlay = false;
    private boolean isMobileNetworkDownload = false;
    private int MOBILE_SCEN_FILE_SIZE = 300;

    @Override
    public void onClick(View view) {
        LogUtils.e("点击了");
        switch (view.getId()) {
            case R.id.cv_mobile_file_size:
                showListAlertDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.setting_fragment_media;
    }

    @Override
    protected void init() { // 初始化数据
        super.init();
        isMobileNetworkPlay = Share.getBoolean(ShareConfig.SHARE_MOBILE_NETWORK_PLAY, false); // 查看是否选中网络播放
        isMobileNetworkDownload = Share.getBoolean(ShareConfig.SHARE_MOBILE_NETWORK_DOWNLOAD, false); // 查看是否选中网络下载
        MOBILE_SCEN_FILE_SIZE = Share.getInt(SHARE_MOBILE_SCEN_FILE_SIZE, 300); // 查看是否选中网络下载
    }

    @Override
    protected void setUpView() { // 初始化ID
        super.setUpView();
        tv_mobile_network_play = $(R.id.tv_mobile_network_play);
        switch_mobile_network_play = $(R.id.switch_mobile_network_play);
        tv_mobile_network_download = $(R.id.tv_mobile_network_download);
        switch_mobile_network_download = $(R.id.switch_mobile_network_download);
        tv_msg = $(R.id.tv_msg);
        cv_mobile_file_size = $(R.id.cv_mobile_file_size);
        tv_mobile_file_size = $(R.id.tv_mobile_file_size);

        cv_mobile_file_size.setOnClickListener(this);
    }

    @Override
    protected void setUpData() { // 数据操作
        super.setUpData();
        initSwitch();
        switch_mobile_network_play.setOnCheckedChangeListener(mobileNetworkPlay);
        switch_mobile_network_download.setOnCheckedChangeListener(mobileNetworkDownload);
        tv_msg.setText("下载路径：" + FileUtils.getMusicDir() + "\n" +
                "歌词路径：" + FileUtils.getLrcDir() + "\n" +
                "音乐来源：百度音乐" + "\n");
        tv_mobile_file_size.setText(MOBILE_SCEN_FILE_SIZE + "KB");
    }

    private void initSwitch() { // 初始化开关
        switch_mobile_network_play.setChecked(isMobileNetworkPlay);
        switch_mobile_network_download.setChecked(isMobileNetworkDownload);
        if (isMobileNetworkPlay)
            tv_mobile_network_play.setText(getString(R.string.media_mobile_network_play));
        else
            tv_mobile_network_play.setText(getString(R.string.media_no_mobile_network_play));
        if (isMobileNetworkDownload)
            tv_mobile_network_download.setText(getString(R.string.media_ok_mobile_network_download));
        else
            tv_mobile_network_download.setText(getString(R.string.media_no_mobile_network_download));
    }

    /**
     * 是否使用移动网络播放
     */
    CompoundButton.OnCheckedChangeListener mobileNetworkPlay = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                Share.putBoolean(ShareConfig.SHARE_MOBILE_NETWORK_PLAY, true); // 打开
                tv_mobile_network_play.setText(getString(R.string.media_mobile_network_play));
            } else {
                Share.putBoolean(ShareConfig.SHARE_MOBILE_NETWORK_PLAY, false); // 关闭
                tv_mobile_network_play.setText(getString(R.string.media_no_mobile_network_play));
            }
        }
    };
    /**
     * 是否使用移动网络下载
     */
    CompoundButton.OnCheckedChangeListener mobileNetworkDownload = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                Share.putBoolean(ShareConfig.SHARE_MOBILE_NETWORK_DOWNLOAD, true); // 打开
                tv_mobile_network_download.setText(getString(R.string.media_ok_mobile_network_download));
            } else {
                Share.putBoolean(ShareConfig.SHARE_MOBILE_NETWORK_DOWNLOAD, false); // 关闭
                tv_mobile_network_download.setText(getString(R.string.media_no_mobile_network_download));
            }
        }
    };

    // 信息列表提示框
    private AlertDialog alertDialog1;

    /**显示单选框*/
    private void showListAlertDialog() {
        final String[] itme = new String[]{"300KB", "800KB", "1M", "2M"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getMContext());
        alertBuilder.setTitle("选择大小");
        alertBuilder.setItems(itme, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int index) {
                switch (index){
                    case 0:
                        Share.putInt(SHARE_MOBILE_SCEN_FILE_SIZE, 300);
                        break;
                    case 1:
                        Share.putInt(SHARE_MOBILE_SCEN_FILE_SIZE, 800);
                        break;
                    case 2:
                        Share.putInt(SHARE_MOBILE_SCEN_FILE_SIZE, 1024);
                        break;
                    case 3:
                        Share.putInt(SHARE_MOBILE_SCEN_FILE_SIZE, 2048);
                        break;
                    default:
                        Share.putInt(SHARE_MOBILE_SCEN_FILE_SIZE, 300);
                        break;
                }
                tv_mobile_file_size.setText(itme[index]);
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }
}
