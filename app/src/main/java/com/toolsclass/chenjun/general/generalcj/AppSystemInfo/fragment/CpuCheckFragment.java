package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.fragment;

import android.view.View;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Setting.Utility.CpuUtility;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/18 10:20
 * QQ：200622550
 */

public class CpuCheckFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_cpu_model;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.system_info_fragment_cup_check;
    }

    @Override
    protected void init() {
        super.init();
        tv_cpu_model = $(R.id.tv_cpu_model);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        tv_cpu_model.setText("CPU类型：" + CpuUtility.getCpuString());
    }

    @Override
    public void onClick(View view) {

    }
}
