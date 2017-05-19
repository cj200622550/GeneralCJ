package com.toolsclass.chenjun.general.generalcj.AppMedia.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.AppMedia.application.AppCache;
import com.toolsclass.chenjun.general.generalcj.AppMedia.model.Music;
import com.toolsclass.chenjun.general.generalcj.AppMedia.service.PlayService;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.CoverLoader;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.FileUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.binding.Bind;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.binding.ViewBinder;
import com.toolsclass.chenjun.general.generalcj.R;


/**
 * 本地音乐列表适配器
 * Created by wcy on 2015/11/27.
 */
public class LocalMusicAdapter extends BaseAdapter {
    private OnMoreClickListener mListener;
    private int mPlayingPosition;

    @Override
    public int getCount() {
        return AppCache.getMusicList().size();
    }

    @Override
    public Object getItem(int position) {
        return AppCache.getMusicList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_view_holder_music, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == mPlayingPosition) {
            holder.vPlaying.setVisibility(View.VISIBLE);
        } else {
            holder.vPlaying.setVisibility(View.INVISIBLE);
        }
        Music music = AppCache.getMusicList().get(position);
        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music);
        holder.ivCover.setImageBitmap(cover);
        holder.tvTitle.setText(music.getTitle());
        String artist = FileUtils.getArtistAndAlbum(music.getArtist(), music.getAlbum());
        holder.tvArtist.setText(artist);
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMoreClick(position);
                }
            }
        });
        holder.vDivider.setVisibility(isShowDivider(position) ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private boolean isShowDivider(int position) {
        return position != AppCache.getMusicList().size() - 1;
    }

    public void updatePlayingPosition(PlayService playService) {
        if (playService.getPlayingMusic() != null && playService.getPlayingMusic().getType() == Music.Type.LOCAL) {
            mPlayingPosition = playService.getPlayingPosition();
        } else {
            mPlayingPosition = -1;
        }
    }

    public void setOnMoreClickListener(OnMoreClickListener listener) {
        mListener = listener;
    }

    private static class ViewHolder {
        @Bind(R.id.v_playing)
        View vPlaying;
        @Bind(R.id.iv_cover)
        ImageView ivCover;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_artist)
        TextView tvArtist;
        @Bind(R.id.iv_more)
        ImageView ivMore;
        @Bind(R.id.v_divider)
        View vDivider;

        public ViewHolder(View view) {
            ViewBinder.bind(this, view);
        }
    }
}
