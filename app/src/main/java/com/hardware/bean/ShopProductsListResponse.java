package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ShopProductsListResponse {

    /**
     * flag : 1
     * shops : {"shopsId":262,"ShopName":"月兔厨具专卖店","Attention":21,"CreateDate":"2016-03-11","GradName":"白金店铺"}
     * message : [{"Id":1335,"imgUrl":"/Storage/Shop/262/Products/1335/1_220.png","name":" 环保 静音客厅门书房门卧室门","SaleCounts":0,"MarketPrice":1699},{"Id":1330,"imgUrl":"/Storage/Shop/262/Products/1330/1_220.png","name":"静音客厅门书房门卧室门","SaleCounts":0,"MarketPrice":1599},{"Id":1326,"imgUrl":"/Storage/Shop/262/Products/1326/1_220.png","name":"实木门 室内门 套装门 书房门卧室门 烤漆门","SaleCounts":0,"MarketPrice":1799},{"Id":1323,"imgUrl":"/Storage/Shop/262/Products/1323/1_220.png","name":"室内门 房门 双开木门","SaleCounts":0,"MarketPrice":1762},{"Id":1318,"imgUrl":"/Storage/Shop/262/Products/1318/1_220.png","name":" 实木门 卧室门 室内门 卫生间厨房门 玻璃木门","SaleCounts":0,"MarketPrice":1480},{"Id":1313,"imgUrl":"/Storage/Shop/262/Products/1313/1_220.png","name":"钢木门 室内门 套装门 房间门 卧室门 房门","SaleCounts":0,"MarketPrice":2580},{"Id":1306,"imgUrl":"/Storage/Shop/262/Products/1306/1_220.png","name":"室内门 卧室门 厨房门 静音门","SaleCounts":0,"MarketPrice":10770},{"Id":1297,"imgUrl":"/Storage/Shop/262/Products/1297/1_220.png","name":"集成吊顶铝扣板模块 纳米抗油污天花板吊顶 厨房卫生间装饰材料","SaleCounts":0,"MarketPrice":12},{"Id":1291,"imgUrl":"/Storage/Shop/262/Products/1291/1_220.png","name":"集成吊顶厨房卫生间客厅卧室镜面纳米抗油污铝扣板","SaleCounts":0,"MarketPrice":9},{"Id":1276,"imgUrl":"/Storage/Shop/262/Products/1276/1_220.png","name":"集成吊顶 铝扣板 厨房卫生间吊顶","SaleCounts":0,"MarketPrice":99}]
     */

    private int flag;
    /**
     * shopsId : 262
     * ShopName : 月兔厨具专卖店
     * Attention : 21
     * CreateDate : 2016-03-11
     * GradName : 白金店铺
     */

    private ShopsEntity shops;
    /**
     * Id : 1335
     * imgUrl : /Storage/Shop/262/Products/1335/1_220.png
     * name :  环保 静音客厅门书房门卧室门
     * SaleCounts : 0
     * MarketPrice : 1699
     */

    private List<MessageEntity> message;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public ShopsEntity getShops() {
        return shops;
    }

    public void setShops(ShopsEntity shops) {
        this.shops = shops;
    }

    public List<MessageEntity> getMessage() {
        return message;
    }

    public void setMessage(List<MessageEntity> message) {
        this.message = message;
    }

    public static class ShopsEntity {
        private int shopsId;
        private String ShopName;
        private int Attention;
        private String CreateDate;
        private String GradName;

        public int getShopsId() {
            return shopsId;
        }

        public void setShopsId(int shopsId) {
            this.shopsId = shopsId;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public int getAttention() {
            return Attention;
        }

        public void setAttention(int Attention) {
            this.Attention = Attention;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getGradName() {
            return GradName;
        }

        public void setGradName(String GradName) {
            this.GradName = GradName;
        }
    }

    public static class MessageEntity {
        private int Id;
        private String imgUrl;
        private String name;
        private int SaleCounts;
        private float MarketPrice;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSaleCounts() {
            return SaleCounts;
        }

        public void setSaleCounts(int SaleCounts) {
            this.SaleCounts = SaleCounts;
        }

        public float getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(float MarketPrice) {
            this.MarketPrice = MarketPrice;
        }
    }
}
