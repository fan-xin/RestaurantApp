package com.fanxin.android.restaurantapplication.biz;

import com.fanxin.android.restaurantapplication.bean.Order;
import com.fanxin.android.restaurantapplication.bean.Product;
import com.fanxin.android.restaurantapplication.config.Config;
import com.fanxin.android.restaurantapplication.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/29  14:55
 */
public class OrderBiz {
    public void listByPage(int currentPage, CommonCallback<List<Order>> commonCallback){
        OkHttpUtils.post()
                .url(Config.baseUrl+"order_find")
                .tag(this)
                .addParams("currentPage",currentPage+"")
                .build()
                .execute(commonCallback);


    }

    public void add(Order order,CommonCallback<String> commonCallback){

        StringBuilder sb = new StringBuilder();

        Map<Product, Integer> productMap = order.productMap;
        for (Product p: productMap.keySet()){
            sb.append(p.getId()+"_"+productMap.get(p));
            sb.append("|");
        }

        sb = sb.deleteCharAt(sb.length() -1);




        OkHttpUtils.post()
                .url(Config.baseUrl+"order_add")
                .addParams("res_id",order.getRestaurant().getId()+"")
                .addParams("product_str",sb.toString())
                .addParams("count",order.getCount()+"")
                .addParams("price",order.getPrice()+"")
                .tag(this)
                .build()
                .execute(commonCallback);
    }

    public void onDestory(){
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
