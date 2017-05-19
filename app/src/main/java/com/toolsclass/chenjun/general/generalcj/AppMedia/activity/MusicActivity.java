package com.toolsclass.chenjun.general.generalcj.AppMedia.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.AppMedia.adapter.FragmentAdapter;
import com.toolsclass.chenjun.general.generalcj.AppMedia.application.AppCache;
import com.toolsclass.chenjun.general.generalcj.AppMedia.constants.Extras;
import com.toolsclass.chenjun.general.generalcj.AppMedia.fragment.LocalMusicFragment;
import com.toolsclass.chenjun.general.generalcj.AppMedia.fragment.PlayFragment;
import com.toolsclass.chenjun.general.generalcj.AppMedia.fragment.SongListFragment;
import com.toolsclass.chenjun.general.generalcj.AppMedia.model.Music;
import com.toolsclass.chenjun.general.generalcj.AppMedia.receiver.RemoteControlReceiver;
import com.toolsclass.chenjun.general.generalcj.AppMedia.service.OnPlayerEventListener;
import com.toolsclass.chenjun.general.generalcj.AppMedia.service.PlayService;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.CoverLoader;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.ToastUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.binding.Bind;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.permission.PermissionReq;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.permission.PermissionResult;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.permission.Permissions;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import java.io.IOException;

/**
 * 作者：陈骏 on 2017/5/16 15:13
 * QQ：200622550
 */

public class MusicActivity extends BaseActivity implements View.OnClickListener, OnPlayerEventListener, ViewPager.OnPageChangeListener  {
    @Bind(R.id.drawer_layout)
    private DrawerLayout drawerLayout;
//    @Bind(R.id.navigation_view)
//    private NavigationView navigationView;
    @Bind(R.id.iv_menu)
    private ImageView ivMenu;
    @Bind(R.id.iv_search)
    private ImageView ivSearch;
    @Bind(R.id.tv_local_music)
    private TextView tvLocalMusic;
    @Bind(R.id.tv_online_music)
    private TextView tvOnlineMusic;
    @Bind(R.id.viewpager)
    private ViewPager mViewPager;
    @Bind(R.id.fl_play_bar)
    private FrameLayout flPlayBar;
    @Bind(R.id.iv_play_bar_cover)
    private ImageView ivPlayBarCover;
    @Bind(R.id.tv_play_bar_title)
    private TextView tvPlayBarTitle;
    @Bind(R.id.tv_play_bar_artist)
    private TextView tvPlayBarArtist;
    @Bind(R.id.iv_play_bar_play)
    private ImageView ivPlayBarPlay;
    @Bind(R.id.iv_play_bar_next)
    private ImageView ivPlayBarNext;
    @Bind(R.id.pb_play_bar)
    private ProgressBar mProgressBar;
    private View vNavigationHeader;

    private LocalMusicFragment mLocalMusicFragment;
    private SongListFragment mSongListFragment;
    private PlayFragment mPlayFragment;
    private AudioManager mAudioManager;
    private ComponentName mRemoteReceiver;
    private boolean isPlayFragmentShow = false;
    private MenuItem timerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_media);

        if (!checkServiceAlive()) {
            return;
        }

        getPlayService().setOnPlayEventListener(this);

        setupView();
