package com.hardware.common;

import com.hardware.api.ApiConstants;
import com.hardware.network.OkHttpUtils;
import com.hardware.network.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hover on 2016/4/14.
 */
public class HardWareApi {
    private static volatile HardWareApi instance = null;

    private HardWareApi() {
    }

    public static HardWareApi getInstance() {
        if (null == instance) {
            synchronized (HardWareApi.class) {
                if (null == instance) {
                    instance = new HardWareApi();
                }
            }
        }
        return instance;
    }

    //一层分类
    public void getGoodsFirstCategory(StringCallback callback) {
        Map<String, String> params = new HashMap<String, String>();
        String url = ApiConstants.GOODS_FIRST_CATEGORY;
        OkHttpUtils//
                .post()//
                .url(url)//
                .params(params)//
                .build()//
                .execute(callback);
    }

    //二三层分类
    public void getGoodsSecondCategory(StringCallback callback, String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        String url = ApiConstants.GOODS_SECOND_CATEGORY;
        OkHttpUtils//
                .post()//
                .url(url)//
                .params(params)//
                .build()//
                .execute(callback);
    }

    //
    public void getGoodsList(StringCallback callback){
        Map<String, String> params = new HashMap<String, String>();
        params.put("Token", "");
        params.put("brandName", "");
        params.put("categoryId", "3350");
        params.put("keyWords", "");
        params.put("latitude", "31.24092916960943");
        params.put("longitude", "121.4727160335718");
        params.put("page", "1");
        params.put("productSort", "2");
        params.put("regionName", "上海市,上海市");
        params.put("shopName", "");
        String url = ApiConstants.GOODS_LIST;
        OkHttpUtils//
                .post()//
                .url(url)//
                .params(params)//
                .build()//
                .execute(callback);
    }
}
