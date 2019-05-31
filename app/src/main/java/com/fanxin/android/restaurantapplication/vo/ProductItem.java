package com.fanxin.android.restaurantapplication.vo;

import com.fanxin.android.restaurantapplication.bean.Product;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/30  11:56
 */
public class ProductItem extends Product {
    public int count;

    public ProductItem(Product product){

        this.id = product.getId();
        this.name = product.getName();
        this.label = product.getLabel();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.icon = product.getIcon();
        this.restaurant = product.getRestaurant();

    }


}
