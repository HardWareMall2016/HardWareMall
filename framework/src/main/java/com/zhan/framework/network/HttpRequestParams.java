package com.zhan.framework.network;

import android.util.Log;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WuYue on 2016/4/21.
 */
public class HttpRequestParams {
    protected final ConcurrentHashMap<String, String> urlParams;
    protected final ConcurrentHashMap<String, RequestParams.FileWrapper> fileParams;
    protected final ConcurrentHashMap<String, List<RequestParams.FileWrapper>> fileArrayParams;
    protected final ConcurrentHashMap<String, Object> urlParamsWithObjects;

    protected String contentEncoding;

    private RequestParams mAsyncHttpRequestParams=new RequestParams();
    private HashMap<String,String> mVolleyRequestParams=new HashMap<>();

    public HttpRequestParams() {
        this((Map)null);
    }

    public HttpRequestParams(Map<String, String> source) {
        this.urlParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();
        this.fileArrayParams = new ConcurrentHashMap();
        this.urlParamsWithObjects = new ConcurrentHashMap();
        this.contentEncoding = "UTF-8";
        if(source != null) {
            Iterator var2 = source.entrySet().iterator();
            while(var2.hasNext()) {
                Map.Entry entry = (Map.Entry)var2.next();
                this.put((String)entry.getKey(), (String)entry.getValue());
            }
        }
    }

    public HttpRequestParams(final String key, final String value) {
        this((Map) (new HashMap() {
            {
                this.put(key, value);
            }
        }));
    }

    public void setContentEncoding(String encoding) {
        mAsyncHttpRequestParams.setContentEncoding(encoding);
        if(encoding != null) {
            this.contentEncoding = encoding;
        } else {
            Log.d("HttpRequestParams", "setContentEncoding called with null attribute");
        }
    }

    public void put(String key, String value) {
        mAsyncHttpRequestParams.put(key,value);
        mVolleyRequestParams.put(key, value);
        if(key != null && value != null) {
            this.urlParams.put(key, value);
        }
    }

    public void put(String key, File[] files) throws FileNotFoundException {
        this.put(key, (File[]) files, (String) null, (String) null);
    }

    public void put(String key, File[] files, String contentType, String customFileName) throws FileNotFoundException {
        mAsyncHttpRequestParams.put(key, files, contentType, customFileName);
    }

    public void put(String key, File file) throws FileNotFoundException {
        this.put(key, (File)file, (String)null, (String)null);
    }

    public void put(String key, String customFileName, File file) throws FileNotFoundException {
        this.put(key, (File)file, (String)null, customFileName);
    }

    public void put(String key, File file, String contentType) throws FileNotFoundException {
        this.put(key, (File) file, contentType, (String) null);
    }

    public void put(String key, File file, String contentType, String customFileName) throws FileNotFoundException {
        mAsyncHttpRequestParams.put(key,file,contentType,customFileName);
    }

    public void put(String key, Object value) {
        mAsyncHttpRequestParams.put(key,value);
        if(key != null && value != null) {
            mVolleyRequestParams.put(key,String.valueOf(value));
        }
        if(key != null && value != null) {
            this.urlParamsWithObjects.put(key, value);
        }
    }

    public void put(String key, int value) {
        mAsyncHttpRequestParams.put(key,value);
        mVolleyRequestParams.put(key,String.valueOf(value));
        if(key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        mAsyncHttpRequestParams.put(key,value);
        mVolleyRequestParams.put(key,String.valueOf(value));
        if(key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }


    public RequestParams parseToAsyncHttpRequestParams(){
        return mAsyncHttpRequestParams;
    }

    public HashMap<String,String> parseToVolleyRequestParams(){
        return mVolleyRequestParams;
    }

}
