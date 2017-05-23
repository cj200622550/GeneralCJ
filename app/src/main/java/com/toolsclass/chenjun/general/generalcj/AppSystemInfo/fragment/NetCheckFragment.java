package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.adapter.NetCheckAdapter;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.model.NetworkModel;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.utility.NetworkUtils;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.utility.SimUtils;
import com.toolsclass.chenjun.general.generalcj.Config.ShareConfig;
import com.toolsclass.chenjun.general.generalcj.Libs.sharelibrary.Share;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Utility.TimeUtility;
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
    private Handler mHandler;
    private List<NetworkModel> networkModels = new ArrayList<>();
    private XRecyclerView mRecyclerView;
    private NetCheckAdapter mNetCheckAdapter;

    private TelephonyManager Tel;
    private MyPhoneStateListener MyListener;
    private String Signal = "";

    @Override
    protected int setLayoutResourceID() {
        return R.layout.system_info_fragment_net_check;
    }

    @Override
    protected void init() {
        super.init();
        MyListener = new MyPhoneStateListener();
        Tel = (TelephonyManager) getMContext().getSystemService(Context.TELEPHONY_SERVICE);
        Tel.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
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
        mHandler = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {
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

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Message msg = mHandler.obtainMessage(UPDATE_TEXT);
            msg.sendToTarget();
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        networkModels.clear();
        mNetCheckAdapter.clear();
        String NetworkAvailable = NetworkUtils.isNetworkAvailable(getMContext()) ? "是" : "否";
        networkModels.add(new NetworkModel("更新时间", TimeUtility.getNowDate().toString()));
        networkModels.add(new NetworkModel("网络是否可用", NetworkAvailable));
        networkModels.add(new NetworkModel("网络类型", NetworkUtils.getConnectedType(getMContext())));
        List<String> list = NetworkUtils.getNetworkInfo(getMContext());
        List<String> wifi = NetworkUtils.getWifiSignal(getMContext());
        networkModels.add(new NetworkModel("网络数据类型", list.size() != 0 ? list.get(3) : "没有网络"));
        networkModels.add(new NetworkModel("WIFI名字", list.size() != 0 ? list.get(4) : "没有连接WIFI"));

        networkModels.add(new NetworkModel("BSSID", wifi.size() != 0 ? wifi.get(0) : "没有连接WIFI"));
        networkModels.add(new NetworkModel("SSID", wifi.size() != 0 ? wifi.get(1) : "没有连接WIFI"));
        networkModels.add(new NetworkModel("IP地址", wifi.size() != 0 ? wifi.get(2) : "没有连接WIFI"));
        networkModels.add(new NetworkModel("MAC地址", wifi.size() != 0 ? wifi.get(3) : "没有连接WIFI"));
        networkModels.add(new NetworkModel("网络ID", wifi.size() != 0 ? wifi.get(4) : "没有连接WIFI"));
        networkModels.add(new NetworkModel("Wifi信号", wifi.size() != 0 ? wifi.get(6) : "没有连接WIFI"));

        networkModels.add(new NetworkModel("信号强度", Signal));
        networkModels.add(new NetworkModel("网络运营商", NetworkUtils.getOperators(getMContext())));
        networkModels.add(new NetworkModel("Sim卡状态", SimUtils.readSIMCard(getMContext()).get(0)));
        networkModels.add(new NetworkModel("Sim卡号", SimUtils.readSIMCard(getMContext()).get(1)));
        networkModels.add(new NetworkModel("Sim供货商代码", SimUtils.readSIMCard(getMContext()).get(2)));
        networkModels.add(new NetworkModel("Sim供货商", SimUtils.readSIMCard(getMContext()).get(3)));
        networkModels.add(new NetworkModel("Sim国籍", SimUtils.readSIMCard(getMContext()).get(4)));
        networkModels.add(new NetworkModel("Sim网络运营商", SimUtils.readSIMCard(getMContext()).get(5)));
        networkModels.add(new NetworkModel("Sim网络运营商名称", SimUtils.readSIMCard(getMContext()).get(6)));
        networkModels.add(new NetworkModel("Sim网络类型", SimUtils.readSIMCard(getMContext()).get(7)));
//        LogUtils.e(networkModels.toString());
        mNetCheckAdapter.addAll(networkModels);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mTimerTask = new MyTimerTask();  // 新建一个任务
            mTimer.schedule(mTimerTask, 0, Share.getInt(ShareConfig.SHARE_SYSTEM_CHECK_TIME, 1000));
            Tel.listen(MyListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        } else {
            if (mTimer != null) {
                if (mTimerTask != null) {
                    mTimerTask.cancel();  //将原任务从队列中移除
                    Tel.listen(MyListener, PhoneStateListener.LISTEN_NONE);
                }
            }
        }
    }

    private class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            Signal = String.valueOf(signalStrength.getGsmSignalStrength());
        }
    };
}
