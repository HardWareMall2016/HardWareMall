package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MoreDiscountShopResponse {
    /**
     * flag : 1
     * message : {"rows":[{"Id":262,"ShopGrade":"白金店铺","ShopName":"月兔厨具专卖店","BusinessSphere":"各类门窗、集成吊顶","Logo":"/Storage/Shop/262/ImageAd/logo.png","CompanyName":"月兔厨具启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":21,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":263,"ShopGrade":"白金店铺","ShopName":"万立石膏线","BusinessSphere":"集成吊顶","Logo":"/Storage/Shop/263/ImageAd/logo.png","CompanyName":"万立石膏线","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":16,"orderProduct":8,"buyerNumber":0,"distance":0},{"Id":264,"ShopGrade":"白金店铺","ShopName":"江苏启东市金源木业启东市莫干山饰材专卖店","BusinessSphere":"三夹板","Logo":"/Storage/Shop/264/ImageAd/logo.jpg","CompanyName":"江苏启东市金源木业启东市莫干山饰材专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":26,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":259,"ShopGrade":"白金店铺","ShopName":"ST.THOMAS圣·托玛斯卫浴","BusinessSphere":"卫浴","Logo":"/Storage/Shop/259/ImageAd/logo.jpg","CompanyName":"ST.THOMAS圣·托玛斯卫浴","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":20,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":260,"ShopGrade":"白金店铺","ShopName":"启东市鸿达机电商场","BusinessSphere":"轴承大类","Logo":"/Storage/Shop/260/ImageAd/logo.jpg","CompanyName":"启东市鸿达机电商场","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":1,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":261,"ShopGrade":"白金店铺","ShopName":"启东市宇通机电设备有限公司","BusinessSphere":"数控刀具","Logo":"/Storage/Shop/261/ImageAd/logo.png","CompanyName":"启东市宇通机电设备有限公司","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":34,"orderProduct":1,"buyerNumber":0,"distance":0},{"Id":217,"ShopGrade":"白金店铺","ShopName":"东莞市东城吉祥电动工具店","BusinessSphere":"五金机电。电动工具","Logo":"/Storage/Shop/217/ImageAd/logo.png","CompanyName":"东莞市东城吉祥电动工具店","CompanyRegionId":2088,"CompanyAddress":"广东省东莞市","CreateDate":0,"ProductNumbere":60,"orderProduct":1,"buyerNumber":0,"distance":0},{"Id":250,"ShopGrade":"白金店铺","ShopName":"广东新润成陶瓷启东专卖店","BusinessSphere":"陶瓷","Logo":"/Storage/Shop/250/ImageAd/logo.jpg","CompanyName":"广东新润成陶瓷启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":30,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":251,"ShopGrade":"白金店铺","ShopName":"德国爱亿壁纸","BusinessSphere":"壁纸","Logo":"/Storage/Shop/251/ImageAd/logo.jpg","CompanyName":"德国爱亿壁纸启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":252,"ShopGrade":"白金店铺","ShopName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","BusinessSphere":"陶瓷","Logo":"/Storage/Shop/252/ImageAd/logo.png","CompanyName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":0,"distance":0}],"total":193}
     */

    private int flag;
    /**
     * rows : [{"Id":262,"ShopGrade":"白金店铺","ShopName":"月兔厨具专卖店","BusinessSphere":"各类门窗、集成吊顶","Logo":"/Storage/Shop/262/ImageAd/logo.png","CompanyName":"月兔厨具启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":21,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":263,"ShopGrade":"白金店铺","ShopName":"万立石膏线","BusinessSphere":"集成吊顶","Logo":"/Storage/Shop/263/ImageAd/logo.png","CompanyName":"万立石膏线","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":16,"orderProduct":8,"buyerNumber":0,"distance":0},{"Id":264,"ShopGrade":"白金店铺","ShopName":"江苏启东市金源木业启东市莫干山饰材专卖店","BusinessSphere":"三夹板","Logo":"/Storage/Shop/264/ImageAd/logo.jpg","CompanyName":"江苏启东市金源木业启东市莫干山饰材专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":26,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":259,"ShopGrade":"白金店铺","ShopName":"ST.THOMAS圣·托玛斯卫浴","BusinessSphere":"卫浴","Logo":"/Storage/Shop/259/ImageAd/logo.jpg","CompanyName":"ST.THOMAS圣·托玛斯卫浴","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":20,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":260,"ShopGrade":"白金店铺","ShopName":"启东市鸿达机电商场","BusinessSphere":"轴承大类","Logo":"/Storage/Shop/260/ImageAd/logo.jpg","CompanyName":"启东市鸿达机电商场","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":1,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":261,"ShopGrade":"白金店铺","ShopName":"启东市宇通机电设备有限公司","BusinessSphere":"数控刀具","Logo":"/Storage/Shop/261/ImageAd/logo.png","CompanyName":"启东市宇通机电设备有限公司","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":34,"orderProduct":1,"buyerNumber":0,"distance":0},{"Id":217,"ShopGrade":"白金店铺","ShopName":"东莞市东城吉祥电动工具店","BusinessSphere":"五金机电。电动工具","Logo":"/Storage/Shop/217/ImageAd/logo.png","CompanyName":"东莞市东城吉祥电动工具店","CompanyRegionId":2088,"CompanyAddress":"广东省东莞市","CreateDate":0,"ProductNumbere":60,"orderProduct":1,"buyerNumber":0,"distance":0},{"Id":250,"ShopGrade":"白金店铺","ShopName":"广东新润成陶瓷启东专卖店","BusinessSphere":"陶瓷","Logo":"/Storage/Shop/250/ImageAd/logo.jpg","CompanyName":"广东新润成陶瓷启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":30,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":251,"ShopGrade":"白金店铺","ShopName":"德国爱亿壁纸","BusinessSphere":"壁纸","Logo":"/Storage/Shop/251/ImageAd/logo.jpg","CompanyName":"德国爱亿壁纸启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":0,"distance":0},{"Id":252,"ShopGrade":"白金店铺","ShopName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","BusinessSphere":"陶瓷","Logo":"/Storage/Shop/252/ImageAd/logo.png","CompanyName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":0,"distance":0}]
     * total : 193
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
         * Id : 262
         * ShopGrade : 白金店铺
         * ShopName : 月兔厨具专卖店
         * BusinessSphere : 各类门窗、集成吊顶
         * Logo : /Storage/Shop/262/ImageAd/logo.png
         * CompanyName : 月兔厨具启东专卖店
         * CompanyRegionId : 305
         * CompanyAddress : 江苏省南通市启东市
         * CreateDate : 0
         * ProductNumbere : 21
         * orderProduct : 0
         * buyerNumber : 0
         * distance : 0
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
            private int distance;

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

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }
        }
    }
}
