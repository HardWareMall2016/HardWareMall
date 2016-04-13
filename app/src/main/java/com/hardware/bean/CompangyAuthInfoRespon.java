package com.hardware.bean;

/**
 * Created by Administrator on 2016/4/12.
 */
public class CompangyAuthInfoRespon {
    /**
     * flag : 1
     * message : {"Id":262,"GradeId":1,"GradeName":"白金店铺","CompanyName":"月兔厨具启东专卖店","CompanyAddress":"江苏省南通市启东市启东市城河建材街399号","CompanyRegionId":305,"CreateDate":"2016-03-11","OrganizationCode":"1111","legalPerson":"黄张健","BusinessLicenceEnd":"2016-12-31","BusinessSphere":"各类门窗、集成吊顶"}
     */

    private int flag;
    /**
     * Id : 262
     * GradeId : 1
     * GradeName : 白金店铺
     * CompanyName : 月兔厨具启东专卖店
     * CompanyAddress : 江苏省南通市启东市启东市城河建材街399号
     * CompanyRegionId : 305
     * CreateDate : 2016-03-11
     * OrganizationCode : 1111
     * legalPerson : 黄张健
     * BusinessLicenceEnd : 2016-12-31
     * BusinessSphere : 各类门窗、集成吊顶
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
        private String CompanyName;
        private String CompanyAddress;
        private int CompanyRegionId;
        private String CreateDate;
        private String OrganizationCode;
        private String legalPerson;
        private String BusinessLicenceEnd;
        private String BusinessSphere;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setGradeId(int GradeId) {
            this.GradeId = GradeId;
        }

        public void setGradeName(String GradeName) {
            this.GradeName = GradeName;
        }

        public void setCompanyName(String CompanyName) {
            this.CompanyName = CompanyName;
        }

        public void setCompanyAddress(String CompanyAddress) {
            this.CompanyAddress = CompanyAddress;
        }

        public void setCompanyRegionId(int CompanyRegionId) {
            this.CompanyRegionId = CompanyRegionId;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public void setOrganizationCode(String OrganizationCode) {
            this.OrganizationCode = OrganizationCode;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public void setBusinessLicenceEnd(String BusinessLicenceEnd) {
            this.BusinessLicenceEnd = BusinessLicenceEnd;
        }

        public void setBusinessSphere(String BusinessSphere) {
            this.BusinessSphere = BusinessSphere;
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

        public String getCompanyName() {
            return CompanyName;
        }

        public String getCompanyAddress() {
            return CompanyAddress;
        }

        public int getCompanyRegionId() {
            return CompanyRegionId;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public String getOrganizationCode() {
            return OrganizationCode;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public String getBusinessLicenceEnd() {
            return BusinessLicenceEnd;
        }

        public String getBusinessSphere() {
            return BusinessSphere;
        }
    }
}
