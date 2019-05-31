package com.fanxin.android.restaurantapplication.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fanxin.android.restaurantapplication.R;


/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/27  15:42
 */
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏为黑色
        //也可以使用systembar的开源库
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            getWindow().setStatusBarColor(0xff000000);
        }

        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setMessage("Loading");

    }


    protected void stopLoadingProgress() {
        if (mLoadingDialog != null & mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }

    protected void startLoadingProgress() {
        mLoadingDialog.show();

    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopLoadingProgress();
        mLoadingDialog = null;
    }

    //跳转到登录界面
    protected void toLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        //清空Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}
