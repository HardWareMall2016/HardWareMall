package com.hardware.bean;

import java.util.List;

/**
 * Created by hover on 2016/4/15.
 */
public class GoodsThirdCategoryBean {

    /**
     * flag : 1
     * message : [{"Depth":3,"Id":1403,"Name":"液压马达","Icon":"/Storage/Plat/Category/201603301558573312650.jpg","ParentCategoryId":644},{"Depth":3,"Id":3399,"Name":"油管","Icon":"/Storage/Plat/Category/201604151316545558150.jpg","ParentCategoryId":644},{"Depth":3,"Id":1400,"Name":"液压管件","Icon":"/Storage/Plat/Category/201603301558243885940.jpg","ParentCategoryId":644},{"Depth":3,"Id":1398,"Name":"液压站液压系统","Icon":"/Storage/Plat/Category/201603301557574803340.jpg","ParentCategoryId":644},{"Depth":3,"Id":1397,"Name":"液压接头","Icon":"/Storage/Plat/Category/201603301557377403180.jpg","ParentCategoryId":644},{"Depth":3,"Id":1395,"Name":"液压阀","Icon":"/Storage/Plat/Category/201603301557176448200.jpg","ParentCategoryId":644},{"Depth":3,"Id":1393,"Name":"液压泵","Icon":"/Storage/Plat/Category/201604011612146211900.jpg","ParentCategoryId":644},{"Depth":3,"Id":1389,"Name":"液压缸油缸","Icon":"/Storage/Plat/Category/201603301555244324650.jpg","ParentCategoryId":644}]
     */

    private int flag;
    /**
     * Depth : 3
     * Id : 1403
     * Name : 液压马达
     * Icon : /Storage/Plat/Category/201603301558573312650.jpg
     * ParentCategoryId : 644
     */

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
        private int Depth;
        private int Id;
        private String Name;
        private String Icon;
        private int ParentCategoryId;

        public int getDepth() {
            return Depth;
        }

        public void setDepth(int Depth) {
            this.Depth = Depth;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getIcon() {
            return Icon;
        }

        public void setIcon(String Icon) {
            this.Icon = Icon;
        }

        public int getParentCategoryId() {
            return ParentCategoryId;
        }

        public void setParentCategoryId(int ParentCategoryId) {
            this.ParentCategoryId = ParentCategoryId;
        }
    }
}
