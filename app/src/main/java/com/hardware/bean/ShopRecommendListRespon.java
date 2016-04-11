package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ShopRecommendListRespon {
    /**
     * flag : 1
     * message : [{"Id":1335,"imgUrl":"/Storage/Shop/262/Products/1335/1_50.png","name":" 环保 静音客厅门书房门卧室门","SaleCounts":0,"MarketPrice":1699}]
     */

    private int flag;
    /**
     * Id : 1335
     * imgUrl : /Storage/Shop/262/Products/1335/1_50.png
     * name :  环保 静音客厅门书房门卧室门
     * SaleCounts : 0
     * MarketPrice : 1699
     */

    private List<MessageEntity> message;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setMessage(List<MessageEntity> message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public List<MessageEntity> getMessage() {
        return message;
    }

    public static class MessageEntity {
        private int Id;
        private String imgUrl;
        private String name;
        private int SaleCounts;
        private int MarketPrice;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSaleCounts(int SaleCounts) {
            this.SaleCounts = SaleCounts;
        }

        public void setMarketPrice(int MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public int getId() {
            return Id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getName() {
            return name;
        }

        public int getSaleCounts() {
            return SaleCounts;
        }

        public int getMarketPrice() {
            return MarketPrice;
        }
    }
}
