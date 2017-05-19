package com.toolsclass.chenjun.general.generalcj.AppMedia.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.AppMedia.adapter.OnMoreClickListener;
import com.toolsclass.chenjun.general.generalcj.AppMedia.adapter.SearchMusicAdapter;
import com.toolsclass.chenjun.general.generalcj.AppMedia.enums.LoadStateEnum;
import com.toolsclass.chenjun.general.generalcj.AppMedia.executor.DownloadSearchedMusic;
import com.toolsclass.chenjun.general.generalcj.AppMedia.executor.PlaySearchedMusic;
import com.toolsclass.chenjun.general.generalcj.AppMedia.executor.ShareOnlineMusic;
import com.toolsclass.chenjun.general.generalcj.AppMedia.http.HttpCallback;
import com.toolsclass.chenjun.general.generalcj.AppMedia.http.HttpClient;
import com.toolsclass.chenjun.general.generalcj.AppMedia.model.Music;
import com.toolsclass.chenjun.general.generalcj.AppMedia.model.SearchMusic;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.FileUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.ToastUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.ViewUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.binding.Bind;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchMusicActivity extends BaseActivity implements SearchView.OnQueryTextListener
        , AdapterView.OnItemClickListener, OnMoreClickListener {
    @Bind(R.id.lv_search_music_list)
    private ListView lvSearchMusic;
    @Bind(R.id.ll_loading)
    private LinearLayout llLoading;
    @Bind(R.id.ll_load_fail)
    private LinearLayout llLoadFail;
    private List<SearchMusic.Song> mSearchMusicList = new ArrayList<>();
    private SearchMusicAdapter mAdapter = new SearchMusicAdapter(mSearchMusicList);
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_activity_search_music);

        if (!checkServiceAlive()) {
            return;
        }

        lvSearchMusic.setAdapter(mAdapter);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.media_loading));
        ((TextView) llLoadFail.findViewById(R.id.tv_load_fail_text)).setText(R.string.media_search_empty);
    }

    @Override
    protected void setListener() {
        lvSearchMusic.setOnItemClickListener(this);
        mAdapter.setOnMoreClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.media_menu_search_music, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();
        searchView.setQueryHint(getString(R.string.media_search_tips));
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        try {
            Field field = searchView.getClass().getDeclaredField("mGoButton");
            field.setAccessible(true);
            ImageView mGoButton = (ImageView) field.get(searchView);
            mGoButton.setImageResource(R.drawable.media_ic_menu_search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ViewUtils.changeViewState(lvSearchMusic, llLoading, llLoadFail, LoadStateEnum.LOADING);
        searchMusic(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void searchMusic(String keyword) {

//        //创建okHttpClient对象
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        //创建一个Request
//        Request request = new Request.Builder()
//                .url("http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.catalogSug&query=演员")
////                .url("https://www.baidu.com/")
//                .build();
//        //new call
//        Call call = mOkHttpClient.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LogUtils.i("失败：" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String htmlStr =  response.body().string();
//                LogUtils.i(htmlStr);
//            }
//        });


        HttpClient.searchMusic(keyword, new HttpCallback<SearchMusic>(){
                    @Override
                    public void onSuccess(SearchMusic response) {
                        if (response == null || response.getSong() == null) {
                            ViewUtils.changeViewState(lvSearchMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
                            return;
                        }
                        ViewUtils.changeViewState(lvSearchMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_SUCCESS);
                        mSearchMusicList.clear();
                        mSearchMusicList.addAll(response.getSong());
                        mAdapter.notifyDataSetChanged();
                        lvSearchMusic.requestFocus();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                lvSearchMusic.setSelection(0);
                            }
                        });
                    }

                    @Override
                    public void onFail(Exception e) {
                        ViewUtils.changeViewState(lvSearchMusic, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
                    }
                }
        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new PlaySearchedMusic(this, mSearchMusicList.get(position)) {
            @Override
            public void onPrepare() {
                mProgressDialog.show();
            }

            @Override
            public void onExecuteSuccess(Music music) {
                mProgressDialog.cancel();
                getPlayService().play(music);
                ToastUtils.show(getString(R.string.media_now_play, music.getTitle()));
            }

            @Override
            public void onExecuteFail(Exception e) {
                mProgressDialog.cancel();
                ToastUtils.show(R.string.media_unable_to_play);
            }
        }.execute();
    }

    @Override
    public void onMoreClick(int position) {
        final SearchMusic.Song song = mSearchMusicList.get(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(song.getSongname());
        String path = FileUtils.getMusicDir() + FileUtils.getMp3FileName(song.getArtistname(), song.getSongname());
        File file = new File(path);
        int itemsId = file.exists() ? R.array.search_music_dialog_no_download : R.array.search_music_dialog;
        dialog.setItems(itemsId, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:// 分享
                        share(song);
                        break;
                    case 1:// 下载
                        download(song);
                        break;
                }
            }
        });
        dialog.show();
    }

    private void share(SearchMusic.Song song) {
        new ShareOnlineMusic(this, song.getSongname(), song.getSongid()) {
            @Override
            public void onPrepare() {
                mProgressDialog.show();
            }

            @Override
            public void onExecuteSuccess(Void aVoid) {
                mProgressDialog.cancel();
            }

            @Override
            public void onExecuteFail(Exception e) {
                mProgressDialog.cancel();
            }
        }.execute();
    }

    private void download(final SearchMusic.Song song) {
        new DownloadSearchedMusic(this, song) {
            @Override
            public void onPrepare() {
                mProgressDialog.show();
            }

            @Override
            public void onExecuteSuccess(Void aVoid) {
                mProgressDialog.cancel();
                ToastUtils.show(getString(R.string.media_now_download, song.getSongname()));
            }

            @Override
            public void onExecuteFail(Exception e) {
                mProgressDialog.cancel();
                ToastUtils.show(R.string.media_unable_to_download);
            }
        }.execute();
    }
}