//        updateWeather();
        registerReceiver();
        onChange(getPlayService().getPlayingMusic());
        parseIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        parseIntent();
    }

    @Override
    protected void setListener() {
        ivMenu.setOnClickListener(this);

        ivSearch.setOnClickListener(this);
        tvLocalMusic.setOnClickListener(this);
        tvOnlineMusic.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(this);
        flPlayBar.setOnClickListener(this);
        ivPlayBarPlay.setOnClickListener(this);
        ivPlayBarNext.setOnClickListener(this);

//        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupView() {
        // add navigation header
//        vNavigationHeader = LayoutInflater.from(this).inflate(R.layout.navigation_header, navigationView, false);
//        navigationView.addHeaderView(vNavigationHeader);

        // setup view pager
        mLocalMusicFragment = new LocalMusicFragment();
        mSongListFragment = new SongListFragment();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(mLocalMusicFragment);
        adapter.addFragment(mSongListFragment);
        mViewPager.setAdapter(adapter);
        tvLocalMusic.setSelected(true);
    }

    private void updateWeather() {
        PermissionReq.with(this)
                .permissions(Permissions.LOCATION)
                .result(new PermissionResult() {
                    @Override
                    public void onGranted() {
//                        new WeatherExecutor(getPlayService(), vNavigationHeader).execute();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.show(getString(R.string.media_no_permission, Permissions.LOCATION_DESC, "更新天气"));
                    }
                })
                .request();
    }

    private void registerReceiver() {
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mRemoteReceiver = new ComponentName(getPackageName(), RemoteControlReceiver.class.getName());
        mAudioManager.registerMediaButtonEventReceiver(mRemoteReceiver);
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(Extras.EXTRA_NOTIFICATION)) {
            showPlayingFragment();
            setIntent(new Intent());
        }
    }

    /**
     * 更新播放进度
     */
    @Override
    public void onPublish(int progress) {
        mProgressBar.setProgress(progress);
        if (mPlayFragment != null) {
            mPlayFragment.onPublish(progress);
        }
    }

    @Override
    public void onChange(Music music) {
        onPlay(music);
        if (mPlayFragment != null) {
            mPlayFragment.onChange(music);
        }
    }

    @Override
    public void onPlayerPause() {
        ivPlayBarPlay.setSelected(false);
        if (mPlayFragment != null) {
            mPlayFragment.onPlayerPause();
        }
    }

    @Override
    public void onPlayerResume() {
        ivPlayBarPlay.setSelected(true);
        if (mPlayFragment != null) {
            mPlayFragment.onPlayerResume();
        }
    }

    @Override
    public void onTimer(long remain) {
//        if (timerItem == null) {
//            timerItem = navigationView.getMenu().findItem(R.id.action_timer);
//        }
//        String title = getString(R.string.media_menu_timer);
//        timerItem.setTitle(remain == 0 ? title : SystemUtils.formatTime(title + "(mm:ss)", remain));
    }

    @Override
    public void onMusicListUpdate() {
        if (mLocalMusicFragment != null) {
            mLocalMusicFragment.onMusicListUpdate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
//                drawerLayout.openDrawer(GravityCompat.START);
                try { // 模拟返回
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_search:
                startActivity(new Intent(this, SearchMusicActivity.class));
                break;
            case R.id.tv_local_music:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_online_music:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.fl_play_bar:
                showPlayingFragment();
                break;
            case R.id.iv_play_bar_play:
                play();
                break;
            case R.id.iv_play_bar_next:
                next();
                break;
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(final MenuItem item) {
//        drawerLayout.closeDrawers();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                item.setChecked(false);
//            }
//        }, 500);
//        return NaviMenuExecutor.onNavigationItemSelected(item, this);
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            tvLocalMusic.setSelected(true);
            tvOnlineMusic.setSelected(false);
        } else {
            tvLocalMusic.setSelected(false);
            tvOnlineMusic.setSelected(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void onPlay(Music music) {
        if (music == null) {
            return;
        }

        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music);
        ivPlayBarCover.setImageBitmap(cover);
        tvPlayBarTitle.setText(music.getTitle());
        tvPlayBarArtist.setText(music.getArtist());
        if (getPlayService().isPlaying() || getPlayService().isPreparing()) {
            ivPlayBarPlay.setSelected(true);
        } else {
            ivPlayBarPlay.setSelected(false);
        }
        mProgressBar.setMax((int) music.getDuration());
        mProgressBar.setProgress(0);

        if (mLocalMusicFragment != null) {
            mLocalMusicFragment.onItemPlay();
        }
    }

    private void play() {
        getPlayService().playPause();
    }

    private void next() {
        getPlayService().next();
    }

    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }
        LogUtils.i("点击了");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.media_fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = new PlayFragment();
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }

    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.media_fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }

    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 切换夜间模式不保存状态
    }

    @Override
    protected void onDestroy() {
        if (mRemoteReceiver != null) {
            mAudioManager.unregisterMediaButtonEventReceiver(mRemoteReceiver);
        }
        PlayService service = AppCache.getPlayService();
        if (service != null) {
            service.setOnPlayEventListener(null);
        }
        super.onDestroy();
    }
}