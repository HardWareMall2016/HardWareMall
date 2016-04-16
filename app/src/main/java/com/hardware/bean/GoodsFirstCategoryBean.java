package com.hardware.bean;

import java.util.List;

/**
 * Created by hover on 2016/4/13.
 */
public class GoodsFirstCategoryBean {

    /**
     * flag : 1
     * message : [{"Depth":1,"Id":1,"Name":"安防劳保","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":2,"Name":"建材家居","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":4,"Name":"橡塑化工","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":5,"Name":"五金零件","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":6,"Name":"行业设备","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":7,"Name":"五金机电","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":8,"Name":"电子器件","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":11,"Name":"机械设备","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":12,"Name":"仪器仪表","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":13,"Name":"电工电器","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":3220,"Name":"行政办公","Icon":null,"ParentCategoryId":0},{"Depth":1,"Id":3221,"Name":"厨房餐饮","Icon":null,"ParentCategoryId":0}]
     */

    private int flag;
    /**
     * Depth : 1
     * Id : 1
     * Name : 安防劳保
     * Icon : null
     * ParentCategoryId : 0
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
        private Object Icon;
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

        public Object getIcon() {
            return Icon;
        }

        public void setIcon(Object Icon) {
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
