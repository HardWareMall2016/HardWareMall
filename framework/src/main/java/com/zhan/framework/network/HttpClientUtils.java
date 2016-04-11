package com.zhan.framework.network;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

public class HttpClientUtils {
    private static String sessionId = null;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static PersistentCookieStore cookieStore ;
    static {
        //设置网络超时时间
        client.setTimeout(5000);
    }
    
    public static RequestHandle get(String url, AsyncHttpResponseHandler responseHandler) {
    	return client.get(url, responseHandler);
    }
    
    public static RequestHandle delete(String url,ResponseHandlerInterface responseHandler) {
    	return client.delete(url, responseHandler);
    }

    public static RequestHandle get(Context context,String url,ResponseHandlerInterface responseHandler) {
    	return client.get(context, url, responseHandler);
    }
    
    public static RequestHandle put(String url,HttpEntity entity, ResponseHandlerInterface responseHandler) {
    	return client.put(null, url, entity, null, responseHandler);
    }
    
    public static RequestHandle get(String url,RequestParams params, ResponseHandlerInterface responseHandler) {
    	return client.get(url, params, responseHandler);
    }

    public static RequestHandle get(String url,Header[] headers,RequestParams params, ResponseHandlerInterface responseHandler) {
        return client.get(null, url, headers, params, responseHandler);
    }

    public static void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(context, url, params, responseHandler);
    }

    public static void get(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(context, url, headers, params, responseHandler);
    }
    
    public static RequestHandle post(String url,RequestParams params, ResponseHandlerInterface responseHandler){
       return client.post(url, params, responseHandler);
    }

    public static RequestHandle post(String url,Header[] headers,RequestParams params, ResponseHandlerInterface responseHandler){
        return client.post(null, url, headers, params, null, responseHandler);
    }
    
    public static RequestHandle post(String url,HttpEntity entity, ResponseHandlerInterface responseHandler){
        return client.post(null,url, entity,null, responseHandler);
    }

    public static RequestHandle post(String url,Header[] headers,HttpEntity entity,String contentType, ResponseHandlerInterface responseHandler){
        return client.post(null, url, headers, entity, contentType, responseHandler);
    }

    public static AsyncHttpClient getClient(){
        return client;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        HttpClientUtils.sessionId = sessionId;
    }

    public static PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    public static void setCookieStore(PersistentCookieStore cookieStore) {
        HttpClientUtils.cookieStore = cookieStore;
        client.setCookieStore(cookieStore);
    }
}
