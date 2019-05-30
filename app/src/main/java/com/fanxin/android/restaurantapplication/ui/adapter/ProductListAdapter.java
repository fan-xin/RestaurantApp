package com.fanxin.android.restaurantapplication.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanxin.android.restaurantapplication.bean.Product;
import com.fanxin.android.restaurantapplication.vo.ProductItem;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListItemViewHolder productListItemViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mProductItems.size();
    }


    class ProductListItemViewHolder extends RecyclerView.ViewHolder{


        public ProductListItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
