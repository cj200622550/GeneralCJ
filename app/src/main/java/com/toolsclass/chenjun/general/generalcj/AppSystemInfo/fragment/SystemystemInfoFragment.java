package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.adapter.SystemInfoPagerAdapter;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.adapter.FindPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/18 10:07
 * QQ：200622550
 */

public class SystemystemInfoFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void setUpView() {
        mTabLayout = $(R.id.sliding_tabs);
        mViewPager = $(R.id.viewpager);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string._string_setting_system_info_cpu_check));
        titles.add(getString(R.string._string_setting_system_net_check));
        titles.add(getString(R.string._string_setting_system_info_service_check));
        titles.add(getString(R.string._string_setting_system_info_service_hardware));

        SystemInfoPagerAdapter viewPagerAdapter = new SystemInfoPagerAdapter(getMContext(), titles, getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string._string_setting_system_info_cpu_check)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string._string_setting_system_net_check)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string._string_setting_system_info_service_check)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string._string_setting_system_info_service_hardware)));
        mTabLayout.setupWithViewPager(mViewPager);

        dynamicAddSkinView(mTabLayout, "tabIndicatorColor", R.color.colorAccent); // 导航横线颜色
    }
}
