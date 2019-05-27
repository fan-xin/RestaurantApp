package com.fanxin.android.restaurantapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

public class RegisterActivity extends BaseActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //为工具栏设定标题
        setupToolbar();

        initView();

        initEvent();

        //设置页面标题
        setTitle("注册");


    }



    private void initView() {
//        mBtnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void initEvent() {
        mEtUsername = (EditText) findViewById(R.id.id_et_username);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
        mEtRePassword = (EditText) findViewById(R.id.id_et_repassword);
        mBtnRegister = (Button) findViewById(R.id.id_btn_register);

    }
}
