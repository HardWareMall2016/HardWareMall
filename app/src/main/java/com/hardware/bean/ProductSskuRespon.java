package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ProductSskuRespon {
    /**
     * flag : 1
     * message : {"SKUId":"4259_0_0_0","Version":"","Color":"","Size":"","SalePrice":22,"Stock":10}
     */

    private int flag;
    /**
     * SKUId : 4259_0_0_0
     * Version :
     * Color :
     * Size :
     * SalePrice : 22.0
     * Stock : 10
     */

    private MessageEntity message;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public static class MessageEntity {
        private String SKUId;
        private String Version;
        private String Color;
        private String Size;
        private double SalePrice;
        private int Stock;

        public void setSKUId(String SKUId) {
            this.SKUId = SKUId;
        }

        public void setVersion(String Version) {
            this.Version = Version;
        }

        public void setColor(String Color) {
            this.Color = Color;
        }

        public void setSize(String Size) {
            this.Size = Size;
        }

        public void setSalePrice(double SalePrice) {
            this.SalePrice = SalePrice;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }

        public String getSKUId() {
            return SKUId;
        }

        public String getVersion() {
            return Version;
        }

        public String getColor() {
            return Color;
        }

        public String getSize() {
            return Size;
        }

        public double getSalePrice() {
            return SalePrice;
        }

        public int getStock() {
            return Stock;
        }
    }
}
