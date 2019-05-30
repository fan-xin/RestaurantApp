package com.fanxin.android.restaurantapplication.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.bean.Product;
import com.fanxin.android.restaurantapplication.biz.ProductBiz;
import com.fanxin.android.restaurantapplication.net.CommonCallback;
import com.fanxin.android.restaurantapplication.ui.adapter.ProductListAdapter;
import com.fanxin.android.restaurantapplication.ui.view.refresh.SwipeRefresh;
import com.fanxin.android.restaurantapplication.ui.view.refresh.SwipeRefreshLayout;
import com.fanxin.android.restaurantapplication.utils.T;
import com.fanxin.android.restaurantapplication.vo.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mTvCount;
    private Button mBtnPay;

    private ProductListAdapter mAdapter;
    private List<ProductItem> mDatas = new ArrayList<>();

    private int mCurrentPage;

    private float mTotalPrice;
    private int mTotalCount;


    private ProductBiz mProductBiz = new ProductBiz();



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


        mAdapter.setOnProductListener(new ProductListAdapter.OnProductListener() {
            @Override
            public void onProductAdd(ProductItem productItem) {
                mTotalCount++;
                mTvCount.setText("数量:"+mTotalCount);
                mTotalPrice += productItem.getPrice();
                mBtnPay.setText(String.format("%5.2f",mTotalPrice)+"元　立即支付");
            }

            @Override
            public void onProductSub(ProductItem productItem) {
                mTotalCount--;
                mTvCount.setText("数量:"+mTotalCount);
                mTotalPrice -= productItem.getPrice();

                //表示金额之前，要处理金额为显示小数点后两位

                mBtnPay.setText(String.format("%5.2f",mTotalPrice)+"元　立即支付");

            }
        });


    }

    private void loadMore() {
        startLoadingProgress();
        mProductBiz.listByPage(++mCurrentPage, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mCurrentPage--;
                mSwipeRefreshLayout.setPullUpRefreshing(false);

            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                mSwipeRefreshLayout.setPullUpRefreshing(false);

                if (response.size() == 0){
                    T.showToast("没有咯");
                    return;
                }

                T.showToast("又找到"+response.size()+"道菜");

                for (Product p: response){
                    mDatas.add(new ProductItem(p));
                }

                mAdapter.notifyDataSetChanged();

            }
        });

    }

    private void loadDatas() {
        startLoadingProgress();
        mProductBiz.listByPage(0, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                T.showToast(e.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                mSwipeRefreshLayout.setRefreshing(false);
                mCurrentPage = 0;

                mDatas.clear();

                for (Product p: response){
                    mDatas.add(new ProductItem(p));
                }

                mAdapter.notifyDataSetChanged();

                //清空选择的数据，数量价格
                mTotalPrice = 0;
                mTotalCount = 0;
            }
        });

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

        mAdapter = new ProductListAdapter(this, mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mProductBiz.onDestory();

    }
}
