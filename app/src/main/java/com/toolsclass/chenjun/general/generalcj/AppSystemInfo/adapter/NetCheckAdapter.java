package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.adapter;

import android.content.Context;

import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.model.NetworkModel;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.solid.library.adapter.SolidRVBaseAdapter;

import java.util.List;

/**
 * 作者：陈骏 on 2017/5/18 15:58
 * QQ：200622550
 */

public class NetCheckAdapter extends SolidRVBaseAdapter<NetworkModel> {

    public NetCheckAdapter(Context context, List<NetworkModel> beans) {
        super(context, beans);
    }

    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, NetworkModel bean, int position) {
        holder.setText(R.id.tv_name, bean.getName() + "：");
        holder.setText(R.id.tv_val, bean.getVal());
    }

    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.system_info_item_net_check;
    }

    @Override
    protected void onItemClick(int position) {
//        Intent intent = new Intent(mContext, MovieDetailActivity.class);
//        intent.putExtra("id", mBeans.get(position - 1).getId());
//        mContext.startActivity(intent);
    }

}
