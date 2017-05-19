package com.toolsclass.chenjun.general.generalcj.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.adapter.FindPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/17 10:07
 * QQ：200622550
 */
public class MainFragment extends BaseFragment {

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
        titles.add("找书");
        titles.add("找电影");
        titles.add("即将上映");
        titles.add("正在热映");
        titles.add("Top250");

        FindPagerAdapter viewPagerAdapter = new FindPagerAdapter(getMContext(), titles, getChildFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

//        mTabLayout.setVisibility(View.GONE);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText("读书"));
        mTabLayout.addTab(mTabLayout.newTab().setText("电影"));
        mTabLayout.addTab(mTabLayout.newTab().setText("即将上映"));
        mTabLayout.addTab(mTabLayout.newTab().setText("正在热映"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Top250"));
        mTabLayout.setupWithViewPager(mViewPager);

        dynamicAddSkinView(mTabLayout, "tabIndicatorColor", R.color.colorAccent); // 导航横线颜色
    }
}
