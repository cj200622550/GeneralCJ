package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.utility;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 作者：陈骏 on 2017/5/18 17:23
 * QQ：200622550
 */

public class SimUtils {
    /***
     *
     * @param context
     * @return 0---sim的状态
     *          1---SIM卡号
     *          2---供货商代码
     *          3---供货商
     *          4---国籍
     *          5---网络运营商
     *          6---网络运营商名称
     *          7---网络类型
     */
    public static List<String> readSIMCard(Context context) {
        List<String> list = new ArrayList<>();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);//取得相关系统服务
        StringBuffer sb = new StringBuffer();
        switch (tm.getSimState()) { //getSimState()取得sim的状态 有下面6中状态 0
            case TelephonyManager.SIM_STATE_ABSENT:
                list.add("无卡");
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                list.add("未知状态");
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                list.add("需要NetworkPIN解锁");
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                list.add("需要PIN解锁");
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                list.add("需要PUK解锁");
                break;
            case TelephonyManager.SIM_STATE_READY:
                list.add("良好");
                break;
        }
        // SIM卡号 1
        if (tm.getSimSerialNumber() != null) {
            list.add(tm.getSimSerialNumber().toString());
        } else {
            list.add("无法取得SIM卡号");
        }
        // 供货商代码 2
        if (tm.getSimOperator().equals("")) {
            list.add("无法取得供货商代码");
        } else {
            list.add(tm.getSimOperator().toString());
        }
        // 供货商 3
        if (tm.getSimOperatorName().equals("")) {
            list.add("无法取得供货商");
        } else {
            list.add(tm.getSimOperatorName().toString());
        }
        // 国籍 4
        if (tm.getSimCountryIso().equals("")) {
            list.add("无法取得国籍");
        } else {
            list.add(tm.getSimCountryIso().toString());
        }
        // 网络运营商 5
        if (tm.getNetworkOperator().equals("")) {
            list.add("无法取得网络运营商");
        } else {
            list.add(tm.getNetworkOperator());
        }
        // 网络运营商名称 6
        if (tm.getNetworkOperatorName().equals("")) {
            list.add("无法取得网络运营商名称");
        } else {
            list.add(tm.getNetworkOperatorName());
        }
        // 网络类型 7
        if (tm.getNetworkType() == 0) {
            list.add("无法取得网络类型");
        } else {
            list.add(tm.getNetworkType() + "");
        }
        return list;
    }
}
