package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AddByOrderRespon {
    /**
     * flag : 1
     * orderPay : [{"Id":null,"OrderId":2016041966017062,"PayId":0,"PayState":false,"PayTime":null}]
     * Amount : 15
     */

    private int flag;
    private int Amount;
    /**
     * Id : null
     * OrderId : 2016041966017062
     * PayId : 0
     * PayState : false
     * PayTime : null
     */

    private List<OrderPayEntity> orderPay;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public void setOrderPay(List<OrderPayEntity> orderPay) {
        this.orderPay = orderPay;
    }

    public int getFlag() {
        return flag;
    }

    public int getAmount() {
        return Amount;
    }

    public List<OrderPayEntity> getOrderPay() {
        return orderPay;
    }

    public static class OrderPayEntity {
        private Object Id;
        private long OrderId;
        private int PayId;
        private boolean PayState;
        private Object PayTime;

        public void setId(Object Id) {
            this.Id = Id;
        }

        public void setOrderId(long OrderId) {
            this.OrderId = OrderId;
        }

        public void setPayId(int PayId) {
            this.PayId = PayId;
        }

        public void setPayState(boolean PayState) {
            this.PayState = PayState;
        }

        public void setPayTime(Object PayTime) {
            this.PayTime = PayTime;
        }

        public Object getId() {
            return Id;
        }

        public long getOrderId() {
            return OrderId;
        }

        public int getPayId() {
            return PayId;
        }

        public boolean isPayState() {
            return PayState;
        }

        public Object getPayTime() {
            return PayTime;
        }
    }
}
