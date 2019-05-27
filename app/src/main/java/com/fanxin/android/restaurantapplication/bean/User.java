package com.fanxin.android.restaurantapplication.bean;

/**
 * Created by Fan Xin <fanxin.hit@gmail.com>
 * 19/05/27  16:55
 */
public class User {
    private int id;
    private String password;
    private String username;
    private int icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
