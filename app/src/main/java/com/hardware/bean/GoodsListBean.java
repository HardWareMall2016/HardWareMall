package com.hardware.bean;

import java.util.List;

/**
 * Created by hover on 2016/4/16.
 */
public class GoodsListBean {

    /**
     * flag : 1
     * message : [{"id":13432,"imgUrl":"/Storage/Shop/460/Products/13432/1_220.png","ProductName":"东成修边机M1P-FF-6木工铣刀小锣机修边倒角电动工具3701铝体修边机 M1P-FF-6(标配","MarketPrice":1000,"MinSalePrice":0,"productCode":"","MeasureUnit":"台","SaleCounts":0,"CompanyAddress":"上海上海市黄浦区"}]
     */

    private int flag;
    /**
     * id : 13432
     * imgUrl : /Storage/Shop/460/Products/13432/1_220.png
     * ProductName : 东成修边机M1P-FF-6木工铣刀小锣机修边倒角电动工具3701铝体修边机 M1P-FF-6(标配
     * MarketPrice : 1000.0
     * MinSalePrice : 0
     * productCode :
     * MeasureUnit : 台
     * SaleCounts : 0
     * CompanyAddress : 上海上海市黄浦区
     */

    private List<GoodsInfo> message;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setMessage(List<GoodsInfo> message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public List<GoodsInfo> getMessage() {
        return message;
    }

    public static class GoodsInfo {
        private int id;
        private String imgUrl;
        private String ProductName;
        private double MarketPrice;
        private int MinSalePrice;
        private String productCode;
        private String MeasureUnit;
        private int SaleCounts;
        private String CompanyAddress;

        public void setId(int id) {
            this.id = id;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public void setMarketPrice(double MarketPrice) {
            this.MarketPrice = MarketPrice;
        }

        public void setMinSalePrice(int MinSalePrice) {
            this.MinSalePrice = MinSalePrice;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public void setMeasureUnit(String MeasureUnit) {
            this.MeasureUnit = MeasureUnit;
        }

        public void setSaleCounts(int SaleCounts) {
            this.SaleCounts = SaleCounts;
        }

        public void setCompanyAddress(String CompanyAddress) {
            this.CompanyAddress = CompanyAddress;
        }

        public int getId() {
            return id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getProductName() {
            return ProductName;
        }

        public double getMarketPrice() {
            return MarketPrice;
        }

        public int getMinSalePrice() {
            return MinSalePrice;
        }

        public String getProductCode() {
            return productCode;
        }

        public String getMeasureUnit() {
            return MeasureUnit;
        }

        public int getSaleCounts() {
            return SaleCounts;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }
    }
}
