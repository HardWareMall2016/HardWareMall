package com.hardware.bean;

import java.util.List;

/**
 * Created by WuYue on 2016/4/18.
 */
public class GetProvinceReponseBean {

    private boolean success;
    private int status;

    private List<MsgEntity> msg;

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

    public List<MsgEntity> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgEntity> msg) {
        this.msg = msg;
    }

    public static class MsgEntity {
        private int ProvinceId;
        private String ProvincName;

        public int getProvinceId() {
            return ProvinceId;
        }

        public void setProvinceId(int ProvinceId) {
            this.ProvinceId = ProvinceId;
        }

        public String getProvincName() {
            return ProvincName;
        }

        public void setProvincName(String ProvincName) {
            this.ProvincName = ProvincName;
        }
    }
}
