package com.fanxin.android.restaurantapplication.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.bean.Order;
import com.fanxin.android.restaurantapplication.config.Config;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/29  14:21
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {

    private List<Order> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public OrderAdapter(Context context, List<Order> datas){
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.item_order_list, viewGroup, false);
        return new OrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder orderItemViewHolder, int i) {
        Order order = mDatas.get(i);

        Picasso.get()
                .load(Config.baseUrl+order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(orderItemViewHolder.mIvImage);

        if (order.getPs().size()>0){
            orderItemViewHolder.mTvLabel.setText(order.getPs().get(0).product.getName()+"等"+
                    order.getCount()+"件商品");
        }else {
            orderItemViewHolder.mTvLabel.setText("无消费");
        }

        orderItemViewHolder.mTvName.setText(order.getRestaurant().getName());
        orderItemViewHolder.mTvPrice.setText("共消费"+order.getPrice()+"元");

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //定义内部类，实现泛型
    class OrderItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView mIvImage;
        public TextView mTvName;
        public TextView mTvLabel;
        public TextView mTvPrice;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO


                }
            });

            mIvImage = (ImageView) itemView.findViewById(R.id.id_iv_image);
            mTvName = (TextView) itemView.findViewById(R.id.id_tv_name);
            mTvLabel = (TextView) itemView.findViewById(R.id.id_tv_label);
            mTvPrice = (TextView) itemView.findViewById(R.id.id_tv_price);

        }
    }
}
