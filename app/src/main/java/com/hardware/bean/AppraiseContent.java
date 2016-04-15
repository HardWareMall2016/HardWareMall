package com.hardware.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/14.
 */
public class AppraiseContent implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentSum() {
        return CommentSum;
    }

    public void setCommentSum(int commentSum) {
        CommentSum = commentSum;
    }

    public int getPackMark() {
        return PackMark;
    }

    public void setPackMark(int packMark) {
        PackMark = packMark;
    }

    public int getServiceMark() {
        return ServiceMark;
    }

    public void setServiceMark(int serviceMark) {
        ServiceMark = serviceMark;
    }

    public int getDeliveryMark() {
        return DeliveryMark;
    }

    public void setDeliveryMark(int deliveryMark) {
        DeliveryMark = deliveryMark;
    }

    private int id ;
    private int CommentSum ;
    private int PackMark ;
    private int ServiceMark ;
    private int DeliveryMark ;
}
