package com.toolsclass.chenjun.general.generalcj.adapter;

import android.content.Context;
import android.content.Intent;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Utility.MovieUtility;
import com.toolsclass.chenjun.general.generalcj.UtilityActivity.MovieDetailActivity;
import com.toolsclass.chenjun.general.generalcj.bean.MovieBean;
import com.toolsclass.chenjun.general.generalcj.solid.library.adapter.SolidRVBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MovieAdapter extends SolidRVBaseAdapter<MovieBean> {

    public MovieAdapter(Context context, List<MovieBean> beans) {
        super(context, beans);
    }
    @Override
    public int getItemLayoutID(int vieWType) {
        return R.layout.item_movie;
    }

    @Override
    protected void onItemClick(int position) {
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra("id", mBeans.get(position - 1).getId());
        mContext.startActivity(intent);
    }
    @Override
    protected void onBindDataToView(SolidCommonViewHolder holder, MovieBean bean, int position) {
        holder.setText(R.id.tv_title, bean.getTitle());
        holder.setText(R.id.tv_subtype, "类型:" + MovieUtility.StringTo(bean.getString(),"/")); // 电影分类
        holder.setText(R.id.tv_director, "导演:" + MovieUtility.getDirector(bean.getDirectors(),"/"));
        holder.setText(R.id.tv_star, "主演:" + MovieUtility.getCasts(bean.getCasts(),"/"));
        holder.setText(R.id.tv_date, "年份:" + bean.getYear() + "年");
        holder.setText(R.id.tv_english_name, "更多名字:" + bean.getOriginal_title());
        holder.setText(R.id.tv_rating, "豆瓣评分:" + bean.getRating().getAverage());
        holder.setImageFromInternet(R.id.iv_image, bean.getImages().getLarge() );
    }

}
