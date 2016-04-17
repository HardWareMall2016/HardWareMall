package com.hardware.bean;

import java.util.List;

/**
 * Created by WuYue on 2016/4/11.
 */
public class ShopListResponseBean {
    private int flag;
    private List<MessageBean> message;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        private int Id;
        private String ShopGrade;
        private String ShopName;
        private String BusinessSphere;
        private String Logo;
        private String CompanyName;
        private int CompanyRegionId;
        private String CompanyAddress;
        private int CreateDate;
        private int ProductNumbere;
        private int orderProduct;
        private int buyerNumber;
        private String distance;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getShopGrade() {
            return ShopGrade;
        }

        public void setShopGrade(String ShopGrade) {
            this.ShopGrade = ShopGrade;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getBusinessSphere() {
            return BusinessSphere;
        }

        public void setBusinessSphere(String BusinessSphere) {
            this.BusinessSphere = BusinessSphere;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public int getCompanyRegionId() {
            return CompanyRegionId;
        }

        public void setCompanyRegionId(int CompanyRegionId) {
            this.CompanyRegionId = CompanyRegionId;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public void setCompanyAddress(String CompanyAddress) {
            this.CompanyAddress = CompanyAddress;
        }

        public int getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(int CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getProductNumbere() {
            return ProductNumbere;
        }

        public void setProductNumbere(int ProductNumbere) {
            this.ProductNumbere = ProductNumbere;
        }

        public int getOrderProduct() {
            return orderProduct;
        }

        public void setOrderProduct(int orderProduct) {
            this.orderProduct = orderProduct;
        }

        public int getBuyerNumber() {
            return buyerNumber;
        }

        public void setBuyerNumber(int buyerNumber) {
            this.buyerNumber = buyerNumber;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
