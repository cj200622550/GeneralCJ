package com.toolsclass.chenjun.general.generalcj.Setting.Utility;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;

import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 作者：陈骏 on 2017/5/18 10:45
 * QQ：200622550
 */

public class CpuUtility {
    private static final String TAG = "CPU工具类\n";
    static public String getCpuString() {
//        if (Build.CPU_ABI.equalsIgnoreCase("x86")) {
//            return "Intel";
//        }

        String strInfo = "";
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                strInfo = ret.substring(0, index);
            } else {
                strInfo = ret;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        LogUtils.i(TAG + "CPU文件信息：" + strInfo);
        return strInfo;
    }

    static public String getCpuType() {
        String strInfo = getCpuString();
        LogUtils.i(TAG + "CPU文件信息：" + strInfo);
        String strType = null;
        if (strInfo.contains("ARMv5")) {
            strType = "armv5";
        } else if (strInfo.contains("ARMv6")) {
            strType = "armv6";
        } else if (strInfo.contains("ARMv7")) {
            strType = "armv7";
        } else if (strInfo.contains("Intel")) {
            strType = "x86";
        } else {
            strType = "unknown";
            return strType;
        }
        if (strInfo.contains("neon")) {
            strType += "_neon";
        } else if (strInfo.contains("vfpv3")) {
            strType += "_vfpv3";
        } else if (strInfo.contains(" vfp")) {
            strType += "_vfp";
        } else {
            strType += "_none";
        }

        return strType;
    }
}
