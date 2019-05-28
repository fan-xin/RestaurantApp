package com.fanxin.android.restaurantapplication.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanxin.android.restaurantapplication.UserInfoHolder;
import com.fanxin.android.restaurantapplication.bean.User;
import com.fanxin.android.restaurantapplication.biz.UserBiz;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.net.CommonCallback;
import com.fanxin.android.restaurantapplication.utils.T;

import org.w3c.dom.Text;

public class LoginActivity extends BaseActivity {

    //业务类对象
    private UserBiz mUserBiz = new UserBiz();

    //对控件进行声明
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initEvent();

    }

    private void initEvent() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名和密码
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();

                //检查用户名和密码
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    T.showToast("username or password is empty");
                    //阻止进程向下执行
                    return;
                }

                startLoadingProgress();

                //点击登陆以后，执行登陆业务
                mUserBiz.login(username, password, new CommonCallback<User>() {
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
                        T.showToast("登录成功");

                        //保存用户的登录信息
                        UserInfoHolder.getInstance().setUser(response);
                        toOrderActivity();
                        finish();

                    }
                });



            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterActivity();

            }
        });

    }



    private void toRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }

    private void toOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {

        mEtUsername = findViewById(R.id.id_et_username);
        mEtPassword = findViewById(R.id.id_et_password);
        mBtnLogin = findViewById(R.id.id_btn_login);
        mTvRegister = findViewById(R.id.id_tv_register);

    }
}
