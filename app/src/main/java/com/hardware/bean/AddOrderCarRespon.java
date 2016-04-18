package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/18.
 */
public class AddOrderCarRespon {
    /**
     * flag : 1
     * message : 添加成功!
     */

    private int flag;
    private String message;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public String getMessage() {
        return message;
    }
}
