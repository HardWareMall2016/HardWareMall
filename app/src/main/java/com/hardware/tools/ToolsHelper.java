package com.hardware.tools;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hardware.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ToolsHelper {
    public static <T> T parseJson(String json, Class<T> beanClass) {
        T bean = null;
        try{
            bean = JSON.parseObject(json, beanClass);
        }catch (JSONException ex){
            Log.e("Utils", "fromJson error : " + ex.getMessage());
        }

        return bean;
    }

    /***
     * 解析服务器时间，服务器是GMT+08时区
     * @param serverTimeStr
     * @return 返回 时间戳
     */
    public static long parseServerTime(String serverTimeStr) {
        if (TextUtils.isEmpty(serverTimeStr)) {
            return 0;
        }
        final String FORMAT = "yyyy-MM-dd HH:mm:ss";
        Date date = ToolsHelper.parseDate(serverTimeStr,FORMAT, TimeZone.getTimeZone("GMT+08"));
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    public static Date parseDate(String date, String format,TimeZone timeZone) {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(timeZone);
        try {
            dt = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static DisplayImageOptions buldDefDisplayImageOptions(){
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .considerExifParams(true)
                .build();
    }
}
