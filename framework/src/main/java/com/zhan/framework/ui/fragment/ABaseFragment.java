package com.zhan.framework.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.R;
import com.zhan.framework.common.setting.SettingUtility;
import com.zhan.framework.network.FileDownloadCallback;
import com.zhan.framework.network.HttpRequestCallback;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.InjectUtility;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.activity.BaseActivityHelper;
import com.zhan.framework.utils.DialogUtils;
import com.zhan.framework.utils.ViewUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdan on 15-1-16.
 */
public abstract class ABaseFragment extends Fragment {
    static final String TAG = ABaseFragment.class.getSimpleName();

    protected enum ABaseTaskState {
        none, prepare, falid, success, finished, canceled
    }

    private ViewGroup rootView;// 根视图

    @ViewInject(idStr = "layoutLoading")
    protected
    View loadingLayout;// 加载中视图
    @ViewInject(idStr = "layoutLoadFailed")
    protected
    View loadFailureLayout;// 加载失败视图
    @ViewInject(idStr = "layoutContent")
    protected
    View contentLayout;// 内容视图
    @ViewInject(idStr = "layoutEmpty")
    protected
    View emptyLayout;// 空视图

    // 标志是否ContentView是否为空
    private boolean contentEmpty = true;

    //private List<RequestHandle> mRequestHandleList = new LinkedList<>();
    private HashMap<String,RequestHandle> mRequestHandleList=new HashMap<>();

    private Dialog mProgressDlg;

    private BaseFragmentHelper mHelper;

    public void onActionBarMenuClick() {

    }

    public void onPrepareActionbarMenu(TextView menu) {

    }

    public void onCreateCustomActionMenu(LinearLayout menuContent) {

    }

