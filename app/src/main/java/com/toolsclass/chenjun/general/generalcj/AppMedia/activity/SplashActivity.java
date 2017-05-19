package com.toolsclass.chenjun.general.generalcj.AppMedia.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.AppMedia.application.AppCache;
import com.toolsclass.chenjun.general.generalcj.AppMedia.http.HttpCallback;
import com.toolsclass.chenjun.general.generalcj.AppMedia.http.HttpClient;
import com.toolsclass.chenjun.general.generalcj.AppMedia.model.Splash;
import com.toolsclass.chenjun.general.generalcj.AppMedia.service.PlayService;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.FileUtils;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.Preferences;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.binding.Bind;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.permission.PermissionReq;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.permission.PermissionResult;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.permission.Permissions;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Utility.PermissionUtils;
import com.toolsclass.chenjun.general.generalcj.UtilityActivity.MainActivity;
import com.toolsclass.chenjun.general.generalcj.solid.library.utils.ToastUtils;

import java.io.File;
import java.util.Calendar;

public class SplashActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private static final String SPLASH_FILE_NAME = "splash";

    @Bind(R.id.iv_splash)
    private ImageView ivSplash;
    @Bind(R.id.tv_copyright)
    private TextView tvCopyright;
    private ServiceConnection mPlayServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        requestPermission();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        tvCopyright.setText(getString(R.string.media_copyright, year));
    }

    private void checkService() {
        if (AppCache.getPlayService() == null) {
            startService();
            showSplash();
            updateSplash();

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, 1000);
        } else {
            startMusicActivity();
            finish();
        }
    }

    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppCache.setPlayService(playService);
            PermissionReq.with(SplashActivity.this)
                    .permissions(Permissions.STORAGE)
                    .result(new PermissionResult() {
                        @Override
                        public void onGranted() {
                            scanMusic(playService);
                        }

                        @Override
                        public void onDenied() {
                            ToastUtils.getInstance().showToast(getString(R.string.media_no_permission, Permissions.STORAGE_DESC, "扫描本地歌曲"));
                            finish();
                            playService.stop();
                        }
                    })
                    .request();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private void scanMusic(final PlayService playService) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                playService.updateMusicList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                startMusicActivity();
                finish();
            }
        }.execute();
    }

    private void showSplash() {
        File splashImg = new File(FileUtils.getSplashDir(this), SPLASH_FILE_NAME);
        if (splashImg.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(splashImg.getPath());
            ivSplash.setImageBitmap(bitmap);
        }
    }

    private void updateSplash() {
        HttpClient.getSplash(new HttpCallback<Splash>() {
            @Override
            public void onSuccess(Splash response) {
                if (response == null || TextUtils.isEmpty(response.getUrl())) {
                    return;
                }

                final String url = response.getUrl();
                String lastImgUrl = Preferences.getSplashUrl();
                if (TextUtils.equals(lastImgUrl, url)) {
                    return;
                }

                HttpClient.downloadFile(url, FileUtils.getSplashDir(AppCache.getContext()), SPLASH_FILE_NAME,
                        new HttpCallback<File>() {
                            @Override
                            public void onSuccess(File file) {
                                Preferences.saveSplashUrl(url);
                            }

                            @Override
                            public void onFail(Exception e) {
                            }
                        });
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }

    private void startMusicActivity() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
//        intent.setClass(this, MusicActivity.class);
        intent.setClass(this, MainActivity.class);
//        intent.putExtras(getIntent());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        if (mPlayServiceConnection != null) {
            unbindService(mPlayServiceConnection);
        }
        super.onDestroy();
    }

    private void requestPermission() {

        PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
            @Override
            public void onPermissionGranted(int requestCode) {
                switch (requestCode) {
                    case PermissionUtils.CODE_RECORD_AUDIO:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_RECORD_AUDIO");
                        break;
                    case PermissionUtils.CODE_GET_ACCOUNTS:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_GET_ACCOUNTS");
                        break;
                    case PermissionUtils.CODE_READ_PHONE_STATE:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_READ_PHONE_STATE");
                        break;
                    case PermissionUtils.CODE_CALL_PHONE:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_CALL_PHONE");
                        break;
                    case PermissionUtils.CODE_CAMERA:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_CAMERA");
                        break;
                    case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_ACCESS_FINE_LOCATION");
                        break;
                    case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_ACCESS_COARSE_LOCATION");
                        break;
                    case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_READ_EXTERNAL_STORAGE");
                        break;
                    case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                        ToastUtils.getInstance().showToast("Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE");
                        break;
                    default:
                        break;
                }
            }
        };

        PermissionUtils.requestMultiPermissions(this,mPermissionGrant);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        checkService();

    }
}
