package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class CartOrderImmedResponse {
    /**
     * flag : 1
     * message : {"CartItemModels":[{"color":"","count":1,"id":4259,"imgUrl":"/Storage/Shop/332/Products/4259/1_150.png","ProductName":"代尔塔防护服 防化服 防化学飞溅 连体服 防粉尘 喷漆服 406115 白色","price":22,"productCode":"","shopId":332,"size":"","skuId":"4259_0_0_0","amount":22,"carId":0}],"carMoney":22,"number":1,"shopId":332,"ShopName":"苏州安德消防","Express":0}
     * sumMoney : 22
     * sumnumber : 1
     * Address : {"Id":66,"fullRegionName":"浙江省 杭州市 上城区","address":"好的方法解决","phone":"18374855958","shipTo":"股东会","fullRegionIdPath":"100,101,102"}
     */

    private int flag;
    /**
     * CartItemModels : [{"color":"","count":1,"id":4259,"imgUrl":"/Storage/Shop/332/Products/4259/1_150.png","ProductName":"代尔塔防护服 防化服 防化学飞溅 连体服 防粉尘 喷漆服 406115 白色","price":22,"productCode":"","shopId":332,"size":"","skuId":"4259_0_0_0","amount":22,"carId":0}]
     * carMoney : 22
     * number : 1
     * shopId : 332
     * ShopName : 苏州安德消防
     * Express : 0
     */

    private MessageEntity message;
    private double sumMoney;
    private int sumnumber;
    /**
     * Id : 66
     * fullRegionName : 浙江省 杭州市 上城区
     * address : 好的方法解决
     * phone : 18374855958
     * shipTo : 股东会
     * fullRegionIdPath : 100,101,102
     */

    private AddressEntity Address;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public void setSumnumber(int sumnumber) {
        this.sumnumber = sumnumber;
    }

    public void setAddress(AddressEntity Address) {
        this.Address = Address;
    }

    public int getFlag() {
        return flag;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public double getSumnumber() {
        return sumnumber;
    }

    public AddressEntity getAddress() {
        return Address;
    }

    public static class MessageEntity {
        private double carMoney;
        private int number;
        private int shopId;
        private String ShopName;
        private int Express;
        /**
         * color :
         * count : 1
         * id : 4259
         * imgUrl : /Storage/Shop/332/Products/4259/1_150.png
         * ProductName : 代尔塔防护服 防化服 防化学飞溅 连体服 防粉尘 喷漆服 406115 白色
         * price : 22
         * productCode :
         * shopId : 332
         * size :
         * skuId : 4259_0_0_0
         * amount : 22
         * carId : 0
         */

        private List<CartItemModelsEntity> CartItemModels;

        public void setCarMoney(double carMoney) {
            this.carMoney = carMoney;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public void setExpress(int Express) {
            this.Express = Express;
        }

        public void setCartItemModels(List<CartItemModelsEntity> CartItemModels) {
            this.CartItemModels = CartItemModels;
        }

        public double getCarMoney() {
            return carMoney;
        }

        public int getNumber() {
            return number;
        }

        public int getShopId() {
            return shopId;
        }

        public String getShopName() {
            return ShopName;
        }

        public int getExpress() {
            return Express;
        }

        public List<CartItemModelsEntity> getCartItemModels() {
            return CartItemModels;
        }

        public static class CartItemModelsEntity {
            private String color;
            private int count;
            private int id;
            private String imgUrl;
            private String ProductName;
            private double price;
            private String productCode;
            private int shopId;
            private String size;
            private String skuId;
            private int amount;
            private int carId;

            public void setColor(String color) {
                this.color = color;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public void setProductName(String ProductName) {
                this.ProductName = ProductName;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public void setCarId(int carId) {
                this.carId = carId;
            }

            public String getColor() {
                return color;
            }

            public int getCount() {
                return count;
            }

            public int getId() {
                return id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public String getProductName() {
                return ProductName;
            }

            public double getPrice() {
                return price;
            }

            public String getProductCode() {
                return productCode;
            }

            public int getShopId() {
                return shopId;
            }

            public String getSize() {
                return size;
            }

            public String getSkuId() {
                return skuId;
            }

            public int getAmount() {
                return amount;
            }

            public int getCarId() {
                return carId;
            }
        }
    }

    public static class AddressEntity {
        private int Id;
        private String fullRegionName;
        private String address;
        private String phone;
        private String shipTo;
        private String fullRegionIdPath;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setFullRegionName(String fullRegionName) {
            this.fullRegionName = fullRegionName;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setShipTo(String shipTo) {
            this.shipTo = shipTo;
        }

        public void setFullRegionIdPath(String fullRegionIdPath) {
            this.fullRegionIdPath = fullRegionIdPath;
        }

        public int getId() {
            return Id;
        }

        public String getFullRegionName() {
            return fullRegionName;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getShipTo() {
            return shipTo;
        }

        public String getFullRegionIdPath() {
            return fullRegionIdPath;
        }
    }
}
