package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.adapter.NetCheckAdapter;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.model.NetworkModel;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.utility.NetworkUtils;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.utility.SimUtils;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.adapter.MovieAdapter;
import com.toolsclass.chenjun.general.generalcj.bean.MovieBean;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/18 11:19
 * QQ：200622550
 */

public class NetCheckFragment extends BaseFragment implements View.OnClickListener {

    protected static final int UPDATE_TEXT = 0;
    private Timer mTimer;
    private MyTimerTask mTimerTask;
    private Handler  mHandler;
    private List<NetworkModel> networkModels = new ArrayList<>();
    private XRecyclerView mRecyclerView;
    private NetCheckAdapter mNetCheckAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.system_info_fragment_net_check;
    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void setUpView() {
        super.setUpView();
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = $(R.id.recyclerview);
        mNetCheckAdapter = new NetCheckAdapter(getMContext(), new ArrayList<NetworkModel>());
        mRecyclerView.setAdapter(mNetCheckAdapter);
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setRefreshing(false);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mHandler = new Handler(){
            public void handleMessage(Message message){
                switch (message.what){
                    case UPDATE_TEXT:
                        getData();
                        break;
                }
            }
        };
        mTimer = new Timer(false);
    }

    @Override
    protected void setUpData() {
        super.setUpData();
    }

    @Override
    public void onClick(View view) {

    }

    class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            Message msg = mHandler.obtainMessage(UPDATE_TEXT);
            msg.sendToTarget();
        }
    }

    /**获取数据*/
    private void getData(){
        networkModels.clear();
        mNetCheckAdapter.clear();
        String NetworkAvailable = NetworkUtils.isNetworkAvailable(getMContext()) ? "是" : "否";
        networkModels.add(new NetworkModel("网络是否可用",NetworkAvailable));
        networkModels.add(new NetworkModel("网络类型",NetworkUtils.getConnectedType(getMContext())));
        List<String> list = NetworkUtils.getNetworkInfo(getMContext());
        networkModels.add(new NetworkModel("网络数据类型",list.size() != 0 ? list.get(3) : "没有网络"));
        networkModels.add(new NetworkModel("WIFI名字",list.size() != 0 ? list.get(4) : "没有连接WIFI"));
        networkModels.add(new NetworkModel("网络运营商",NetworkUtils.getOperators(getMContext())));
        networkModels.add(new NetworkModel("Sim卡状态", SimUtils.readSIMCard(getMContext()).get(0)));
        networkModels.add(new NetworkModel("Sim卡号", SimUtils.readSIMCard(getMContext()).get(1)));
        networkModels.add(new NetworkModel("Sim供货商代码", SimUtils.readSIMCard(getMContext()).get(2)));
        networkModels.add(new NetworkModel("Sim供货商", SimUtils.readSIMCard(getMContext()).get(3)));
        networkModels.add(new NetworkModel("Sim国籍", SimUtils.readSIMCard(getMContext()).get(4)));
        networkModels.add(new NetworkModel("Sim网络运营商", SimUtils.readSIMCard(getMContext()).get(5)));
        networkModels.add(new NetworkModel("Sim网络运营商名称", SimUtils.readSIMCard(getMContext()).get(6)));
        networkModels.add(new NetworkModel("Sim网络类型", SimUtils.readSIMCard(getMContext()).get(7)));
        LogUtils.e(networkModels.toString());
        mNetCheckAdapter.addAll(networkModels);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mTimerTask = new MyTimerTask();  // 新建一个任务
            mTimer.schedule(mTimerTask,0, 1*1000);
        } else {
            if (mTimer != null) {
                if (mTimerTask != null) {
                    mTimerTask.cancel();  //将原任务从队列中移除
                }
            }
        }
    }
}
