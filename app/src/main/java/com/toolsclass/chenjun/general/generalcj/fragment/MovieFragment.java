package com.toolsclass.chenjun.general.generalcj.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;

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
import java.util.Random;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:11:54
 */
public class MovieFragment extends BaseFragment implements View.OnClickListener {
    private static final int ACTION_REFRESH = 1;
    private static final int ACTION_LOAD_MORE = 2;
    private int mCurrentAction = ACTION_REFRESH;
    private String mCurrentKeyWord; // 输入关键字
    private int mPageSize = 20; // 每页显示20条数据
    private int mCurrentPageIndex = 1;

    private XRecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private FloatingActionButton mFABSearch;
    private EditText mETInput;  //弹出编辑框
    private AlertDialog mInputDialog; // 弹出查找窗口

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        mFABSearch = $(R.id.fab_search);
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
        initInputDialog();
        dynamicAddSkinView(mFABSearch, "backgroundTint", R.color.colorAccent);
    }
    @Override
    protected void setUpData() {
        String[] keyWords = {"杨紫", "蒋欣", "古力娜扎", "张天爱", "杨幂"};
        Random random = new Random();
        int n = random.nextInt(keyWords.length);
        mCurrentKeyWord = keyWords[n];
        //switchAction(ACTION_REFRESH);
        mRecyclerView.setRefreshing(true);

    }

    private void loadComplete() {
        if (mCurrentAction == ACTION_REFRESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }

    private void getData() {
        String reqUrl = Apis.MovieSearch + "?q=" + mCurrentKeyWord + "&start=" + (mCurrentPageIndex - 1) * mPageSize + "&count=" + mPageSize;

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
            case R.id.fab_search:
                mInputDialog.show();
                break;
            default:
                break;
        }
    }
    /***
     * 初始化输入对框框
     */
    private void initInputDialog() {
        mETInput = new EditText(getMContext());
        mETInput.setTextColor(Color.parseColor("#292929"));
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getMContext());
        builder.setTitle("请输入关键字");
        builder.setView(mETInput);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentKeyWord = mETInput.getText().toString();
                if ("".equals(mCurrentKeyWord)) {//如果用户输入的关键字为空，我们就按照最开始的数据加载方式加载
                    setUpData();
                } else {
                    switchAction(ACTION_REFRESH);
                }
                mETInput.setText("");

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mETInput.setText("");
            }
        });
        mInputDialog = builder.create();
    }
}
