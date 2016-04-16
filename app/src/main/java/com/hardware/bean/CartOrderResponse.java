package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 16/4/16.
 */
public class CartOrderResponse {
    /**
     * flag : 1
     * message : [{"CartItemModels":[{"color":"","count":1,"id":654,"imgUrl":"/Storage/Shop/279/Products/654/1_150.png","ProductName":"摩恩（MOEN） 100611 精铜陶瓷阀芯冷热水角阀","price":20,"productCode":"","shopId":279,"size":"","skuId":"654_0_0_0","amount":20,"carId":3}],"carMoney":20,"number":1,"shopId":279,"ShopName":"MOEN摩恩","Express":0}]
     * sumMoney : 20
     * sumnumber : 1
     * Address : {"Id":2,"fullRegionName":"浙江省 杭州市 下城区","address":"小猪猪","phone":"1381632233","shipTo":"猪猪","fullRegionIdPath":"100,101,103"}
     */

    private int flag;
    private int sumMoney;
    private int sumnumber;
    private AddressBean Address;
    private List<MessageBean> message;


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getSumnumber() {
        return sumnumber;
    }

    public void setSumnumber(int sumnumber) {
        this.sumnumber = sumnumber;
    }

    public AddressBean getAddress() {
        return Address;
    }

    public void setAddress(AddressBean Address) {
        this.Address = Address;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class AddressBean {
        private int Id;
        private String fullRegionName;
        private String address;
        private String phone;
        private String shipTo;
        private String fullRegionIdPath;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getFullRegionName() {
            return fullRegionName;
        }

        public void setFullRegionName(String fullRegionName) {
            this.fullRegionName = fullRegionName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getShipTo() {
            return shipTo;
        }

        public void setShipTo(String shipTo) {
            this.shipTo = shipTo;
        }

        public String getFullRegionIdPath() {
            return fullRegionIdPath;
        }

        public void setFullRegionIdPath(String fullRegionIdPath) {
            this.fullRegionIdPath = fullRegionIdPath;
        }
    }

    public static class MessageBean {
        private int carMoney;
        private int number;
        private int shopId;
        private String ShopName;
        private int Express;
        /**
         * color :
         * count : 1
         * id : 654
         * imgUrl : /Storage/Shop/279/Products/654/1_150.png
         * ProductName : 摩恩（MOEN） 100611 精铜陶瓷阀芯冷热水角阀
         * price : 20
         * productCode :
         * shopId : 279
         * size :
         * skuId : 654_0_0_0
         * amount : 20
         * carId : 3
         */

        private List<CartItemModelsBean> CartItemModels;

        public int getCarMoney() {
            return carMoney;
        }

        public void setCarMoney(int carMoney) {
            this.carMoney = carMoney;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public int getExpress() {
            return Express;
        }

        public void setExpress(int Express) {
            this.Express = Express;
        }

        public List<CartItemModelsBean> getCartItemModels() {
            return CartItemModels;
        }

        public void setCartItemModels(List<CartItemModelsBean> CartItemModels) {
            this.CartItemModels = CartItemModels;
        }

        public static class CartItemModelsBean {
            private String color;
            private int count;
            private int id;
            private String imgUrl;
            private String ProductName;
            private int price;
            private String productCode;
            private int shopId;
            private String size;
            private String skuId;
            private int amount;
            private int carId;

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getProductName() {
                return ProductName;
            }

            public void setProductName(String ProductName) {
                this.ProductName = ProductName;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getCarId() {
                return carId;
            }

            public void setCarId(int carId) {
                this.carId = carId;
            }
        }
    }
}
