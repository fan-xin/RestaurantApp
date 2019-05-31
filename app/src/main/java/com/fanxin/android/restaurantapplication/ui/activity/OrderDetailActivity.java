package com.fanxin.android.restaurantapplication.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.bean.Order;
import com.fanxin.android.restaurantapplication.config.Config;
import com.fanxin.android.restaurantapplication.utils.T;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailActivity extends BaseActivity {

    private Order mOrder;
    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvPrice;

    private static final String KEY_ORDER = "key_order";

    public static void launch(Context context, Order order){
        Intent intent = new Intent(context,OrderDetailActivity.class);
        intent.putExtra(KEY_ORDER,order);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        setupToolbar();

        setTitle("订单详情");

        Intent intent = getIntent();
        if (intent != null){
            mOrder = (Order) intent.getSerializableExtra(KEY_ORDER);
        }
        if (mOrder == null){
            T.showToast("出错了");
            finish();
            return;
        }

        initView();

        initEvent();
    }

    private void initEvent() {
        Picasso.get()
                .load(Config.baseUrl+mOrder.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(mIvImage);
        mTvTitle.setText(mOrder.getRestaurant().getName());

        mTvPrice.setText("共消费: "+mOrder.getPrice()+"元");

        //拼接订单详情
        List<Order.ProductVo> ps = mOrder.getPs();
        StringBuilder sb = new StringBuilder();
        for (Order.ProductVo productVo : ps){
            sb.append(productVo.product.getName())
                    .append("*")
                    .append(productVo.count)
                    .append("\n");
        }
        mTvDesc.setText(sb.toString());
    }

    private void initView() {
        mIvImage = (ImageView)findViewById(R.id.id_iv_image);
        mTvTitle = (TextView)findViewById(R.id.id_tv_title);
        mTvDesc = (TextView)findViewById(R.id.id_tv_desc);
        mTvPrice = (TextView)findViewById(R.id.id_tv_price);
    }

}
