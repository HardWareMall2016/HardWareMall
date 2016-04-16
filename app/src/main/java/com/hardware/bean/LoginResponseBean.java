package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/16.
 */
public class LoginResponseBean {
    private boolean success;
    private int status;
    private MsgBean msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        private String Token;

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }
    }
}
