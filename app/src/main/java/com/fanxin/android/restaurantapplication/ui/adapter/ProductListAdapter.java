package com.fanxin.android.restaurantapplication.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanxin.android.restaurantapplication.R;
import com.fanxin.android.restaurantapplication.bean.Product;
import com.fanxin.android.restaurantapplication.config.Config;
import com.fanxin.android.restaurantapplication.utils.T;
import com.fanxin.android.restaurantapplication.vo.ProductItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/30  11:51
 */
public class ProductListAdapter
        extends RecyclerView.Adapter<ProductListAdapter.ProductListItemViewHolder> {

    private Context mContext;
    private List<ProductItem> mProductItems;
    private LayoutInflater mInflater;

    public ProductListAdapter(Context context, List<ProductItem> productItems){
        mContext = context;
        mProductItems = productItems;
        mInflater= LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ProductListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.item_product_list, viewGroup, false);
        return new ProductListItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductListItemViewHolder productListItemViewHolder, int i) {
        ProductItem productItem = mProductItems.get(i);
        Picasso.get()
                .load(Config.baseUrl+productItem.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(productListItemViewHolder.mIvImage);

        productListItemViewHolder.mTvName.setText(productItem.getName());
        productListItemViewHolder.mTvCount.setText(productItem.count+"");
        productListItemViewHolder.mTvLabel.setText(productItem.getLabel());
        productListItemViewHolder.mTvPrice.setText(productItem.getPrice()+"元/份");

    }

    @Override
    public int getItemCount() {
        return mProductItems.size();
    }


    //自定义接口
    public interface OnProductListener{
        void onProductAdd(ProductItem productItem);
        void onProductSub(ProductItem productItem);


    }

    //接口
    private OnProductListener mOnProductListener;

    public void setOnProductListener(OnProductListener onProductListener){
        mOnProductListener = onProductListener;
    }

    class ProductListItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView mIvImage;
        public TextView mTvName;
        public TextView mTvLabel;
        public TextView mTvPrice;

        public ImageView mIvAdd;
        public ImageView mIvSub;
        public TextView mTvCount;

        public ProductListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvImage = (ImageView) itemView.findViewById(R.id.id_iv_image);
            mTvName = (TextView) itemView.findViewById(R.id.id_tv_name);
            mTvLabel = (TextView) itemView.findViewById(R.id.id_tv_label);
            mTvPrice = (TextView) itemView.findViewById(R.id.id_tv_price);
            mIvAdd = (ImageView) itemView.findViewById(R.id.id_iv_add);
            mIvSub = (ImageView) itemView.findViewById(R.id.id_iv_sub);
            mTvCount = (TextView) itemView.findViewById(R.id.id_tv_count);


            //点击以后，跳转到商品详情页
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //
            mIvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getLayoutPosition();
                    ProductItem productItem = mProductItems.get(pos);
                    productItem.count++;
                    mTvCount.setText(productItem.count+"");
                    //回调到Activity
                    if (mOnProductListener != null){
                        mOnProductListener.onProductAdd(productItem);
                    }

                }
            });

            mIvSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getLayoutPosition();
                    ProductItem productItem = mProductItems.get(pos);

                    if (productItem.count <= 0){
                        T.showToast("已经是0了！");
                        return;
                    }

                    productItem.count--;
                    mTvCount.setText(productItem.count+"");
                    //回调到Activity
                    if (mOnProductListener != null){
                        mOnProductListener.onProductSub(productItem);
                    }



                }
            });


        }
    }
}
