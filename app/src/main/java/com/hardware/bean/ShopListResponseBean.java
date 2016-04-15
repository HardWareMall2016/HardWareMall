package com.hardware.bean;

import java.util.List;

/**
 * Created by WuYue on 2016/4/11.
 */
public class ShopListResponseBean {

    /**
     * flag : 1
     * message : {"rows":[{"Id":262,"ShopGrade":"白金店铺","ShopName":"月兔厨具专卖店","BusinessSphere":null,"Logo":"/Storage/Shop/262/ImageAd/logo.png","CompanyName":"月兔厨具启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街399号","CreateDate":0,"ProductNumbere":21,"orderProduct":0,"buyerNumber":28,"distance":0},{"Id":263,"ShopGrade":"白金店铺","ShopName":"万立石膏线","BusinessSphere":null,"Logo":"/Storage/Shop/263/ImageAd/logo.png","CompanyName":"万立石膏线","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市和平中路373号","CreateDate":0,"ProductNumbere":16,"orderProduct":9,"buyerNumber":11,"distance":0},{"Id":264,"ShopGrade":"白金店铺","ShopName":"江苏启东市金源木业启东市莫干山饰材专卖店","BusinessSphere":null,"Logo":"/Storage/Shop/264/ImageAd/logo.jpg","CompanyName":"江苏启东市金源木业启东市莫干山饰材专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市建材街和平路385号","CreateDate":0,"ProductNumbere":26,"orderProduct":0,"buyerNumber":11,"distance":0},{"Id":259,"ShopGrade":"白金店铺","ShopName":"ST.THOMAS圣·托玛斯卫浴","BusinessSphere":null,"Logo":"/Storage/Shop/259/ImageAd/logo.jpg","CompanyName":"ST.THOMAS圣·托玛斯卫浴","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市汇龙镇建材街437号（温莎堡向东500）","CreateDate":0,"ProductNumbere":20,"orderProduct":0,"buyerNumber":24,"distance":0},{"Id":250,"ShopGrade":"白金店铺","ShopName":"广东新润成陶瓷启东专卖店","BusinessSphere":null,"Logo":"/Storage/Shop/250/ImageAd/logo.jpg","CompanyName":"广东新润成陶瓷启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市建材街280号","CreateDate":0,"ProductNumbere":30,"orderProduct":0,"buyerNumber":15,"distance":0},{"Id":251,"ShopGrade":"白金店铺","ShopName":"德国爱亿壁纸","BusinessSphere":null,"Logo":"/Storage/Shop/251/ImageAd/logo.jpg","CompanyName":"德国爱亿壁纸启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街483号","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":10,"distance":0},{"Id":252,"ShopGrade":"白金店铺","ShopName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","BusinessSphere":null,"Logo":"/Storage/Shop/252/ImageAd/logo.png","CompanyName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河街345-347号","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":16,"distance":0},{"Id":254,"ShopGrade":"白金店铺","ShopName":"美国约克墙纸","BusinessSphere":null,"Logo":"/Storage/Shop/254/ImageAd/logo.jpg","CompanyName":"美国约克墙纸","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市汇龙镇城河建材街277号","CreateDate":0,"ProductNumbere":19,"orderProduct":0,"buyerNumber":1,"distance":0},{"Id":255,"ShopGrade":"白金店铺","ShopName":"飞鸿灯饰电器总汇","BusinessSphere":null,"Logo":"/Storage/Shop/255/ImageAd/logo.png","CompanyName":"飞鸿灯饰电器总汇","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街216号","CreateDate":0,"ProductNumbere":36,"orderProduct":0,"buyerNumber":1,"distance":0},{"Id":256,"ShopGrade":"白金店铺","ShopName":"龙胜启东代理","BusinessSphere":null,"Logo":"/Storage/Shop/256/ImageAd/logo.jpg","CompanyName":"龙胜启东代理","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街277号","CreateDate":0,"ProductNumbere":20,"orderProduct":0,"buyerNumber":1,"distance":0}],"total":84}
     */

