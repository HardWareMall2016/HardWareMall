package com.hardware.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ShopContent implements Serializable {
    private int typeId ;
    private String title ;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
