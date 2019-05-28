package com.fanxin.android.restaurantapplication;

import android.app.Application;

import com.fanxin.android.restaurantapplication.utils.SPUtils;
import com.fanxin.android.restaurantapplication.utils.T;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/28  14:53
 */
public class ResApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //必须调用初始化
        //OkHttpUtils.init(this);

        //初始化Toast
        T.init(this);
        SPUtils.init(this,"sp_user.pref");

        //使用到Cookie
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
