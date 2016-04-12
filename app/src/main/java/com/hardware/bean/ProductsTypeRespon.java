package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ProductsTypeRespon {
    /**
     * flag : 1
     * shopCategories : [{"Id":340,"Name":"电动工具配件","shopCate":[{"Id":452,"Name":"开孔器"},{"Id":451,"Name":"抛光片"},{"Id":450,"Name":"合金锯片"},{"Id":449,"Name":"磨片"},{"Id":448,"Name":"云石片"},{"Id":447,"Name":"电锤钻头"}]}]
     */

    private int flag;
    /**
     * Id : 340
     * Name : 电动工具配件
     * shopCate : [{"Id":452,"Name":"开孔器"},{"Id":451,"Name":"抛光片"},{"Id":450,"Name":"合金锯片"},{"Id":449,"Name":"磨片"},{"Id":448,"Name":"云石片"},{"Id":447,"Name":"电锤钻头"}]
     */

    private List<ShopCategoriesEntity> shopCategories;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setShopCategories(List<ShopCategoriesEntity> shopCategories) {
        this.shopCategories = shopCategories;
    }

    public int getFlag() {
        return flag;
    }

    public List<ShopCategoriesEntity> getShopCategories() {
        return shopCategories;
    }

    public static class ShopCategoriesEntity {
        private int Id;
        private String Name;
        /**
         * Id : 452
         * Name : 开孔器
         */

        private List<ShopCateEntity> shopCate;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setShopCate(List<ShopCateEntity> shopCate) {
            this.shopCate = shopCate;
        }

        public int getId() {
            return Id;
        }

        public String getName() {
            return Name;
        }

        public List<ShopCateEntity> getShopCate() {
            return shopCate;
        }

        public static class ShopCateEntity {
            private int Id;
            private String Name;

            public void setId(int Id) {
                this.Id = Id;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getId() {
                return Id;
            }

            public String getName() {
                return Name;
            }
        }
    }
}
