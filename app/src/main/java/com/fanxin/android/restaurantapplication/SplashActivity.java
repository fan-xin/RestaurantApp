package com.fanxin.android.restaurantapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    private Button mBtnSkip;
    //等待3秒的handler
    private Handler mHandler = new Handler();

    private Runnable mRunnableToLogin = new Runnable() {
        @Override
        public void run() {
            toLoginActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化界面
        initView();
        //添加监听事件
        initEvent();

        mHandler.postDelayed(mRunnableToLogin,8000);

    }

    private void initEvent(){
        //给跳过按钮添加响应事件
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了防止执行两次toLoginActivity
                mHandler.removeCallbacks(mRunnableToLogin);
                toLoginActivity();
            }
        });
    }

    private void initView(){
        mBtnSkip = findViewById(R.id.splash_skip_btn);

    }

    private void toLoginActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        //因为是闪屏页面，所以是没有back的
        //finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnableToLogin);
    }
}