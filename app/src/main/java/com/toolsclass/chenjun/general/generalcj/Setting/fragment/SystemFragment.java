package com.toolsclass.chenjun.general.generalcj.Setting.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.Config.ShareConfig;
import com.toolsclass.chenjun.general.generalcj.Libs.sharelibrary.Share;
import com.toolsclass.chenjun.general.generalcj.R;

import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.ToastUtils;

/**
 * 作者：陈骏 on 2017/5/17 10:30
 * QQ：200622550
 */

public class SystemFragment extends BaseFragment {
    private TextView tv_system_check_time; // 设置系统信息-网络检测刷新时间 设置显示时间
    private CardView cv_system_check_time; // 设置系统信息-网络检测刷新时间 设置显示时间
    private EditText EdSystemCheckTime;// 设置系统信息-网络检测刷新时间 弹出编辑框
    private String TvSystemCheckTime = ""; // 设置系统信息-网络检测刷新时间 输入关键字
    private AlertDialog mInputDialogSystemCheckTime; // 设置系统信息-网络检测刷新时间 弹出查找窗口

    @Override
    protected int setLayoutResourceID() { // 初始化布局
        return R.layout.setting_fragment_system;
    }

    @Override
    protected void setUpView() { // 初始化id
        tv_system_check_time = $(R.id.tv_system_check_time);
        cv_system_check_time = $(R.id.cv_system_check_time);
        cv_system_check_time.setOnClickListener(checkTimeClick);
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        initCheckTimeInputDialog();
        tv_system_check_time.setText(Share.getInt(ShareConfig.SHARE_SYSTEM_CHECK_TIME, 1000) + " 毫秒");

    }

    View.OnClickListener checkTimeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EdSystemCheckTime.setHint(Share.getInt(ShareConfig.SHARE_SYSTEM_CHECK_TIME,1000) + " 毫秒");
            mInputDialogSystemCheckTime.show();
        }
    };

    /***
     * 初始化设置系统信息-网络检测刷新时间输入对框框
     */
    private void initCheckTimeInputDialog() {
        EdSystemCheckTime = new EditText(getMContext());
        EdSystemCheckTime.setTextColor(getResources().getColor(R.color.colorPrimary));
        EdSystemCheckTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(getMContext());
        builder.setTitle("请输入时间 单位毫秒ms");
        builder.setView(EdSystemCheckTime);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TvSystemCheckTime = EdSystemCheckTime.getText().toString();
                if (TvSystemCheckTime.isEmpty() || Integer.valueOf(TvSystemCheckTime) <= 500 ) {//如果用户输入的关键字为空，我们就按照最开始的数据加载方式加载
                    ToastUtils.getInstance().showToast("输入不能为空或者小于等于500");
                } else {
                    Share.putInt(ShareConfig.SHARE_SYSTEM_CHECK_TIME,Integer.valueOf(TvSystemCheckTime));
                    tv_system_check_time.setText(TvSystemCheckTime + " 毫秒");
                }
                EdSystemCheckTime.setText("");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EdSystemCheckTime.setText("");
            }
        });
        mInputDialogSystemCheckTime = builder.create();
    }
}
