package com.fanxin.android.restaurantapplication.biz;

import com.fanxin.android.restaurantapplication.bean.Product;
import com.fanxin.android.restaurantapplication.config.Config;
import com.fanxin.android.restaurantapplication.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/30  11:36
 */
public class ProductBiz {
    public void listByPage(int currentPage, CommonCallback<List<Product>> commonCallback){

        OkHttpUtils.post()
                .url(Config.baseUrl+"product_find")
                .addParams("currentPage",currentPage+"")
                .tag(this)
                .build()
                .execute(commonCallback);

    }

    public void onDestory(){
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
