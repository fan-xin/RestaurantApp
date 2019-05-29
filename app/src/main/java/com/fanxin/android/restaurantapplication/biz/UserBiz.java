package com.fanxin.android.restaurantapplication.biz;

import com.fanxin.android.restaurantapplication.bean.User;
import com.fanxin.android.restaurantapplication.config.Config;
import com.fanxin.android.restaurantapplication.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/27  16:55
 * 用户相关的逻辑类
 */
public class UserBiz {
    public void login(String username, String password, CommonCallback<User> commonCallback){

        OkHttpUtils
                .post()
                .url(Config.baseUrl+"user_login")
                .tag(this)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);

    }

    public void register(String username, String password, CommonCallback<User> commonCallback){
        OkHttpUtils
                .post()
                .url(Config.baseUrl+"user_register")
                .tag(this)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);

    }

    public void onDestory(){
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
