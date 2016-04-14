package com.hardware.bean;

import java.util.List;

/**
 * Created by WuYue on 2016/4/14.
 */
public class DiscountProductsResponse {
    private int flag;
    private MessageEntity message;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public static class MessageEntity {
        private int total;
        /**
         * id : 4256
         * imgUrl : /Storage/Shop/343/Products/4256/1_220.png
         * ProductName : 艾瑞泽彩白锌拉铆螺母铆钉拉铆枪拉帽平头滚花柱纹铆螺母铆螺帽
         * MarketPrice : 2.6
         * MinSalePrice : 0
         * productCode :
         * MeasureUnit : 个
         * SaleCounts : 0
         * CompanyAddress : 江苏省苏州市常熟市
         */

        private List<RowsEntity> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsEntity> getRows() {
            return rows;
        }

        public void setRows(List<RowsEntity> rows) {
            this.rows = rows;
        }

        public static class RowsEntity {
            private int id;
            private String imgUrl;
            private String ProductName;
            private double MarketPrice;
            private int MinSalePrice;
            private String productCode;
            private String MeasureUnit;
            private int SaleCounts;
            private String CompanyAddress;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getProductName() {
                return ProductName;
            }

            public void setProductName(String ProductName) {
                this.ProductName = ProductName;
            }

            public double getMarketPrice() {
                return MarketPrice;
            }

            public void setMarketPrice(double MarketPrice) {
                this.MarketPrice = MarketPrice;
            }

            public int getMinSalePrice() {
                return MinSalePrice;
            }

            public void setMinSalePrice(int MinSalePrice) {
                this.MinSalePrice = MinSalePrice;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getMeasureUnit() {
                return MeasureUnit;
            }

            public void setMeasureUnit(String MeasureUnit) {
                this.MeasureUnit = MeasureUnit;
            }

            public int getSaleCounts() {
                return SaleCounts;
            }

            public void setSaleCounts(int SaleCounts) {
                this.SaleCounts = SaleCounts;
            }

            public String getCompanyAddress() {
                return CompanyAddress;
            }

            public void setCompanyAddress(String CompanyAddress) {
                this.CompanyAddress = CompanyAddress;
            }
        }
    }
}
