package com.hardware.utils;

import android.content.Context;

import org.json.JSONArray;

public class JsonCache {

    private ACache mCache;
    private Context activity;

    public JsonCache(Context activity) {
        this.activity = activity;
        mCache = ACache.get(activity);
    }


    /***
     * 缓存一个jsonArray
     ***/
    public void cacheJsonArray(JSONArray jsonArray, String jsonName) {
        mCache.put(jsonName, jsonArray);
    }

    /***
     * 读取一个jsonArray
     ***/
    public JSONArray readJsonArray(String jsonName) {
        JSONArray testJsonArray = mCache.getAsJSONArray(jsonName);
        if (testJsonArray == null) {
            return null;
        }
        return testJsonArray;
    }

    /**
     * 缓存一个string
     **/
    public void cacheString(String name, String json) {
        mCache.put(name, json);
    }

    /**
     * 读取一个json
     **/
    public String readString(String name) {
        return mCache.getAsString(name);
    }

    /**
     * 移除对应的String
     **/
    public void clear(String name) {
        mCache.remove(name);
    }

    /**
     * 缓存一个员工Object
     **/
//    public void cacheObject(String name, DlYgxx json) {
//        //mCache.put(name, json);
//        mCache.put(name, json);
//    }

    /**
     * 读取一个员工Object
     **/
    public Object readObject(String name) {
        return mCache.getAsObject(name);
    }

//    /**
//     * 缓存一个员工Object
//     **/
//    public void cacheUser(String name, UserTwo json) {
//        //mCache.put(name, json);
//        mCache.put(name, json);
//    }

    /**
     * 读取一个员工Object
     **/
    public Object readUser(String name) {
        return mCache.getAsObject(name);
    }


}
