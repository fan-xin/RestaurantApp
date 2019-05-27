package com.fanxin.android.restaurantapplication.net;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;



/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/27  17:40
 */
public abstract class CommonCallback<T> extends StringCallback {

    Type mType;

    public CommonCallback(){
        Class<? extends CommonCallback> clazz = getClass();
        Type genericSuperclass = clazz.getGenericSuperclass();

        if (genericSuperclass instanceof Class){
            throw new RuntimeException("Miss Type Parameters");
        }

        ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;
        //返回Type数组
        mType = parameterizedType.getActualTypeArguments()[0];
    }


    @Override
    public void onError(Call call, Exception e) {
        onError(e);
    }

    @Override
    public void onResponse(Call call, String s) {
        //在response函数中进行区分到底是不是Error
        try {
            JSONObject resp = new JSONObject(s);
            int resultCode = resp.getInt("resultCode");

            if (resultCode == 1){
                //成功
                String data = resp.getString("data");
                Gson gson = new Gson();
                onSuccess((T)gson.fromJson(data,mType));
                
            }else {
                onError(new RuntimeException(resp.getString("resultMessage")));
            }



        }catch (JSONException e){
            e.printStackTrace();
            onError(e);
        }
    }

    public abstract void onError(Exception e);
    public abstract void onSuccess(T response);
}
