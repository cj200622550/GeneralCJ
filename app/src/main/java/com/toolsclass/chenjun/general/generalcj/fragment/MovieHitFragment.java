package com.toolsclass.chenjun.general.generalcj.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.adapter.MovieAdapter;
import com.toolsclass.chenjun.general.generalcj.bean.MovieBean;
import com.toolsclass.chenjun.general.generalcj.constants.Apis;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;
import com.toolsclass.chenjun.general.generalcj.solid.library.http.HttpClientManager;
import com.toolsclass.chenjun.general.generalcj.solid.library.http.callback.adapter.JsonHttpCallBack;
import com.toolsclass.chenjun.general.generalcj.solid.library.utils.ToastUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/15 17:04
 * QQ：200622550
 * 正在热映
 */

public class MovieHitFragment extends BaseFragment implements View.OnClickListener {
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private int mPageSize = 20; // 每页显示20条数据
    private int mCurrentPageIndex = 1;

    private XRecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private FloatingActionButton mFABSearch;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        mFABSearch = $(R.id.fab_search);
        mFABSearch.setVisibility(View.GONE);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = $(R.id.recyclerview);
        mMovieAdapter = new MovieAdapter(getMContext(), new ArrayList<MovieBean>());
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mFABSearch.setOnClickListener(this);
        dynamicAddSkinView(mFABSearch, "backgroundTint", R.color.colorAccent);
    }
    @Override
    protected void setUpData() {
        mRecyclerView.setRefreshing(true);

    }

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }

    private void getData() {
        String reqUrl = Apis.MovieTheatersApi + "?start=" + (mCurrentPageIndex - 1) * mPageSize + "&count=" + mPageSize;

        HttpClientManager.getData(reqUrl, new JsonHttpCallBack<List<MovieBean>>() {
            @Override
            public Type getType() {
                return new TypeToken<List<MovieBean>>() {
                }.getType();
            }

            @Override
            public String getDataName() {
                return "subjects";
            }

            @Override
            public void onSuccess(List<MovieBean> result) {
                LogUtils.i(result.toString());
                mMovieAdapter.addAll(result);
                loadComplete();
            }

            @Override
            public void onError(Exception e) {
                LogUtils.e("onError:" + e);
//                ToastUtils.getInstance().showToast(e.getMessage());
                loadComplete();
            }
        });
    }

    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
                mMovieAdapter.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
