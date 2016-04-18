package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 16/4/17.
 */
public class CartOrderAddressResponse {

    private boolean success;
    private int status;
    private List<AddressInfo> msg;

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

    public List<AddressInfo> getMsg() {
        return msg;
    }

    public void setMsg(List<AddressInfo> msg) {
        this.msg = msg;
    }

    public static class AddressInfo {
        private int AddressId;
        private String ReceiverPerson;
        private String ReceiverPhone;
        private String Address;
        private boolean IsDefault;
        private boolean isSelected=false;

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

        public boolean isSelected() {
            return isSelected;
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }
    }
}
