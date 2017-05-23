package com.toolsclass.chenjun.general.generalcj.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

import static android.R.id.list;

/**
 * 作者：陈骏 on 2017/5/22 16:54
 * QQ：200622550
 */

public class ActivivtyUtility {

    public static boolean isActivity(String className, Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.toolsclass.chenjun.general.generalcj", className);
        if (context.getPackageManager().resolveActivity(intent, 0) == null) {
            // 说明系统中不存在这个activity
            return false;
        } else  if (intent.resolveActivity(context.getPackageManager()) == null) {
            // 说明系统中不存在这个activity
            return false;
        }
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
        if (list.size() == 0){
            return false;
        } else {
            return true;
        }
    }
}
