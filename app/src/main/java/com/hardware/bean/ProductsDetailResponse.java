package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class ProductsDetailResponse {
    /**
     * flag : 1
     * message : {"Color":null,"Size":null,"Version":null,"Description":"<h1 style=\"font: 700 16px/normal &quot;microsoft yahei&quot;; margin: 0px; padding: 0px 0px 0.2em; color: rgb(0, 0, 0); text-transform: none; text-indent: 0px; letter-spacing: normal; word-spacing: 0px; white-space: normal; font-size-adjust: none; font-stretch: normal; background-color: rgb(255, 255, 255); -webkit-text-stroke-width: 0px;\" data-spm=\"1000983\"><span class=\"Apple-converted-space\">&nbsp;<\/span>环保 静音客厅门书房门卧室门<\/h1><p><\/p>","shopid":262,"ProductName":" 环保 静音客厅门书房门卧室门","Logo":"/Storage/Shop/262/ImageAd/logo.png","CompanyRegionName":"江苏省南通市启东市","shopName":"月兔厨具启东专卖店","imgUrl":"/Storage/Shop/262/Products/1335/1_350.png","PackMark":5,"CreateDate":0,"DeliveryMark":5,"ServiceMark":5,"commentNumber":0,"commentSum":5,"SaleCount":0,"MarketPrice":1699}
     * attributes : []
     */

    private int flag;
    /**
     * Color : null
     * Size : null
     * Version : null
     * Description : <h1 style="font: 700 16px/normal &quot;microsoft yahei&quot;; margin: 0px; padding: 0px 0px 0.2em; color: rgb(0, 0, 0); text-transform: none; text-indent: 0px; letter-spacing: normal; word-spacing: 0px; white-space: normal; font-size-adjust: none; font-stretch: normal; background-color: rgb(255, 255, 255); -webkit-text-stroke-width: 0px;" data-spm="1000983"><span class="Apple-converted-space">&nbsp;</span>环保 静音客厅门书房门卧室门</h1><p></p>
     * shopid : 262
     * ProductName :  环保 静音客厅门书房门卧室门
     * Logo : /Storage/Shop/262/ImageAd/logo.png
     * CompanyRegionName : 江苏省南通市启东市
     * shopName : 月兔厨具启东专卖店
     * imgUrl : /Storage/Shop/262/Products/1335/1_350.png
     * PackMark : 5
     * CreateDate : 0
     * DeliveryMark : 5
     * ServiceMark : 5
     * commentNumber : 0
     * commentSum : 5
     * SaleCount : 0
     * MarketPrice : 1699.0
     */

    private MessageEntity message;
    private List<?> attributes;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public void setAttributes(List<?> attributes) {
        this.attributes = attributes;
    }

    public int getFlag() {
        return flag;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public List<?> getAttributes() {
        return attributes;
    }

    public static class MessageEntity {
        private String Color;
        private String Size;
        private String Version;
        private String Description;
        private int shopid;
        private String ProductName;
        private String Logo;
        private String CompanyRegionName;
        private String shopName;
        private String imgUrl;
        private int PackMark;
        private int CreateDate;
        private int DeliveryMark;
        private int ServiceMark;
        private int commentNumber;
        private int commentSum;
        private int SaleCount;
        private double MarketPrice;

        public void setColor(String Color) {
            this.Color = Color;
        }

        public void setSize(String Size) {
            this.Size = Size;
        }

        public void setVersion(String Version) {
            this.Version = Version;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public void setCompanyRegionName(String CompanyRegionName) {
            this.CompanyRegionName = CompanyRegionName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setPackMark(int PackMark) {
            this.PackMark = PackMark;
        }

        public void setCreateDate(int CreateDate) {
            this.CreateDate = CreateDate;
        }

        public void setDeliveryMark(int DeliveryMark) {
            this.DeliveryMark = DeliveryMark;
        }

        public void setServiceMark(int ServiceMark) {
            this.ServiceMark = ServiceMark;
        }

        public void setCommentNumber(int commentNumber) {
            this.commentNumber = commentNumber;
        }

        public void setCommentSum(int commentSum) {
            this.commentSum = commentSum;
        }

        public void setSaleCount(int SaleCount) {
            this.SaleCount = SaleCount;
        }

        public void setMarketPrice(double MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public String getColor() {
            return Color;
        }

        public String getSize() {
            return Size;
        }

        public String getVersion() {
            return Version;
        }

        public String getDescription() {
            return Description;
        }

        public int getShopid() {
            return shopid;
        }

        public String getProductName() {
            return ProductName;
        }

        public String getLogo() {
            return Logo;
        }

        public String getCompanyRegionName() {
            return CompanyRegionName;
        }

        public String getShopName() {
            return shopName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public int getPackMark() {
            return PackMark;
        }

        public int getCreateDate() {
            return CreateDate;
        }

        public int getDeliveryMark() {
            return DeliveryMark;
        }

        public int getServiceMark() {
            return ServiceMark;
        }

        public int getCommentNumber() {
            return commentNumber;
        }

        public int getCommentSum() {
            return commentSum;
        }

        public int getSaleCount() {
            return SaleCount;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }
    }
}
