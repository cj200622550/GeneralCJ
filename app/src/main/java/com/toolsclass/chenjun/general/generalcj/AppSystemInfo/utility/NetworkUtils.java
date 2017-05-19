package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：陈骏 on 2017/5/18 11:22
 * QQ：200622550
 */

public class NetworkUtils {

    public static ConnectivityManager getConnectivityManager(Context context){
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static List<String> getNetworkInfo (Context context){
        List<String> list = new ArrayList<>();
        NetworkInfo info2 = NetworkUtils.getConnectivityManager(context).getActiveNetworkInfo();
        if (info2 != null) {
            list.add(String.valueOf(info2.getDetailedState())); // getDetailedState 0
            list.add(info2 == null ? "" : info2.getReason()); // getReason 1
            list.add(String.valueOf(info2.getSubtype())); // getSubtype 2
            list.add(String.valueOf(info2.getSubtypeName())); //getSubtypeName 3
            list.add(info2.getExtraInfo().equals("CTNET") ? "没有连接WIFI" : info2.getExtraInfo()); // getExtraInfo 4
            list.add(info2.getTypeName().equals("MOBILE") ? "GPRS网络" : "WIFI"); //getTypeName 5
            list.add(String.valueOf(info2.getType())); //getType 6
        }
        LogUtils.e(list == null ? "没有数据":list.toString());
        return list;
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息 1 wifi 2 移动网络 -1 无网络
     *
     * @return
     */
    public static String getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI ? "WIFI" : "移动网络";
            }
        }
        return "无网络";
    }

    /**
     * 返回运营商 需要加入权限 <uses-permission android:name="android.permission.READ_PHONE_STATE"/> <BR>
     *
     * @return 1,代表中国移动，2，代表中国联通，3，代表中国电信，0，代表未知
     * @author youzc@yiche.com
     */
    public static String getOperators(Context context) {
        // 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）（也称为“MCC /
        // MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC 获取手机服务商信息
        String OperatorsName = "未知";
        TelephonyManager telephonyManager =  (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI =  telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
        System.out.println(IMSI);
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            OperatorsName = "中国移动";
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            OperatorsName = "中国联通";
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            OperatorsName = "中国电信";
        }
        return OperatorsName;
    }
    /**
     * 执行exec
     *
     * @param exec 参数 如：ping www.baidu.com
     * @return
     */
    public static String getProcess(String exec) {
        String ret = "";
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(exec);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return ret;
                }
            }
        }
    }
}
