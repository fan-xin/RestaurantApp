package com.fanxin.android.restaurantapplication.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.UserInfoHolder;
import com.fanxin.android.restaurantapplication.bean.Order;
import com.fanxin.android.restaurantapplication.bean.User;
import com.fanxin.android.restaurantapplication.biz.OrderBiz;
import com.fanxin.android.restaurantapplication.net.CommonCallback;
import com.fanxin.android.restaurantapplication.ui.adapter.OrderAdapter;
import com.fanxin.android.restaurantapplication.ui.view.CircleTransform;
import com.fanxin.android.restaurantapplication.ui.view.refresh.SwipeRefresh;
import com.fanxin.android.restaurantapplication.ui.view.refresh.SwipeRefreshLayout;
import com.fanxin.android.restaurantapplication.utils.T;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {
    private Button mBtnOrder;
    private TextView mTvUsername;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mIvIcon;

    private OrderAdapter orderAdapter;
    private List<Order> mDatas = new ArrayList<>();


    //业务类对象
    private OrderBiz mOrderBiz = new OrderBiz();


    private int mCurrentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initEvent();

        //在创建Activity的时候加载数据
        loadDatas();

    }

    private void initEvent() {

        //下拉
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();

            }
        });

        //上拉
        mSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();

            }
        });

        //点餐按钮
        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, ProductListActivity.class);
                startActivityForResult(intent,1001);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && requestCode == RESULT_OK){
            loadDatas();
        }
    }

    //向上拉，获取更多
    private void loadMore() {
        startLoadingProgress();
        mOrderBiz.listByPage(++mCurrentPage, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mCurrentPage--;
                mSwipeRefreshLayout.setPullUpRefreshing(false);

                if ("用户未登录".equals(e.getMessage())){
                    toLoginActivity();
                }

            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();

                if (response.size()== 0){
                    T.showToast("没有订单了");
                    //停止刷新
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }

                T.showToast("订单加载成功！");
                mDatas.addAll(response);
                mSwipeRefreshLayout.setPullUpRefreshing(false);

            }
        });

    }

    //下拉刷新
    private void loadDatas() {
        startLoadingProgress();
        mOrderBiz.listByPage(0, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);

                if ("用户未登录".equals(e.getMessage())){
                    toLoginActivity();
                }



            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                mCurrentPage = 0;
                T.showToast("订单更新成功！");
                mDatas.clear();
                mDatas.addAll(response);
                orderAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }

            }
        });

    }

    private void initView() {
        mBtnOrder = (Button)findViewById(R.id.id_btn_order);
        mTvUsername = (TextView) findViewById(R.id.id_tv_username);
        mRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.id_swiperefresh);
        mIvIcon = findViewById(R.id.id_iv_icon);

        //从infoholder中获取user，然后从user获取用户名
        User user = UserInfoHolder.getInstance().getUser();
        if(user!= null){
            //设置用户名
            mTvUsername.setText(user.getUsername());
        } else {
            //如果用户登录状态丢失
            toLoginActivity();
            finish();
            return;
        }

        //设置控件，上拉，下拉都支持
        mSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.GREEN,Color.YELLOW);


        //初始化adapter
        orderAdapter = new OrderAdapter(this, mDatas);

        //设置recyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(orderAdapter);


        Picasso.get()
                .load(R.drawable.icon)
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(mIvIcon);



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            try{
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;

            }catch (Exception e){
                //ignoe
            }

        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrderBiz.onDestory();
    }
}