    public void onCreateCustomActionbarBar(FrameLayout customerContent) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity)
            ((BaseActivity) activity).addFragment(toString(), this);
        if (mHelper == null) {
            try {
                if (SettingUtility.getStringSetting("fragment_helper") != null) {
                    mHelper = (BaseFragmentHelper) Class.forName(SettingUtility.getStringSetting("fragment_helper")).newInstance();
                    mHelper.bindFragment(this);
                    mHelper.onAttach(activity);
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mHelper != null)
            mHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (inflateContentView() > 0) {

            if(rootView!=null&&isCacheRootView()){
                onInitCachedRootView(rootView);
            }else{
                rootView = (ViewGroup) inflater.inflate(inflateContentView(), null);
                rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                _layoutInit(inflater, savedInstanceState);

                layoutInit(inflater, savedInstanceState);
            }
            if (mHelper != null)
                mHelper.onCreateView(rootView,savedInstanceState);
            return rootView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHelper != null)
            mHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mHelper != null)
            mHelper.onPause();
    }

    /***
     * 是否缓存 RootView
     * @return
     */
    public boolean isCacheRootView(){
        return false;
    }

    public void onInitCachedRootView(View rootView){
    }

    @Override
    public void onDestroyView() {
        /*for (RequestHandle request : mRequestHandleList) {
            HttpRequestUtils.releaseRequest(request);
        }*/
        releaseAllRequest();
        closeRotateProgressDialog();
        if (mHelper != null)
            mHelper.onDestroyView();
        super.onDestroyView();
    }

    public void releaseAllRequest(){
        Iterator<Map.Entry<String, RequestHandle>> iterator = mRequestHandleList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, RequestHandle> entry = iterator.next();
            HttpRequestUtils.releaseRequest(entry.getValue());
        }
        /*for (RequestHandle request : mRequestHandleList) {
            HttpRequestUtils.releaseRequest(request);
        }*/
        mRequestHandleList.clear();
    }

    /**
     * 根视图
     *
     * @return
     */
    public ViewGroup getRootView() {
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null)
            requestData();
    }

    /**
     * Action的home被点击了
     *
     * @return
     */
    public boolean onHomeClick() {
        return onBackClick();
    }

    /**
     * 返回按键被点击了
     *
     * @return
     */
    public boolean onBackClick() {
        return false;
    }

    /**
     * 初次创建时默认会调用一次
     */
    public void requestData() {

    }

    /**
     * A*Fragment重写这个方法
     *
     * @param inflater
     * @param savedInstanceSate
     */
    void _layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        InjectUtility.initInjectedView(this, rootView);

        if (emptyLayout != null) {
            View reloadView = emptyLayout.findViewById(R.id.layoutReload);
            if (reloadView != null)
                setViewOnClick(reloadView);
        }

        if (loadFailureLayout != null) {
            View reloadView = loadFailureLayout.findViewById(R.id.layoutReload);
            if (reloadView != null)
                setViewOnClick(reloadView);
        }

        setViewVisiable(loadingLayout, View.GONE);
        setViewVisiable(loadFailureLayout, View.GONE);
        setViewVisiable(emptyLayout, View.GONE);
        if (isContentEmpty()) {
            if (savedInstanceSate != null) {
                requestData();
            } else {
                setViewVisiable(emptyLayout, View.VISIBLE);
                setViewVisiable(contentLayout, View.GONE);
            }
        } else {
            setViewVisiable(contentLayout, View.VISIBLE);
        }
    }

    /**
     * 子类重写这个方法，初始化视图
     *
     * @param inflater
     * @param savedInstanceSate
     */
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {

    }

    protected View findViewById(int viewId) {
        if (rootView == null)
            return null;

        return rootView.findViewById(viewId);
    }

    abstract protected int inflateContentView();

    public void setContentEmpty(boolean empty) {
        this.contentEmpty = empty;
    }

    public boolean isContentEmpty() {
        return contentEmpty;
    }

    /**
     * 视图点击回调，子类重写
     *
     * @param view
     */
    public void onViewClicked(View view) {
        if (view.getId() == R.id.layoutReload)
            requestData();
        else if (view.getId() == R.id.layoutRefresh)
            requestData();
    }

    protected void setViewVisiable(View v, int visibility) {
        if (v != null)
            v.setVisibility(visibility);
    }

    /**
     * 根据{@link BaseHttpRequestTask} 的加载状态，刷新视图
     *
     * @param state
     * @param tag
     */
    protected void taskStateChanged(ABaseTaskState state, Serializable tag) {
        // 开始Task
        if (state == ABaseTaskState.prepare) {
            if (isContentEmpty()) {
                setViewVisiable(loadingLayout, View.VISIBLE);

                setViewVisiable(contentLayout, View.GONE);
            } else {
                setViewVisiable(loadingLayout, View.GONE);

                setViewVisiable(contentLayout, View.VISIBLE);
            }

            setViewVisiable(emptyLayout, View.GONE);
            setViewVisiable(loadFailureLayout, View.GONE);
        }
        // Task成功
        else if (state == ABaseTaskState.success) {
            setViewVisiable(loadingLayout, View.GONE);

            if (isContentEmpty()) {
                setViewVisiable(emptyLayout, View.VISIBLE);
            } else {
                setViewVisiable(contentLayout, View.VISIBLE);
            }
        }
        // 取消Task
        else if (state == ABaseTaskState.canceled) {
            if (isContentEmpty()) {
                setViewVisiable(loadingLayout, View.GONE);
                setViewVisiable(emptyLayout, View.VISIBLE);
            }
        }
        // Task失败
        else if (state == ABaseTaskState.falid) {
            if (isContentEmpty()) {
                setViewVisiable(emptyLayout, View.GONE);
                setViewVisiable(loadingLayout, View.GONE);
                setViewVisiable(loadFailureLayout, View.VISIBLE);
                if (tag != null && loadFailureLayout != null)
                    ViewUtils.setTextViewValue(loadFailureLayout, R.id.txtLoadFailed, tag.toString());
            }
        }
        // Task结束
        else if (state == ABaseTaskState.finished) {

        }
    }

    /**
     * 以Toast形式显示一个消息
     *
     * @param msg
     */
    protected void showMessage(CharSequence msg) {
        if (!TextUtils.isEmpty(msg))
            ViewUtils.showMessage(msg.toString());
    }

    /**
     * 参照{@linkplain #showMessage(int)}
     *
     * @param msgId
     */
    protected void showMessage(int msgId) {
        if (getActivity() != null)
            showMessage(getString(msgId));
    }

    /**
     * Fragment主要的刷新任务线程，定义任务加载流程，耦合Fragment各个状态下的视图刷新方法
     */
    protected abstract class BaseHttpRequestTask<ResponseBean> extends HttpRequestHandler {

        public void beforeOnPrepare(){

        }

        @Override
        public void onPrepare() {
            beforeOnPrepare();
            taskStateChanged(ABaseTaskState.prepare, null);
        }

        @Override
        final public void onRequestSucceeded(String content) {
            ResponseBean result = parseResponseToResult(content);
            String errorMsg=verifyResponseResult(result);
            if(TextUtils.isEmpty(errorMsg)){
                onSuccess(result);
                super.onRequestSucceeded(content);
            }else{
                super.onRequestFailed(errorMsg);
            }
        }

        /**
         * setContentEmpty(false);//默认加载数据成功，且ContentView有数据展示
         * 异步执行方法
         * @return
         */
        abstract public ResponseBean parseResponseToResult(String content);

        /***
         * 验证数据的有效性，无效或出错就放回错误信息
         * @param result
         * @return
         */
        public String verifyResponseResult(ResponseBean result){
            return null;
        }

        protected void onSuccess(ResponseBean result) {
            setContentEmpty(resultIsEmpty(result));
        }
        /**
         * 返回数据是否空
         *
         * @param result
         * @return
         */
        protected boolean resultIsEmpty(ResponseBean result) {
            return result == null ? true : false;
        }

        @Override
        public void onRequestFinished(ResultCode resultCode, String result) {
            switch (resultCode) {
                case noNetwork:
                    taskStateChanged(ABaseTaskState.falid, result);
                    break;
                case timeout:
                    taskStateChanged(ABaseTaskState.falid, result);
                    break;
                case failed:
                    taskStateChanged(ABaseTaskState.falid, result);
                    break;
                case success:
                    taskStateChanged(ABaseTaskState.success, null);
                    break;
                case canceled:
                    taskStateChanged(ABaseTaskState.canceled, result);
                    break;
            }
            taskStateChanged(ABaseTaskState.finished, null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).removeFragment(this.toString());
    }

    protected void setViewOnClick(View v) {
        if (v == null)
            return;
        v.setOnClickListener(innerOnClickListener);
    }

    View.OnClickListener innerOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onViewClicked(v);
        }

    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /***
     * 显示进度对话框
     *
     * @param msg
     * @param cancelable
     */
    public void showRotateProgressDialog(String msg, boolean cancelable) {
        mProgressDlg = DialogUtils.getRotateProgressDialog(getActivity(), msg);
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
        //先释放上次的请求
        releaseRequest(apiUrl);
        RequestHandle handle = HttpRequestUtils.startRequest(apiUrl, requestParams, requestCallback, requestType);
        if (handle != null) {
            //mRequestHandleList.add(handle);
            mRequestHandleList.put(apiUrl,handle);
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
        //先释放上次的请求
        releaseRequest(apiUrl);
        RequestHandle handle = HttpRequestUtils.startRequest(apiUrl, requestJsonBean, requestCallback);
        if (handle != null) {
            //mRequestHandleList.add(handle);
            mRequestHandleList.put(apiUrl, handle);
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
        //先释放上次的请求
        releaseRequest(serviceFileUrl);
        RequestHandle handle = HttpRequestUtils.downloadFile(serviceFileUrl, saveFilePath, callback);
        if (handle != null) {
            //mRequestHandleList.add(handle);
            mRequestHandleList.put(serviceFileUrl,handle);
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

    /***
     * 释放网络请求
     *
     * @param apiUrl
     */
    public void releaseRequest(String apiUrl) {
        RequestHandle request=mRequestHandleList.get(apiUrl);
        releaseRequest(request);
    }

    /***
     * 检查网络请求是否还在处理中
     *
     * @param apiUrl
     * @return
     */
    public boolean isRequestProcessing(String apiUrl) {
        RequestHandle request=mRequestHandleList.get(apiUrl);
        return isRequestProcessing(request);
    }
}
