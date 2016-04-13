package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/12.
 */
public class CompangyInfoRespon {
    /**
     * flag : 1
     * message : {"Id":263,"GradeId":1,"GradeName":"白金店铺","ShopName":"万立石膏线","CompanyRegionId":305,"CompanyRegionName":"江苏省南通市启东市","ProductNumber":16,"buyerNumber":11,"ContactsName":"喻重才","ContactsPhone":"13813707018","BusinessSphere":"集成吊顶","PackMark":5,"DeliveryMark":5,"ServiceMark":5}
     */

    private int flag;
    /**
     * Id : 263
     * GradeId : 1
     * GradeName : 白金店铺
     * ShopName : 万立石膏线
     * CompanyRegionId : 305
     * CompanyRegionName : 江苏省南通市启东市
     * ProductNumber : 16
     * buyerNumber : 11
     * ContactsName : 喻重才
     * ContactsPhone : 13813707018
     * BusinessSphere : 集成吊顶
     * PackMark : 5
     * DeliveryMark : 5
     * ServiceMark : 5
     */

    private MessageEntity message;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public static class MessageEntity {
        private int Id;
        private int GradeId;
        private String GradeName;
        private String ShopName;
        private int CompanyRegionId;
        private String CompanyRegionName;
        private int ProductNumber;
        private int buyerNumber;
        private String ContactsName;
        private String ContactsPhone;
        private String BusinessSphere;
        private int PackMark;
        private int DeliveryMark;
        private int ServiceMark;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setGradeId(int GradeId) {
            this.GradeId = GradeId;
        }

        public void setGradeName(String GradeName) {
            this.GradeName = GradeName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public void setCompanyRegionId(int CompanyRegionId) {
            this.CompanyRegionId = CompanyRegionId;
        }

        public void setCompanyRegionName(String CompanyRegionName) {
            this.CompanyRegionName = CompanyRegionName;
        }

        public void setProductNumber(int ProductNumber) {
            this.ProductNumber = ProductNumber;
        }

        public void setBuyerNumber(int buyerNumber) {
            this.buyerNumber = buyerNumber;
        }

        public void setContactsName(String ContactsName) {
            this.ContactsName = ContactsName;
        }

        public void setContactsPhone(String ContactsPhone) {
            this.ContactsPhone = ContactsPhone;
        }

        public void setBusinessSphere(String BusinessSphere) {
            this.BusinessSphere = BusinessSphere;
        }

        public void setPackMark(int PackMark) {
            this.PackMark = PackMark;
        }

        public void setDeliveryMark(int DeliveryMark) {
            this.DeliveryMark = DeliveryMark;
        }

        public void setServiceMark(int ServiceMark) {
            this.ServiceMark = ServiceMark;
        }

        public int getId() {
            return Id;
        }

        public int getGradeId() {
            return GradeId;
        }

        public String getGradeName() {
            return GradeName;
        }

        public String getShopName() {
            return ShopName;
        }

        public int getCompanyRegionId() {
            return CompanyRegionId;
        }

        public String getCompanyRegionName() {
            return CompanyRegionName;
        }

        public int getProductNumber() {
            return ProductNumber;
        }

        public int getBuyerNumber() {
            return buyerNumber;
        }

        public String getContactsName() {
            return ContactsName;
        }

        public String getContactsPhone() {
            return ContactsPhone;
        }

        public String getBusinessSphere() {
            return BusinessSphere;
        }

        public int getPackMark() {
            return PackMark;
        }

        public int getDeliveryMark() {
            return DeliveryMark;
        }

        public int getServiceMark() {
            return ServiceMark;
        }
    }
}
