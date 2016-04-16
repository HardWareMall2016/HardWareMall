package com.hardware.utils;

/**
 * Created by hover on 2015/12/24.
 */
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    private static JsonHelper util;

    public static JsonHelper getInstance() {

        if (util == null) {
            util = new JsonHelper();
        }
        return util;
    }

    private JsonHelper() {
        super();
    }

    public String createJsonString(Object value) {
        Gson gson = new Gson();
        String str = gson.toJson(value);
        return str;
    }

    public <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            if (isGoodJson(jsonString)) {
                Gson gson = new Gson();
                t = gson.fromJson(jsonString, cls);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public <T> List<T> getList(String jsonString, TypeToken<T> tt) {
        List<T> list = new ArrayList<T>();
        try {
            if (isGoodJson(jsonString)) {
                Gson gson = new Gson();

                list = gson.fromJson(jsonString, tt.getType());
            }

        } catch (Exception e) {
        }
        return list;
    }

    public boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    public boolean isGoodJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);

        } catch (JsonParseException e) {
            return false;
        }

        return true;
    }

}

