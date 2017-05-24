package com.toolsclass.chenjun.general.generalcj.AppRobotChat.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 作者：陈骏 on 2017/5/23 22:47
 * QQ：200622550
 */

public class ViewHolder {
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
