package com.fanxin.android.restaurantapplication.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.biz.ProductBiz;
import com.fanxin.android.restaurantapplication.ui.view.refresh.SwipeRefresh;
import com.fanxin.android.restaurantapplication.ui.view.refresh.SwipeRefreshLayout;

public class ProductListActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mTvCount;
    private Button mBtnPay;

    private ProductBiz mProductBiz;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        setupToolbar();
        setTitle("订餐");

        initView();

        initEvent();

        loadDatas();

    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();

            }
        });

        mSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadDatas();

            }
        });


        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void loadMore() {

    }

    private void loadDatas() {

    }

    private void initView() {
        //初始化控件
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.id_swiperefresh);
        mBtnPay = (Button)findViewById(R.id.id_btn_pay);
        mTvCount = (TextView)findViewById(R.id.id_tv_count);
        mRecyclerView = (RecyclerView)findViewById(R.id.id_recyclerview);

        //设置控件，上拉，下拉都支持
        mSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.GREEN,Color.YELLOW);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mProductBiz.onDestory();

    }
}
