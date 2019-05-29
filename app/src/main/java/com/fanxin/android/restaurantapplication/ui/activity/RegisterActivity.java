package com.fanxin.android.restaurantapplication.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.UserInfoHolder;
import com.fanxin.android.restaurantapplication.bean.User;
import com.fanxin.android.restaurantapplication.biz.UserBiz;
import com.fanxin.android.restaurantapplication.net.CommonCallback;
import com.fanxin.android.restaurantapplication.utils.T;

public class RegisterActivity extends BaseActivity {

    //新建业务类
    private UserBiz mUserBiz = new UserBiz();


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



    private void initEvent() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取用户名和密码
                final String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String repassword = mEtRePassword.getText().toString();

                //检查用户名和密码
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)){
                    T.showToast("username or password is empty");
                    //阻止进程向下执行
                    return;
                }

                if (!password.equals(repassword)){
                    T.showToast("password is not same");
                    return;
                }

                startLoadingProgress();

                //点击登陆以后，执行登陆业务
                mUserBiz.register(username, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        T.showToast(e.getMessage());
                        Log.e("hello",e.getMessage());
                        Log.e("hello",e.getStackTrace().toString());

                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        T.showToast("注册成功！"+response.getUsername());
                        LoginActivity.launch(RegisterActivity.this, response.getUsername(),response.getPassword());
                        finish();



                    }
                });


            }
        });
    }

    private void initView() {
        mEtUsername = (EditText) findViewById(R.id.id_et_username);
        mEtPassword = (EditText) findViewById(R.id.id_et_password);
        mEtRePassword = (EditText) findViewById(R.id.id_et_repassword);
        mBtnRegister = (Button) findViewById(R.id.id_btn_register);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUserBiz.onDestory();
    }
}
