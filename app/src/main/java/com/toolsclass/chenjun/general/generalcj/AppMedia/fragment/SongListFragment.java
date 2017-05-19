package com.toolsclass.chenjun.general.generalcj.AppMedia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.AppMedia.activity.OnlineMusicActivity;
import com.toolsclass.chenjun.general.generalcj.AppMedia.adapter.SongListAdapter;
import com.toolsclass.chenjun.general.generalcj.AppMedia.application.AppCache;
import com.toolsclass.chenjun.general.generalcj.AppMedia.constants.Extras;
import com.toolsclass.chenjun.general.generalcj.AppMedia.enums.LoadStateEnum;
import com.toolsclass.chenjun.general.generalcj.AppMedia.model.SongListInfo;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.NetworkUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.ViewUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.binding.Bind;

/**
 * 在线音乐
 * Created by wcy on 2015/11/26.
 */
public class SongListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @Bind(R.id.lv_song_list)
    private ListView lvSongList;
    @Bind(R.id.ll_loading)
    private LinearLayout llLoading;
    @Bind(R.id.ll_load_fail)
    private LinearLayout llLoadFail;
    private List<SongListInfo> mSongLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.media_fragment_song_list, container, false);
    }

    @Override
    protected void init() {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            ViewUtils.changeViewState(lvSongList, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
            return;
        }
        mSongLists = AppCache.getSongListInfos();
        if (mSongLists.isEmpty()) {
            String[] titles = getResources().getStringArray(R.array.online_music_list_title);
            String[] types = getResources().getStringArray(R.array.online_music_list_type);
            for (int i = 0; i < titles.length; i++) {
                SongListInfo info = new SongListInfo();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                mSongLists.add(info);
            }
        }
        SongListAdapter adapter = new SongListAdapter(mSongLists);
        lvSongList.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        lvSongList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SongListInfo songListInfo = mSongLists.get(position);
        Intent intent = new Intent(getContext(), OnlineMusicActivity.class);
        intent.putExtra(Extras.MUSIC_LIST_TYPE, songListInfo);
        startActivity(intent);
    }
}
