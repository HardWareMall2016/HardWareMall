package com.hardware.bean;

import java.util.List;

/**
 * Created by hover on 2016/4/15.
 */
public class GoodsSecondCategoryBean {

    /**
     * flag : 1
     * message : [{"Depth":2,"Id":644,"Name":"液压元件","Icon":"/Storage/Plat/Category/201603291402408808590.jpg","ParentCategoryId":5},{"Depth":2,"Id":639,"Name":"锁具","Icon":"/Storage/Plat/Category/201603291401309497880.jpg","ParentCategoryId":5},{"Depth":2,"Id":635,"Name":"气动元件","Icon":null,"ParentCategoryId":5},{"Depth":2,"Id":631,"Name":"磨具磨料","Icon":"/Storage/Plat/Category/201603291400172630980.jpg","ParentCategoryId":5},{"Depth":2,"Id":629,"Name":"密封件","Icon":"/Storage/Plat/Category/201603291359301479490.jpg","ParentCategoryId":5},{"Depth":2,"Id":626,"Name":"过滤件","Icon":"/Storage/Plat/Category/201603291358392703460.jpg","ParentCategoryId":5},{"Depth":2,"Id":625,"Name":"家具五金","Icon":"/Storage/Plat/Category/201603291357434223900.jpg","ParentCategoryId":5},{"Depth":2,"Id":623,"Name":"机床附件","Icon":"/Storage/Plat/Category/201603291355577562230.jpg","ParentCategoryId":5},{"Depth":2,"Id":622,"Name":"金属丝绳网","Icon":"/Storage/Plat/Category/201603291354467417780.png","ParentCategoryId":5},{"Depth":2,"Id":621,"Name":"焊接件","Icon":"/Storage/Plat/Category/201603291351315233440.jpg","ParentCategoryId":5},{"Depth":2,"Id":620,"Name":"冲压五金配件","Icon":"/Storage/Plat/Category/201603291350236281590.jpg","ParentCategoryId":5},{"Depth":2,"Id":618,"Name":"弹性元件弹簧","Icon":"/Storage/Plat/Category/201603291348504448930.JPG","ParentCategoryId":5},{"Depth":2,"Id":617,"Name":"门窗五金配件","Icon":"/Storage/Plat/Category/201603291345181306100.jpg","ParentCategoryId":5},{"Depth":2,"Id":616,"Name":"幕墙五金配件","Icon":"/Storage/Plat/Category/201603291343455808700.png","ParentCategoryId":5},{"Depth":2,"Id":615,"Name":"电热元件装置","Icon":"/Storage/Plat/Category/201603291342345484090.jpg","ParentCategoryId":5},{"Depth":2,"Id":614,"Name":"工业皮带","Icon":"/Storage/Plat/Category/201603291341219460120.jpg","ParentCategoryId":5},{"Depth":2,"Id":613,"Name":"通用五金配件","Icon":"/Storage/Plat/Category/201603291340225628720.jpg","ParentCategoryId":5},{"Depth":2,"Id":612,"Name":"管件","Icon":"/Storage/Plat/Category/201603291338270984770.jpg","ParentCategoryId":5},{"Depth":2,"Id":114,"Name":"轴承","Icon":"/Storage/Plat/Category/201603291337226887780.jpg","ParentCategoryId":5},{"Depth":2,"Id":113,"Name":"传动件","Icon":"/Storage/Plat/Category/201603291336184910000.jpg","ParentCategoryId":5},{"Depth":2,"Id":112,"Name":"紧固件和连接件","Icon":"/Storage/Plat/Category/201603291335193568930.jpg","ParentCategoryId":5},{"Depth":2,"Id":111,"Name":"焊接耗材","Icon":null,"ParentCategoryId":5},{"Depth":2,"Id":110,"Name":"水暖卫浴五金","Icon":null,"ParentCategoryId":5}]
     */

    private int flag;
    /**
     * Depth : 2
     * Id : 644
     * Name : 液压元件
     * Icon : /Storage/Plat/Category/201603291402408808590.jpg
     * ParentCategoryId : 5
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
