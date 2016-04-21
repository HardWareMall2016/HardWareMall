package com.zhan.framework.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.zhan.framework.R;
import com.zhan.framework.common.context.GlobalContext;
import com.zhan.framework.common.setting.SettingUtility;
import com.zhan.framework.utils.Consts;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

public class HttpRequestUtils {
    private final static String TAG = "HttpRequestUtils";

    public static final String DRF_REQUEST_TAG = "VolleyPatterns";

    private static RequestQueue sRequestQueue;

    public enum RequestType {GET, POST}

    public static RequestHandle startRequest(String apiUrl, RequestParams requestParams, final HttpRequestCallback requestCallback, RequestType requestType) {
       return startRequest(Consts.BASE_URL,apiUrl, requestParams,  requestCallback, requestType);
    }
    /***
     * @param apiUrl
     * @param requestParams   参数组，requestType为get时此参数无效
     * @param requestCallback
     * @param requestType     请求类型GET/POST
     * @return
     */
    public static RequestHandle startRequest(String baseUrlSettingKey,String apiUrl, RequestParams requestParams, final HttpRequestCallback requestCallback, RequestType requestType) {
        RequestHandle mRequestHandle = null;
        Context context= GlobalContext.getInstance();
        final Resources mResources = context.getResources();

        String requestUrl;
        if(TextUtils.isEmpty(baseUrlSettingKey)){
            requestUrl = SettingUtility.getSetting(Consts.BASE_URL).getValue() + apiUrl;
        }else{
            requestUrl = SettingUtility.getSetting(baseUrlSettingKey).getValue() + apiUrl;
        }
        //String requestUrl = SettingUtility.getSetting(Consts.BASE_URL).getValue() + apiUrl;

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
        /*Header[] headers = new Header[2];
        headers[0] = new BasicHeader("platform", "Android");
        headers[1] = new BasicHeader("version", getVersion(context));*/

        switch (requestType) {
            case GET:
                if(requestParams!=null){
                //mRequestHandle = HttpClientUtils.get(requestUrl, headers, requestParams, responseHandler);
                    mRequestHandle = HttpClientUtils.get(requestUrl, requestParams, responseHandler);
                }else{
                    mRequestHandle=HttpClientUtils.get(requestUrl,responseHandler);
                }
                break;
		    /*case DELETE:
                mRequestHandle=HttpClientUtils.delete(apiUrl,requestParams,responseHandler);
                break;*/
            case POST:
                //mRequestHandle = HttpClientUtils.post(requestUrl, headers, requestParams, responseHandler);
                mRequestHandle = HttpClientUtils.post(requestUrl, requestParams, responseHandler);
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

    //下面是Volley框架的请求
    public static RequestQueue getRequestQueue(){
        if (sRequestQueue == null) {
            sRequestQueue = Volley.newRequestQueue(GlobalContext.getInstance());
        }
        return sRequestQueue;
    }

    public static <T> void addToRequestQueue(Request<T> req, String apiUrl) {
        req.setTag(TextUtils.isEmpty(apiUrl) ? DRF_REQUEST_TAG : apiUrl);
        getRequestQueue().add(req);
    }

    public static <T> void addToRequestQueue(Request<T> req) {
        req.setTag(DRF_REQUEST_TAG);
        getRequestQueue().add(req);
    }

    public static void cancelPendingRequests(String apiUrl) {
        if (sRequestQueue != null) {
            sRequestQueue.cancelAll(apiUrl);
        }
    }

    public static StringRequest startVolleyRequest(String baseUrlSettingKey,String apiUrl, final HashMap<String,String> requestParams, final HttpRequestCallback requestCallback, final RequestType requestType) {
        String requestUrl;
        final boolean[] hasFinished = {false};

        if (TextUtils.isEmpty(baseUrlSettingKey)) {
            requestUrl = SettingUtility.getSetting(Consts.BASE_URL).getValue() + apiUrl;
        } else {
            requestUrl = SettingUtility.getSetting(baseUrlSettingKey).getValue() + apiUrl;
        }
        //String requestUrl = SettingUtility.getSetting(Consts.BASE_URL).getValue() + apiUrl;

        Log.i(TAG, "requestType = " + requestType);

        if (requestCallback != null) {
            requestCallback.onPrepare();
        }

        if (!Connectivity.isConnected(GlobalContext.getInstance())) {
            hasFinished[0] =true;
            if (requestCallback != null) {
                requestCallback.onRequestFailedNoNetwork();
            }
            return null;
        }
        if (requestParams != null && requestParams.size() > 0) {
            Iterator uee = requestParams.entrySet().iterator();
            while (uee.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) uee.next();
                if (entry.getValue() == null) {
                    entry.setValue("");
                }
                Log.i(TAG, "Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        }
        int method = Request.Method.POST;
        if (requestType == RequestType.GET) {
            method = Request.Method.GET;
            //Get请求参数
            if (requestParams != null && requestParams.size() > 0) {
                StringBuilder result = new StringBuilder();
                Map.Entry entry;
                Iterator params = requestParams.entrySet().iterator();
                while (params.hasNext()) {
                    entry = (Map.Entry) params.next();
                    if (result.length() > 0) {
                        result.append("&");
                    }
                    result.append(entry.getKey());
                    result.append("=");
                    result.append(entry.getValue());
                }
                requestUrl += "?" + result.toString();
            }
        }
        Log.i(TAG, "requestUrl = " + requestUrl);
        //这里要可以换JsonRequest,做Json请求
        StringRequest stringRequest = new StringRequest(method, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String responseStr) {
                Log.i(TAG, "onResponse = " + responseStr);
                hasFinished[0] =true;
                requestCallback.onRequestSucceeded(responseStr);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hasFinished[0] =true;
                if (requestCallback == null) {
                    return;
                }
                if (volleyError instanceof TimeoutError) {
                    requestCallback.onTimeout();
                } else {
                    String errorMsg = VolleyErrorHelper.getMessage(volleyError, GlobalContext.getInstance());
                    requestCallback.onRequestFailed(errorMsg);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //默认返回空集合
                return super.getHeaders();
                //可以如下
                /*HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");*/
            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (requestType == RequestType.GET){
                    return super.getParams();
                }else{
                    return requestParams;
                }
            }

            @Override
            public void onCanceled() {
                Log.i(TAG, "onCanceled requestUrl = "+getUrl());
                hasFinished[0] =true;
                if (requestCallback != null) {
                    requestCallback.onRequestCanceled();
                }
            }

            @Override
            protected void finish(String tag) {
                super.finish(tag);
                //如果都请求各个分支都没有返回，在这里认为是取消了
                if(!hasFinished[0]){
                    hasFinished[0] =true;
                    if (requestCallback != null) {
                        requestCallback.onRequestCanceled();
                    }
                }
            }
        };
        addToRequestQueue(stringRequest, apiUrl);
        return stringRequest;
    }
}
