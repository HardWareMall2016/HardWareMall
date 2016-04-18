package com.hardware.bean;

import java.util.List;

/**
 * Created by WuYue on 2016/4/18.
 */
public class GetCountyResponseBean {

    /**
     * success : true
     * status : 0
     * msg : [{"CountyId":102,"CountyName":"上城区"},{"CountyId":103,"CountyName":"下城区"},{"CountyId":104,"CountyName":"江干区"},{"CountyId":105,"CountyName":"拱墅区"},{"CountyId":106,"CountyName":"西湖区"},{"CountyId":107,"CountyName":"滨江区"},{"CountyId":108,"CountyName":"萧山区"},{"CountyId":109,"CountyName":"余杭区"},{"CountyId":110,"CountyName":"桐庐县"},{"CountyId":111,"CountyName":"淳安县"},{"CountyId":112,"CountyName":"建德市"},{"CountyId":113,"CountyName":"富阳市"},{"CountyId":114,"CountyName":"临安市"},{"CountyId":115,"CountyName":"其他区"}]
     */

    private boolean success;
    private int status;
    /**
     * CountyId : 102
     * CountyName : 上城区
     */

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
        private int CountyId;
        private String CountyName;

        public int getCountyId() {
            return CountyId;
        }

        public void setCountyId(int CountyId) {
            this.CountyId = CountyId;
        }

        public String getCountyName() {
            return CountyName;
        }

        public void setCountyName(String CountyName) {
            this.CountyName = CountyName;
        }
    }
}
