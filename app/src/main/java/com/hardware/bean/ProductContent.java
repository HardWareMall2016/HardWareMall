package com.hardware.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ProductContent implements Serializable {
    private int id ;
    private String district ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
