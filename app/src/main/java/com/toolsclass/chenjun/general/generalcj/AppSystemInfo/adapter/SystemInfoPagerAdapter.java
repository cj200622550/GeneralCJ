package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.fragment.CpuCheckFragment;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.fragment.NetCheckFragment;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.fragment.BookFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieHitFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieSoonFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieTopFragment;

import java.util.List;

import ren.solid.library.utils.ViewUtils;

/**
 * 作者：陈骏 on 2017/5/18 10:16
 * QQ：200622550
 */

public class SystemInfoPagerAdapter extends FragmentPagerAdapter {
    private final List<String> mTitleList;
    private final Context mContext;

    public SystemInfoPagerAdapter(Context context, List<String> titles, FragmentManager fm) {
        super(fm);
        mContext = context;
        mTitleList = titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = createFragmentByTitle(mTitleList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    private Fragment createFragmentByTitle(String title) {
        Fragment result = new CpuCheckFragment();//这里主要是一个防止没有找到Fragment，给一个默认
        if (title.equals(mContext.getString(R.string._string_setting_system_info_cpu_check))) {
            result = ViewUtils.createFragment(CpuCheckFragment.class,false);
        } else if (title.equals(mContext.getString(R.string._string_setting_system_net_check))) {
            result = ViewUtils.createFragment(NetCheckFragment.class,false);
        } else if (title.equals(mContext.getString(R.string._string_setting_system_info_service_check))) {
            result = ViewUtils.createFragment(MovieSoonFragment.class,false);
        } else if (title.equals(mContext.getString(R.string._string_setting_system_info_service_hardware))) {
            result = ViewUtils.createFragment(MovieHitFragment.class, false);
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
