package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 16/4/17.
 */
public class CartOrderAddressResponse {

    /**
     * flag : 1
     * status : 0
     * success : Ture
     * msg : [{"Address":"","AddressId":0,"IsDefault":"true","ReceiverPerson":"123","ReceiverPhone":"123"}]
     */

    //private int flag;
    private int status;
    private String success;
    /**
     * Address :
     * AddressId : 0
     * IsDefault : true
     * ReceiverPerson : 123
     * ReceiverPhone : 123
     */

    private List<MsgBean> msg;
//
//    public int getFlag() {
//        return flag;
//    }
//
//    public void setFlag(int flag) {
//        this.flag = flag;
//    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        private String Address;
        private int AddressId;
        private String IsDefault;
        private String ReceiverPerson;
        private String ReceiverPhone;

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public int getAddressId() {
            return AddressId;
        }

        public void setAddressId(int AddressId) {
            this.AddressId = AddressId;
        }

        public String getIsDefault() {
            return IsDefault;
        }

        public void setIsDefault(String IsDefault) {
            this.IsDefault = IsDefault;
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
    }
}
