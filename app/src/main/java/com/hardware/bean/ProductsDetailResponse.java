package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class ProductsDetailResponse {

    /**
     * flag : 1
     * message : {"Color":[{"Color":"红色"}],"Size":null,"Version":null,"Description":"<p>LYC 深沟球轴承 61840 MC3(3G1000840H)<\/p>","shopid":334,"ProductName":"LYC 深沟球轴承 61840 MC3(3G1000840H)","Logo":null,"CompanyRegionName":"江苏省苏州市常熟市","shopName":"哈尔滨轴承制造有限公司授权经销商常熟销售处","imgUrl":"/Storage/Shop/334/Products/4246/1_350.png","PackMark":5,"CreateDate":0,"DeliveryMark":5,"ServiceMark":5,"commentNumber":0,"commentSum":5,"SaleCount":0,"MarketPrice":633,"urlimges":["/Storage/Shop/334/Products/4246/1_350.png"]}
     * attributes : []
     */

    private int flag;
    /**
     * Color : [{"Color":"红色"}]
     * Size : null
     * Version : null
     * Description : <p>LYC 深沟球轴承 61840 MC3(3G1000840H)</p>
     * shopid : 334
     * ProductName : LYC 深沟球轴承 61840 MC3(3G1000840H)
     * Logo : null
     * CompanyRegionName : 江苏省苏州市常熟市
     * shopName : 哈尔滨轴承制造有限公司授权经销商常熟销售处
     * imgUrl : /Storage/Shop/334/Products/4246/1_350.png
     * PackMark : 5
     * CreateDate : 0
     * DeliveryMark : 5
     * ServiceMark : 5
     * commentNumber : 0
     * commentSum : 5
     * SaleCount : 0
     * MarketPrice : 633
     * urlimges : ["/Storage/Shop/334/Products/4246/1_350.png"]
     */

    private MessageBean message;
    private List<?> attributes;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public List<?> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<?> attributes) {
        this.attributes = attributes;
    }

    public static class MessageBean {
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
        private int MarketPrice;
        /**
         * Color : 红色
         */

        private List<ColorBean> Color;
        private List<String> urlimges;

        public String getSize() {
            return Size;
        }

        public void setSize(String Size) {
            this.Size = Size;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String Version) {
            this.Version = Version;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getShopid() {
            return shopid;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getCompanyRegionName() {
            return CompanyRegionName;
        }

        public void setCompanyRegionName(String CompanyRegionName) {
            this.CompanyRegionName = CompanyRegionName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getPackMark() {
            return PackMark;
        }

        public void setPackMark(int PackMark) {
            this.PackMark = PackMark;
        }

        public int getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(int CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getDeliveryMark() {
            return DeliveryMark;
        }

        public void setDeliveryMark(int DeliveryMark) {
            this.DeliveryMark = DeliveryMark;
        }

        public int getServiceMark() {
            return ServiceMark;
        }

        public void setServiceMark(int ServiceMark) {
            this.ServiceMark = ServiceMark;
        }

        public int getCommentNumber() {
            return commentNumber;
        }

        public void setCommentNumber(int commentNumber) {
            this.commentNumber = commentNumber;
        }

        public int getCommentSum() {
            return commentSum;
        }

        public void setCommentSum(int commentSum) {
            this.commentSum = commentSum;
        }

        public int getSaleCount() {
            return SaleCount;
        }

        public void setSaleCount(int SaleCount) {
            this.SaleCount = SaleCount;
        }

        public int getMarketPrice() {
            return MarketPrice;
        }

        public void setMarketPrice(int MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public List<ColorBean> getColor() {
            return Color;
        }

        public void setColor(List<ColorBean> Color) {
            this.Color = Color;
        }

        public List<String> getUrlimges() {
            return urlimges;
        }

        public void setUrlimges(List<String> urlimges) {
            this.urlimges = urlimges;
        }

        public static class ColorBean {
            private String Color;

            public String getColor() {
                return Color;
            }

            public void setColor(String Color) {
                this.Color = Color;
            }
        }
    }
}
