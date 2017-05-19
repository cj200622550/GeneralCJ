package com.toolsclass.chenjun.general.generalcj.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.toolsclass.chenjun.general.generalcj.fragment.BookFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieHitFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieSoonFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MovieTopFragment;

import java.util.List;

import ren.solid.library.utils.ViewUtils;

/**
 * 作者：陈骏 on 2017/5/15 10:16
 * QQ：200622550
 */
public class FindPagerAdapter extends FragmentPagerAdapter {


    private final List<String> mTitleList;
    private final Context mContext;

    public FindPagerAdapter(Context context, List<String> titles, FragmentManager fm) {
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

        Fragment result = new BookFragment();//这里主要是一个防止没有找到Fragment，给一个默认
        if (title.equals("找书籍")) {
            result = ViewUtils.createFragment(BookFragment.class,false);
        } else if (title.equals("找电影")) {
            result = ViewUtils.createFragment(MovieFragment.class,false);
        } else if (title.equals("即将上映")) {
            result = ViewUtils.createFragment(MovieSoonFragment.class,false);
        } else if (title.equals("正在热映")) {
            result = ViewUtils.createFragment(MovieHitFragment.class,false);
        } else if (title.equals("Top250")) {
            result = ViewUtils.createFragment(MovieTopFragment.class,false);
        }
        return result;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
