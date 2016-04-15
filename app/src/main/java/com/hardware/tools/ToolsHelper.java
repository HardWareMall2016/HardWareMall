package com.hardware.tools;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hardware.R;
import com.hardware.base.App;
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

    /**
     * 下载并安装app
     *
     * @param url
     */
    public static void installApp(String url) {
        final DownloadManager systemService = (DownloadManager) App.ctx.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "upgrade.apk");
        systemService.enqueue(request);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
                myDownloadQuery.setFilterById(reference);

                Cursor myDownload = systemService.query(myDownloadQuery);
                if (myDownload.moveToFirst()) {
                    int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

                    String fileUri = myDownload.getString(fileUriIdx);

                    Intent ViewInstallIntent = new Intent(Intent.ACTION_VIEW);
                    ViewInstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ViewInstallIntent.setDataAndType(Uri.parse(fileUri), "application/vnd.android.package-archive");
                    context.startActivity(ViewInstallIntent);
                }
                myDownload.close();

                App.ctx.unregisterReceiver(this);
            }
        };
        App.ctx.registerReceiver(receiver, filter);
    }

    public static String getCurVersionName() {
        try {
            PackageManager manager = App.ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.ctx.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            return "1.0.0";
        }
    }

    public static int getCurVersionCode() {
        try {
            PackageManager manager = App.ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.ctx.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            return 100;
        }
    }

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
