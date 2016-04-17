package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/17.
 */
public class DefResponseBean2 {

    /**
     * msg : 购物车产品移到收藏夹成功！
     * status : 0
     * success : true
     */

    private String msg;
    private int status;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
