package com.toolsclass.chenjun.general.generalcj.UtilityActivity;

import android.os.Environment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.toolsclass.chenjun.general.generalcj.AppMedia.application.AppCache;
import com.toolsclass.chenjun.general.generalcj.AppMedia.http.HttpInterceptor;
import com.toolsclass.chenjun.general.generalcj.AppMedia.utils.Preferences;
import com.toolsclass.chenjun.general.generalcj.Libs.sharelibrary.Share;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import ren.solid.library.SolidApplication;

/**
 * 作者：陈骏 on 2017/5/16 10:58
 * QQ：200622550
 */

public class Application extends SolidApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCache.init(this);
        AppCache.updateNightMode(Preferences.isNightMode());
        initImageLoader(); // 初始化图片加载框架
        initOkHttpUtils(); // 初始化OhHttp框架
        initShare(); // 初始化自定义配置文件
    }

    private void initShare() {
        Share.init("CACHE", 10 * 1024);
    }

    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HttpInterceptor())
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(2 * 1024 * 1024) // 2MB
                .diskCacheSize(50 * 1024 * 1024) // 50MB
                .build();
        ImageLoader.getInstance().init(configuration);
    }

}
