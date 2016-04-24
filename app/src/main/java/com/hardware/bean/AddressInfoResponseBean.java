package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/24.
 */
public class AddressInfoResponseBean {

    /**
     * success : true
     * status : 0
     * msg : {"AddressId":73,"ReceiverPerson":"都匀","ReceiverPhone":"4654696","RegionId":102,"RegionIdPath":"浙江省 杭州市 上城区","Address":"的回家的感觉","IsDefault":false}
     */

    private boolean success;
    private int status;
    /**
     * AddressId : 73
     * ReceiverPerson : 都匀
     * ReceiverPhone : 4654696
     * RegionId : 102
     * RegionIdPath : 浙江省 杭州市 上城区
     * Address : 的回家的感觉
     * IsDefault : false
     */

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
        private int AddressId;
        private String ReceiverPerson;
        private String ReceiverPhone;
        private int RegionId;
        private String RegionIdPath;
        private String Address;
        private boolean IsDefault;

        public int getAddressId() {
            return AddressId;
        }

        public void setAddressId(int AddressId) {
            this.AddressId = AddressId;
        }

        public String getReceiverPerson() {
            return ReceiverPerson;
        }

        public void setReceiverPerson(String ReceiverPerson) {
            this.ReceiverPerson = ReceiverPerson;
        }

        public String getReceiverPhone() {
            return ReceiverPhone;
        }

        public void setReceiverPhone(String ReceiverPhone) {
            this.ReceiverPhone = ReceiverPhone;
        }

        public int getRegionId() {
            return RegionId;
        }

        public void setRegionId(int RegionId) {
            this.RegionId = RegionId;
        }

        public String getRegionIdPath() {
            return RegionIdPath;
        }

        public void setRegionIdPath(String RegionIdPath) {
            this.RegionIdPath = RegionIdPath;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public boolean isIsDefault() {
            return IsDefault;
        }

        public void setIsDefault(boolean IsDefault) {
            this.IsDefault = IsDefault;
        }
    }
}
