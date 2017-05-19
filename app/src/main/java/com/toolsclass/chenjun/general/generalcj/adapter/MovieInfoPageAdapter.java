package com.toolsclass.chenjun.general.generalcj.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.toolsclass.chenjun.general.generalcj.Utility.MovieUtility;
import com.toolsclass.chenjun.general.generalcj.bean.MovieDetailBean;
import com.toolsclass.chenjun.general.generalcj.solid.library.fragment.StringFragment;
import com.toolsclass.chenjun.general.generalcj.solid.library.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：陈骏 on 2017/5/15 16:24
 * QQ：200622550
 */

public class MovieInfoPageAdapter extends FragmentPagerAdapter {
    private final List<String> mTitleList;
    private final Context mContext;
    private MovieDetailBean mMovieBean;

    public MovieInfoPageAdapter(Context context, MovieDetailBean movieBean, FragmentManager fm) {
        super(fm);
        mContext = context;
        mMovieBean = movieBean;
        mTitleList = new ArrayList<>();
        mTitleList.add("视频信息");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(StringFragment.class, false);
        Bundle bundle = new Bundle();
        if (getPageTitle(position).equals("视频信息")) {
            bundle.putString("text", "中文名：" + mMovieBean.getTitle() + "\n" +
                    "原名：" + mMovieBean.getOriginal_title() + "\n" +
                    "又名：" + MovieUtility.StringTo(mMovieBean.getAka(),"/") + "\n" +
                    "制片国家/地区：" + MovieUtility.StringTo(mMovieBean.getCountries(),"/") + "\n" +
                    "影片类型：" + MovieUtility.StringTo(mMovieBean.getGenres(),"/") + "\n" +
                    "导演：" + MovieUtility.getDetailDirector(mMovieBean.getDirectors(),"/") + "\n" +
                    "主演：" + MovieUtility.getDetailCasts(mMovieBean.getCasts(),"/") + "\n" +
                    "年代：" + mMovieBean.getYear() + "年\n" +
                    "中文名：" + mMovieBean.getTitle() + "\n" +
                    "简介：" + mMovieBean.getSummary());
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
