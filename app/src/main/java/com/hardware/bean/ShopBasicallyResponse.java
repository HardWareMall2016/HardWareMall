package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ShopBasicallyResponse {
    /**
     * flag : 1
     * message : {"Id":262,"GradeId":1,"GradeName":"白金店铺","ShopName":"月兔厨具专卖店","CompanyRegionId":305,"CompanyRegionName":"江苏省南通市启东市","ProductNumber":21,"buyerNumber":28,"ContactsName":"黄张健","ContactsPhone":"13921656655","BusinessSphere":"各类门窗、集成吊顶","PackMark":5,"DeliveryMark":5,"ServiceMark":5}
     */

    private int flag;
    /**
     * Id : 262
     * GradeId : 1
     * GradeName : 白金店铺
     * ShopName : 月兔厨具专卖店
     * CompanyRegionId : 305
     * CompanyRegionName : 江苏省南通市启东市
     * ProductNumber : 21
     * buyerNumber : 28
     * ContactsName : 黄张健
     * ContactsPhone : 13921656655
     * BusinessSphere : 各类门窗、集成吊顶
     * PackMark : 5
     * DeliveryMark : 5
     * ServiceMark : 5
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

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getGradeId() {
            return GradeId;
        }

        public void setGradeId(int GradeId) {
            this.GradeId = GradeId;
        }

        public String getGradeName() {
            return GradeName;
        }

        public void setGradeName(String GradeName) {
            this.GradeName = GradeName;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public int getCompanyRegionId() {
            return CompanyRegionId;
        }

        public void setCompanyRegionId(int CompanyRegionId) {
            this.CompanyRegionId = CompanyRegionId;
        }

        public String getCompanyRegionName() {
            return CompanyRegionName;
        }

        public void setCompanyRegionName(String CompanyRegionName) {
            this.CompanyRegionName = CompanyRegionName;
        }

        public int getProductNumber() {
            return ProductNumber;
        }

        public void setProductNumber(int ProductNumber) {
            this.ProductNumber = ProductNumber;
        }

        public int getBuyerNumber() {
            return buyerNumber;
        }

        public void setBuyerNumber(int buyerNumber) {
            this.buyerNumber = buyerNumber;
        }

        public String getContactsName() {
            return ContactsName;
        }

        public void setContactsName(String ContactsName) {
            this.ContactsName = ContactsName;
        }

        public String getContactsPhone() {
            return ContactsPhone;
        }

        public void setContactsPhone(String ContactsPhone) {
            this.ContactsPhone = ContactsPhone;
        }

        public String getBusinessSphere() {
            return BusinessSphere;
        }

        public void setBusinessSphere(String BusinessSphere) {
            this.BusinessSphere = BusinessSphere;
        }

        public int getPackMark() {
            return PackMark;
        }

        public void setPackMark(int PackMark) {
            this.PackMark = PackMark;
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
    }
}
