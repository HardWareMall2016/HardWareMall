package com.zhan.framework.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;


import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.common.setting.SettingUtility;
import com.zhan.framework.network.FileDownloadCallback;
import com.zhan.framework.network.HttpRequestCallback;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.InjectUtility;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.DialogUtils;
import com.zhan.framework.utils.Logger;
import com.zhan.framework.utils.ViewUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class BaseActivity extends ActionBarActivity  {

    static final String TAG = "Activity-Base";

    private BaseActivityHelper mHelper;

    private Dialog mProgressDlg;

    private int theme = 0;// 当前界面设置的主题

    private Locale language = null;// 当前界面的语言

    private boolean isDestory;

    // 当有Fragment Attach到这个Activity的时候，就会保存
    private Map<String, WeakReference<ABaseFragment>> fragmentRefs;

    private static BaseActivity runningActivity;

    private List<RequestHandle> mRequestHandleList = new LinkedList<>();

    public static BaseActivity getRunningActivity() {
        return runningActivity;
    }

    public static void setRunningActivity(BaseActivity activity) {
        runningActivity = activity;
    }

    protected int configTheme() {
        if (mHelper != null) {
            int theme = mHelper.configTheme();
            if (theme > 0)
                return theme;
        }

        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mHelper == null) {
            try {
                if (SettingUtility.getStringSetting("activity_helper") != null) {
                    mHelper = (BaseActivityHelper) Class.forName(SettingUtility.getStringSetting("activity_helper")).newInstance();
                    mHelper.bindActivity(this);
                }
            } catch (Exception e) {

            }
        }

        if (mHelper != null)
            mHelper.onCreate(savedInstanceState);

        fragmentRefs = new HashMap<String, WeakReference<ABaseFragment>>();

        if (savedInstanceState == null) {
            theme = configTheme();

            language = new Locale(SettingUtility.getPermanentSettingAsStr("language", Locale.getDefault().getLanguage()),
                    SettingUtility.getPermanentSettingAsStr("language-country", Locale.getDefault().getCountry()));
        } else {
            theme = savedInstanceState.getInt("theme");

            language = new Locale(savedInstanceState.getString("language"), savedInstanceState.getString("language-country"));
        }
        // 设置主题
        if (theme > 0)
            setTheme(theme);
        // 设置语言
        setLanguage(language);

        // 如果设备有实体MENU按键，overflow菜单不会再显示
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
        if (viewConfiguration.hasPermanentMenuKey()) {
            try {
                Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(viewConfiguration, false);
            } catch (Exception e) {
            }
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (mHelper != null)
            mHelper.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mHelper != null)
            mHelper.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (mHelper != null)
            mHelper.onRestart();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    View getRootView() {
        return getUserView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view);
        InjectUtility.initInjectedView(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        InjectUtility.initInjectedView(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mHelper != null)
            mHelper.onSaveInstanceState(outState);

        outState.putInt("theme", theme);
        outState.putString("language", language.getLanguage());
        outState.putString("language-country", language.getCountry());
    }

    public void addFragment(String tag, ABaseFragment fragment) {
        fragmentRefs.put(tag, new WeakReference<ABaseFragment>(fragment));
    }

    public void removeFragment(String tag) {
        fragmentRefs.remove(tag);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mHelper != null)
            mHelper.onResume();

        setRunningActivity(this);

        if (theme == configTheme()) {

        } else {
            Logger.i("theme changed, reload()");
            reload();

            return;
        }

        String languageStr = SettingUtility.getPermanentSettingAsStr("language", Locale.getDefault().getLanguage());
        String country = SettingUtility.getPermanentSettingAsStr("language-country", Locale.getDefault().getCountry());
        if (language != null && language.getLanguage().equals(languageStr) && country.equals(language.getCountry())) {

        }
        else {
            Logger.i("language changed, reload()");
            reload();

            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mHelper != null)
            mHelper.onPause();
    }

    public void setLanguage(Locale locale) {
        Resources resources = getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        config.locale = locale;
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        resources.updateConfiguration(config, dm);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mHelper != null)
            mHelper.onStop();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        for (RequestHandle request : mRequestHandleList) {
            HttpRequestUtils.releaseRequest(request);
        }
        closeRotateProgressDialog();
        isDestory = true;
        super.onDestroy();
        if (mHelper != null)
            mHelper.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mHelper != null) {
            boolean handle = mHelper.onOptionsItemSelected(item);
            if (handle)
                return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                if (onHomeClick())
                    return true;
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean onHomeClick() {
        if (mHelper != null) {
            boolean handle = mHelper.onHomeClick();
            if (handle)
                return true;
        }

        return onBackClick();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mHelper != null) {
            boolean handle = mHelper.onKeyDown(keyCode, event);
            if (handle)
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if(!onBackClick()){
            super.onBackPressed();
        }
    }

    public boolean onBackClick() {
        if (mHelper != null) {
            boolean handle = mHelper.onBackClick();
            if (handle)
                return true;
        }

        Set<String> keys = fragmentRefs.keySet();
        for (String key : keys) {
            WeakReference<ABaseFragment> fragmentRef = fragmentRefs.get(key);
            ABaseFragment fragment = fragmentRef.get();
            if (fragment != null && fragment.onBackClick())
                return true;
        }

        return false;
    }

    /**
     * 以Toast形式显示一个消息
     *
     * @param msg
     */
    public void showMessage(CharSequence msg) {
        ViewUtils.showMessage(msg.toString());
    }

    /**
     * @param msgId
     */
    public void showMessage(int msgId) {
        showMessage(getText(msgId));
    }

    @Override
    public void finish() {
        // 2014-09-12 解决ATabTitlePagerFragment的destoryFragments方法报错的BUG
        setMDestory(true);

        super.finish();

        if (mHelper != null) {
            mHelper.finish();
        }
    }

    public boolean mIsDestoryed() {
        return isDestory;
    }

    public void setMDestory(boolean destory) {
        this.isDestory = destory;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mHelper != null) {
            mHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    public BaseActivityHelper getActivityHelper() {
        return mHelper;
    }

    /***
     * 显示进度对话框
     * @param msg
     * @param cancelable
     */
    public void showRotateProgressDialog(String msg, boolean cancelable) {
        mProgressDlg = DialogUtils.getRotateProgressDialog(this, msg);
        mProgressDlg.setCancelable(cancelable);
        mProgressDlg.show();
    }

    /***
     * 关闭显示进度对话框
     */
    public void closeRotateProgressDialog() {
        if (mProgressDlg != null) {
            mProgressDlg.dismiss();
            mProgressDlg = null;
        }
    }

    /***
     * 网络请求
     *
     * @param apiUrl
     * @param requestParams
     * @param requestCallback
     * @param requestType
     * @return
     */
    public RequestHandle startRequest(String apiUrl, RequestParams requestParams, HttpRequestCallback requestCallback, HttpRequestUtils.RequestType requestType) {
        RequestHandle handle = HttpRequestUtils.startRequest(apiUrl, requestParams, requestCallback, requestType);
        if (handle != null) {
            mRequestHandleList.add(handle);
        }
        return handle;
    }

    /***
     * 网络请求
     * @param apiUrl
     * @param requestJsonBean
     * @param requestCallback
     * @return
     */
    public RequestHandle startRequest(String apiUrl, Object requestJsonBean, HttpRequestCallback requestCallback) {
        RequestHandle handle = HttpRequestUtils.startRequest(apiUrl, requestJsonBean, requestCallback);
        if (handle != null) {
            mRequestHandleList.add(handle);
        }
        return handle;
    }

    /***
     * 下载文件
     *
     * @param serviceFileUrl
     * @param saveFilePath
     * @param callback
     * @return
     */
    public RequestHandle downloadFile(String serviceFileUrl, String saveFilePath, FileDownloadCallback callback) {
        RequestHandle handle = HttpRequestUtils.downloadFile(serviceFileUrl, saveFilePath, callback);
        if (handle != null) {
            mRequestHandleList.add(handle);
        }
        return handle;
    }

    /***
     * 释放网络请求
     *
     * @param request
     */
    public void releaseRequest(RequestHandle request) {
        HttpRequestUtils.releaseRequest(request);
    }

    /***
     * 检查网络请求是否还在处理中
     *
     * @param request
     * @return
     */
    public boolean isRequestProcessing(RequestHandle request) {
        if (request != null && !request.isFinished()) {
            return true;
        }
        return false;
    }
}
