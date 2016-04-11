package com.zhan.framework.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.zhan.framework.common.context.GlobalContext;

/**
 * Created by WuYue on 2015/12/15.
 */
public class ToastUtils {
    private static int DURATION_SHORT=2000;
    private static int DURATION_LONG=3000;

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    private static void showToast(Context mContext, String text, int duration) {
        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, duration==Toast.LENGTH_SHORT?DURATION_SHORT:DURATION_LONG);

        mToast.show();
    }

    private static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }

    public static void toast(int id) {
        ToastUtils.showToast(GlobalContext.getInstance(), id, Toast.LENGTH_SHORT);
    }

    public static void toast(String s) {
        ToastUtils.showToast(GlobalContext.getInstance(), s, Toast.LENGTH_SHORT);
    }

    public static void toastLong(int id) {
        ToastUtils.showToast(GlobalContext.getInstance(), id, Toast.LENGTH_LONG);
    }

    public static void toastLong(String s) {
        ToastUtils.showToast(GlobalContext.getInstance(), s, Toast.LENGTH_LONG);
    }
}
