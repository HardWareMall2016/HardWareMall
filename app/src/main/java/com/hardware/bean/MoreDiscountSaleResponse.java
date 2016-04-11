package com.hardware.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 * 更多折扣特卖
 */
public class MoreDiscountSaleResponse implements Serializable {

    /**
     * flag : 1
     * message : {"rows":[{"id":4261,"imgUrl":"/Storage/Shop/344/Products/4261/1_220.png","ProductName":"加硬镀彩锌 纤维板钉 沉头自攻螺丝钉","MarketPrice":0.08,"MinSalePrice":0,"productCode":"","MeasureUnit":"只","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4259,"imgUrl":"/Storage/Shop/332/Products/4259/1_220.png","ProductName":"代尔塔防护服 防化服 防化学飞溅 连体服 防粉尘 喷漆服 406115 白色","MarketPrice":22,"MinSalePrice":0,"productCode":"","MeasureUnit":"件","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4258,"imgUrl":"/Storage/Shop/344/Products/4258/1_220.png","ProductName":"镍圆头带垫自攻螺丝钉PWA盘头尖尾","MarketPrice":0.04,"MinSalePrice":0,"productCode":"","MeasureUnit":"只","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4253,"imgUrl":"/Storage/Shop/332/Products/4253/1_220.png","ProductName":"代尔塔 牛皮焊工围裙 防护服 牛皮围裙 耐高温405015 灰色","MarketPrice":85,"MinSalePrice":0,"productCode":"","MeasureUnit":"件","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4247,"imgUrl":"/Storage/Shop/340/Products/4247/1_220.png","ProductName":"日丰管防爆金属软管 不锈钢丝编织管厨房冷热水龙头进水管40/60cm","MarketPrice":15,"MinSalePrice":0,"productCode":"","MeasureUnit":"厘米","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4246,"imgUrl":"/Storage/Shop/334/Products/4246/1_220.png","ProductName":"LYC 深沟球轴承 61840 MC3(3G1000840H)","MarketPrice":633,"MinSalePrice":0,"productCode":"","MeasureUnit":"个","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4160,"imgUrl":"/Storage/Shop/336/Products/4160/1_220.png","ProductName":"德力西行程开关LXW6-11W1万向长杆式微动开关 360度万向长杆式小型行程开关","MarketPrice":15,"MinSalePrice":0,"productCode":"","MeasureUnit":"米","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"}],"total":0}
     */

    private int flag;
    /**
     * rows : [{"id":4261,"imgUrl":"/Storage/Shop/344/Products/4261/1_220.png","ProductName":"加硬镀彩锌 纤维板钉 沉头自攻螺丝钉","MarketPrice":0.08,"MinSalePrice":0,"productCode":"","MeasureUnit":"只","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4259,"imgUrl":"/Storage/Shop/332/Products/4259/1_220.png","ProductName":"代尔塔防护服 防化服 防化学飞溅 连体服 防粉尘 喷漆服 406115 白色","MarketPrice":22,"MinSalePrice":0,"productCode":"","MeasureUnit":"件","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4258,"imgUrl":"/Storage/Shop/344/Products/4258/1_220.png","ProductName":"镍圆头带垫自攻螺丝钉PWA盘头尖尾","MarketPrice":0.04,"MinSalePrice":0,"productCode":"","MeasureUnit":"只","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4253,"imgUrl":"/Storage/Shop/332/Products/4253/1_220.png","ProductName":"代尔塔 牛皮焊工围裙 防护服 牛皮围裙 耐高温405015 灰色","MarketPrice":85,"MinSalePrice":0,"productCode":"","MeasureUnit":"件","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4247,"imgUrl":"/Storage/Shop/340/Products/4247/1_220.png","ProductName":"日丰管防爆金属软管 不锈钢丝编织管厨房冷热水龙头进水管40/60cm","MarketPrice":15,"MinSalePrice":0,"productCode":"","MeasureUnit":"厘米","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4246,"imgUrl":"/Storage/Shop/334/Products/4246/1_220.png","ProductName":"LYC 深沟球轴承 61840 MC3(3G1000840H)","MarketPrice":633,"MinSalePrice":0,"productCode":"","MeasureUnit":"个","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"},{"id":4160,"imgUrl":"/Storage/Shop/336/Products/4160/1_220.png","ProductName":"德力西行程开关LXW6-11W1万向长杆式微动开关 360度万向长杆式小型行程开关","MarketPrice":15,"MinSalePrice":0,"productCode":"","MeasureUnit":"米","SaleCounts":0,"CompanyAddress":"江苏省苏州市常熟市"}]
     * total : 0
     */

    private MessageBean message;

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

    public static class MessageBean {
        private int total;
        /**
         * id : 4261
         * imgUrl : /Storage/Shop/344/Products/4261/1_220.png
         * ProductName : 加硬镀彩锌 纤维板钉 沉头自攻螺丝钉
         * MarketPrice : 0.08
         * MinSalePrice : 0
         * productCode :
         * MeasureUnit : 只
         * SaleCounts : 0
         * CompanyAddress : 江苏省苏州市常熟市
         */

        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private int id;
            private String imgUrl;
            private String ProductName;
            private double MarketPrice;
            private double MinSalePrice;
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

            public double getMinSalePrice() {
                return MinSalePrice;
            }

            public void setMinSalePrice(double MinSalePrice) {
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
