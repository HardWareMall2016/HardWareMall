package com.hardware.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/16.
 */
public class MyCartOrderCarResponse {
    private int flag;
    private double sumMoney;
    private int sumnumber;

    private List<MessageBean> message;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getSumnumber() {
        return sumnumber;
    }

    public void setSumnumber(int sumnumber) {
        this.sumnumber = sumnumber;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        private double carMoney;
        private int number;
        private int shopId;
        private String ShopName;
        private int Express;

        private List<CartItemModelsBean> CartItemModels;

        public double getCarMoney() {
            return carMoney;
        }

        public void setCarMoney(double carMoney) {
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
            private double price;
            private String productCode;
            private int shopId;
            private String size;
            private String skuId;
            private double amount;
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
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

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
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