    private int flag;
    /**
     * rows : [{"Id":262,"ShopGrade":"白金店铺","ShopName":"月兔厨具专卖店","BusinessSphere":null,"Logo":"/Storage/Shop/262/ImageAd/logo.png","CompanyName":"月兔厨具启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街399号","CreateDate":0,"ProductNumbere":21,"orderProduct":0,"buyerNumber":28,"distance":0},{"Id":263,"ShopGrade":"白金店铺","ShopName":"万立石膏线","BusinessSphere":null,"Logo":"/Storage/Shop/263/ImageAd/logo.png","CompanyName":"万立石膏线","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市和平中路373号","CreateDate":0,"ProductNumbere":16,"orderProduct":9,"buyerNumber":11,"distance":0},{"Id":264,"ShopGrade":"白金店铺","ShopName":"江苏启东市金源木业启东市莫干山饰材专卖店","BusinessSphere":null,"Logo":"/Storage/Shop/264/ImageAd/logo.jpg","CompanyName":"江苏启东市金源木业启东市莫干山饰材专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市建材街和平路385号","CreateDate":0,"ProductNumbere":26,"orderProduct":0,"buyerNumber":11,"distance":0},{"Id":259,"ShopGrade":"白金店铺","ShopName":"ST.THOMAS圣·托玛斯卫浴","BusinessSphere":null,"Logo":"/Storage/Shop/259/ImageAd/logo.jpg","CompanyName":"ST.THOMAS圣·托玛斯卫浴","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市汇龙镇建材街437号（温莎堡向东500）","CreateDate":0,"ProductNumbere":20,"orderProduct":0,"buyerNumber":24,"distance":0},{"Id":250,"ShopGrade":"白金店铺","ShopName":"广东新润成陶瓷启东专卖店","BusinessSphere":null,"Logo":"/Storage/Shop/250/ImageAd/logo.jpg","CompanyName":"广东新润成陶瓷启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市建材街280号","CreateDate":0,"ProductNumbere":30,"orderProduct":0,"buyerNumber":15,"distance":0},{"Id":251,"ShopGrade":"白金店铺","ShopName":"德国爱亿壁纸","BusinessSphere":null,"Logo":"/Storage/Shop/251/ImageAd/logo.jpg","CompanyName":"德国爱亿壁纸启东专卖店","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街483号","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":10,"distance":0},{"Id":252,"ShopGrade":"白金店铺","ShopName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","BusinessSphere":null,"Logo":"/Storage/Shop/252/ImageAd/logo.png","CompanyName":"唐山惠达陶瓷（集团）股份有限公司启东直销处","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河街345-347号","CreateDate":0,"ProductNumbere":13,"orderProduct":0,"buyerNumber":16,"distance":0},{"Id":254,"ShopGrade":"白金店铺","ShopName":"美国约克墙纸","BusinessSphere":null,"Logo":"/Storage/Shop/254/ImageAd/logo.jpg","CompanyName":"美国约克墙纸","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市汇龙镇城河建材街277号","CreateDate":0,"ProductNumbere":19,"orderProduct":0,"buyerNumber":1,"distance":0},{"Id":255,"ShopGrade":"白金店铺","ShopName":"飞鸿灯饰电器总汇","BusinessSphere":null,"Logo":"/Storage/Shop/255/ImageAd/logo.png","CompanyName":"飞鸿灯饰电器总汇","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街216号","CreateDate":0,"ProductNumbere":36,"orderProduct":0,"buyerNumber":1,"distance":0},{"Id":256,"ShopGrade":"白金店铺","ShopName":"龙胜启东代理","BusinessSphere":null,"Logo":"/Storage/Shop/256/ImageAd/logo.jpg","CompanyName":"龙胜启东代理","CompanyRegionId":305,"CompanyAddress":"江苏省南通市启东市启东市城河建材街277号","CreateDate":0,"ProductNumbere":20,"orderProduct":0,"buyerNumber":1,"distance":0}]
     * total : 84
     */

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
         * Id : 262
         * ShopGrade : 白金店铺
         * ShopName : 月兔厨具专卖店
         * BusinessSphere : null
         * Logo : /Storage/Shop/262/ImageAd/logo.png
         * CompanyName : 月兔厨具启东专卖店
         * CompanyRegionId : 305
         * CompanyAddress : 江苏省南通市启东市启东市城河建材街399号
         * CreateDate : 0
         * ProductNumbere : 21
         * orderProduct : 0
         * buyerNumber : 28
         * distance : 0
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
            private int Id;
            private String ShopGrade;
            private String ShopName;
            private Object BusinessSphere;
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

            public Object getBusinessSphere() {
                return BusinessSphere;
            }

            public void setBusinessSphere(Object BusinessSphere) {
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
}
