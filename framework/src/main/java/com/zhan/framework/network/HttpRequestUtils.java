package com.zhan.framework.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.R;
import com.zhan.framework.common.context.GlobalContext;
import com.zhan.framework.common.setting.SettingUtility;
import com.zhan.framework.utils.Consts;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

public class HttpRequestUtils {
    private final static String TAG = "HttpRequestUtils";

    public enum RequestType {GET, POST}

    /***
     * @param apiUrl
     * @param requestParams   参数组，requestType为get时此参数无效
     * @param requestCallback
     * @param requestType     请求类型GET/POST
     * @return
     */
    public static RequestHandle startRequest(String apiUrl, RequestParams requestParams, final HttpRequestCallback requestCallback, RequestType requestType) {
        RequestHandle mRequestHandle = null;
        Context context= GlobalContext.getInstance();
        final Resources mResources = context.getResources();

        String requestUrl = SettingUtility.getSetting(Consts.BASE_URL).getValue() + apiUrl;

        Log.i(TAG, "requestUrl = " + requestUrl);
        Log.i(TAG, "requestType = " + requestType);

        if (requestCallback != null) {
            requestCallback.onPrepare();
        }

        if (!Connectivity.isConnected(context)) {
            if (requestCallback != null) {
                requestCallback.onRequestFailedNoNetwork();
            }
            return null;
        }

        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    String content = new String(responseBody);
                    Log.i(TAG, "onFailure responseBody = " + content);
                }

                Log.i(TAG, "onFailure statusCode = " + statusCode);
                if (requestCallback != null) {
                    if (statusCode == 0) {
                        requestCallback.onTimeout();
                    } else {
                        requestCallback.onRequestFailed(mResources.getString(R.string.comm_unknow_service_error));
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.i(TAG, "onSuccess statusCode = " + statusCode);
                Log.i(TAG, "onSuccess responseBody = " + content);
                if (requestCallback != null) {
                    requestCallback.onRequestSucceeded(content);
                }
            }

            @Override
            public void onCancel() {
                if (requestCallback != null) {
                    requestCallback.onRequestCanceled();
                }
            }
        };

        //Header
        Header[] headers = new Header[2];
        headers[0] = new BasicHeader("platform", "Android");
        headers[1] = new BasicHeader("version", getVersion(context));
        //headers[2]=new BasicHeader("qAuthor", Utils.getQAuthor(context));

        switch (requestType) {
            case GET:
                if(requestParams!=null){
                mRequestHandle = HttpClientUtils.get(requestUrl, headers, requestParams, responseHandler);
                }else{
                    mRequestHandle=HttpClientUtils.get(requestUrl,responseHandler);
                }
                break;
		    /*case DELETE:
                mRequestHandle=HttpClientUtils.delete(apiUrl,requestParams,responseHandler);
                break;*/
            case POST:
                mRequestHandle = HttpClientUtils.post(requestUrl, headers, requestParams, responseHandler);
                break;
		    /*case PUT:
                mRequestHandle=HttpClientUtils.put(apiUrl, requestParams,responseHandler);
                break;*/
            default:
                break;
        }

        return mRequestHandle;
    }

    /***
     * @param apiUrl
     * @param requestBean
     * @param requestCallback
     * @return
     */
    public static RequestHandle startRequest(String apiUrl, Object requestBean, final HttpRequestCallback requestCallback) {
        Context context= GlobalContext.getInstance();
        final Resources mResources = context.getResources();

        String requestUrl = SettingUtility.getSetting(Consts.BASE_URL).getValue() + apiUrl;

        Log.i(TAG, "requestUrl = " + requestUrl);

        if (requestCallback != null) {
            requestCallback.onPrepare();
        }

        if (!Connectivity.isConnected(context)) {
            if (requestCallback != null) {
                requestCallback.onRequestFailedNoNetwork();
            }
            return null;
        }

        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody != null) {
                    String content = new String(responseBody);
                    Log.i(TAG, "onFailure responseBody = " + content);
                }

                Log.i(TAG, "onFailure statusCode = " + statusCode);
                if (requestCallback != null) {
                    if (statusCode == 0) {
                        requestCallback.onTimeout();
                    } else {
                        requestCallback.onRequestFailed(mResources.getString(R.string.comm_unknow_service_error));
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.i(TAG, "onSuccess statusCode = " + statusCode);
                Log.i(TAG, "onSuccess responseBody = " + content);
                if (requestCallback != null) {
                    requestCallback.onRequestSucceeded(content);
                }
            }

            @Override
            public void onCancel() {
                if (requestCallback != null) {
                    requestCallback.onRequestCanceled();
                }
            }
        };

        //Header
        Header[] headers = new Header[2];
        //headers[0] = new BasicHeader("Content-Type", "application/json");
        headers[0] = new BasicHeader("platform", "Android");
        headers[1] = new BasicHeader("version", getVersion(context));

        String jsonStr = JSON.toJSONString(requestBean);
        HttpEntity entity = new StringEntity(jsonStr,"utf-8");

        Log.i(TAG, "jsonStr = " + jsonStr);

        return HttpClientUtils.post(requestUrl, headers, entity,"application/json", responseHandler);
    }


    private static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            return "1.0.0";
        }
    }

    public static RequestHandle downloadFile(String serviceFileUrl, String saveFilePath, final FileDownloadCallback callback) {
        Context context= GlobalContext.getInstance();
        final Resources mResources = context.getResources();
        Log.i(TAG, "downloadFile = " + serviceFileUrl);

        callback.onPrepare();

        if (TextUtils.isEmpty(serviceFileUrl)) {
            callback.onDownloadFailed(mResources.getString(R.string.comm_network_download_path_null));
            return null;
        }

        if (!Connectivity.isConnected(context)) {
            callback.onNoNetwork();
            return null;
        }

        File saveFile = new File(saveFilePath);
        FileAsyncHttpResponseHandler responseHandler = new FileAsyncHttpResponseHandler(saveFile) {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Log.i(TAG, "onFailure statusCode = " + statusCode);
                if (statusCode == 0) {
                    callback.onTimeout();
                } else {
                    callback.onDownloadFailed(mResources.getString(R.string.comm_download_fialed));
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                Log.i(TAG, "onSuccess statusCode = " + statusCode);
                Log.i(TAG, "onSuccess file length = " + file.length());
                if (file.length() == 0) {
                    callback.onDownloadFailed(mResources.getString(R.string.comm_download_fialed));
                } else {
                    callback.onDownloadSuccess(file);
                }
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                callback.onProgress(bytesWritten, totalSize);
            }

            @Override
            public void onCancel() {
                callback.onCanceled();
            }
        };
        return HttpClientUtils.get(serviceFileUrl, responseHandler);
    }

    public static void releaseRequest(RequestHandle request) {
        if (request != null && !request.isFinished()) {
            request.cancel(true);
        }
    }
}
