package com.hardware.api;


public class ApiConstants {
    public static final String BASE_URL = "http://121.43.111.133:50013/";

    public static final String IMG_BASE_URL = "http://121.43.111.133:51022/";

    public static final String MOBILE_HOME_PRODUCTS_LIST = "common/aplHome/mobilehomeproductsList";

    //商品详情
    public static final String PRODUCTS_DETAIL = "common/aplHome/productsDetail";

    //同店推荐
    public static final String PRODUCTS_SHOPSPRODUCTS = "common/aplHome/shopsProducts";

    //店铺基本&公司基本信息
    public static final String SHOP_BASICALLY = "common/aplHome/shopBasically";

    //店铺产品
    public static final String SHOP_PRODUCTS_LIST = "common/aplHome/shopProductsList";

    //更多折扣产品
    public static final String MORE_DISCOUNT_SALE_LIST = "common/aplHome/volumeproductsList";

    //更多人气店铺
    public static final String MORE_POPULARITY_SHOP_LIST = "common/aplHome/popularityShopsList";

    //店铺列表
    public static final String MORE_ALL_SHOP_LIST = "common/aplHome/shopList";

    //公司认证信息
    public static final String SHOP_DETAILS = "common/aplHome/shopDetails";

    //店铺产品分类
    public static final String SHOP_CATEGORIES = "common/aplHome/shopCategories";

    //产品评价
    public static final String PRODUCTS_EVALUATE = "common/aplHome/productsEvaluate";

    //热销单品
    public static final String DISCOUNT_PRODUCTSLIST = "common/aplHome/discountProductsList";

    //购物车列表
    public static final String MY_CART_ORDER_CAR = "common/aplHome/myCartOrderCar";
    //更新购物车产品数量
    public static final String UPDATE_CART_ORDER_CAR = "common/aplHome/UpdateCartOrderCar";
    //删除购物车
    public static final String DELETE_ORDER_CAR = "common/aplHome/DeleteOrderCar";

    //一级分类
    public static final String GOODS_FIRST_CATEGORY = BASE_URL + "common/aplHome/oneCategories";
    //二，三级分类
    public static final String GOODS_SECOND_CATEGORY = BASE_URL + "common/aplHome/twoCategories";
    //
    public static final String GOODS_LIST= BASE_URL + "common/aplHome/productsList";

    //确认订单(购物提交订单显示)
    public static final String CAR_BYORDER ="common/aplHome/CarByOrder";

    //收货地址(属于47777端口接口)
    public static final String GET_MYADDRESS = "common/MBUser/GetMyAddress" ;

    //以下属于47777端口接口
    //登录
    public static final String LOGIN = "common/MBUser/Login";
    //移至收藏夹
    public static final String SHOPPING_REMOVE_COLLECTION = "common/MBUser/ShoppingRemoveCollection ";
    //获取省份
    public static final String GET_PROVINCE = "common/MBUser/GetProvince";
    //根据省份获取城市
    public static final String GET_CITY = "common/MBUser/GetCity";
    //根据城市获取区
    public static final String GET_COUNTY = "common/MBUser/GetCounty";
    //新增收货地址
    public static final String ADD_MY_ADDRESS_INFO = "common/MBUser/AddMyAddressInfo";
}
