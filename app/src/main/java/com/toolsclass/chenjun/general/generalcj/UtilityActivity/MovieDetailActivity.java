package com.toolsclass.chenjun.general.generalcj.UtilityActivity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Utility.MovieUtility;
import com.toolsclass.chenjun.general.generalcj.adapter.MovieInfoPageAdapter;
import com.toolsclass.chenjun.general.generalcj.bean.MovieDetailBean;
import com.toolsclass.chenjun.general.generalcj.constants.Apis;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;
import com.toolsclass.chenjun.general.generalcj.solid.library.http.HttpClientManager;
import com.toolsclass.chenjun.general.generalcj.solid.library.http.callback.adapter.JsonHttpCallBack;

import java.lang.reflect.Type;

import ren.solid.library.activity.base.BaseActivity;
import ren.solid.library.http.ImageLoader;
import ren.solid.library.http.request.ImageRequest;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MovieDetailActivity  extends BaseActivity {
    private String mId; // 获取电影条目ID
    private Toolbar mToolbar; // 标题
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mIvMovie;// 图片背景

    private TextView mTvTitle;// 标题
    private TextView mTvMsg;// 副标题
    private TextView mTvRating; // 评分
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private MovieDetailBean mMovieBean;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void init() {
        mId = getIntent().getStringExtra("id");
    }

    @Override
    protected void setUpView() {
        //设置Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        mToolbar.setNavigationIcon(R.drawable.ic_back); // 坐上角图标
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() { // 点击左上角图标事件
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mCollapsingToolbarLayout = $(R.id.collapsing_toolbar_layout); // 头部控件
        mIvMovie = $(R.id.iv_movie_image); // 图片背景
        mTvTitle = $(R.id.tv_title); // 标题
        mTvMsg = $(R.id.tv_msg); // 副标题
        mTvRating = $(R.id.tv_rating); // 评分
        mViewPager = $(R.id.viewpager);
        mTabLayout = $(R.id.sliding_tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("视频信息"));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#6d4c41"));
        dynamicAddSkinEnableView(mTabLayout, "tabIndicatorColor", R.color.colorAccent);
        dynamicAddSkinEnableView(mCollapsingToolbarLayout, "contentScrimColor", R.color.colorPrimary);
    }

    @Override
    protected void setUpData() {
        HttpClientManager.getData(Apis.MovieDetailApi + mId, new JsonHttpCallBack<MovieDetailBean>() {

            @Override
            public void onSuccess(MovieDetailBean result) {
                mMovieBean = result;
                LogUtils.i(mMovieBean.toString());

                mCollapsingToolbarLayout.setTitle(mMovieBean.getTitle());
                mTvRating.setText(mMovieBean.getRating().getAverage() + "分");
                mTvTitle.setText(mMovieBean.getTitle());
                mTvMsg.setText(MovieUtility.getDetailCasts(mMovieBean.getCasts(),"/"));

                ImageRequest imageRequest = new ImageRequest.Builder().imgView(mIvMovie).url(mMovieBean.getImages().getLarge()).create();
                ImageLoader.getProvider().loadImage(imageRequest);

                MovieInfoPageAdapter adapter = new MovieInfoPageAdapter(MovieDetailActivity.this, result, getSupportFragmentManager());
                mViewPager.setAdapter(adapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public DataType getDataType() {
                return DataType.OBJECT;
            }

            @Override
            public Type getType() {
                return MovieDetailBean.class;
            }
        });
    }
}
