package com.toolsclass.chenjun.general.generalcj.Setting.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Setting.fragment.MediaFragment;
import com.toolsclass.chenjun.general.generalcj.Setting.fragment.SystemFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.BookFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieHitFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieSoonFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieTopFragment;

import java.util.List;

import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:48
 */
public class SettingPagerAdapter extends FragmentPagerAdapter {


    private final List<String> mTitleList;
    private final Context mContext;

    public SettingPagerAdapter(Context context, List<String> titles, FragmentManager fm) {
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

        Fragment result = new SystemFragment();//这里主要是一个防止没有找到Fragment，给一个默认
        if (title.equals(mContext.getString(R.string._string_setting_system))) {
            result = ViewUtils.createFragment(SystemFragment.class,false); // 进入到系统设置界面
        } else if (title.equals(mContext.getString(R.string._string_setting_media))) {
            result = ViewUtils.createFragment(MediaFragment.class,false); // 进入到音乐设置界面
        }
        return result;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
