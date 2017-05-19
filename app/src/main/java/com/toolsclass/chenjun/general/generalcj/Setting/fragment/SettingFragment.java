package com.toolsclass.chenjun.general.generalcj.Setting.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Setting.adapter.SettingPagerAdapter;
import com.toolsclass.chenjun.general.generalcj.adapter.FindPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/17 10:08
 * QQ：200622550
 */

public class SettingFragment extends BaseFragment {

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
        titles.add(getString(R.string._string_setting_system));
        titles.add(getString(R.string._string_setting_media));

        SettingPagerAdapter viewPagerAdapter = new SettingPagerAdapter(getMContext(), titles, getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string._string_setting_system)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string._string_setting_media)));
        mTabLayout.setupWithViewPager(mViewPager);

        dynamicAddSkinView(mTabLayout, "tabIndicatorColor", R.color.colorAccent); // 导航横线颜色
    }

}
