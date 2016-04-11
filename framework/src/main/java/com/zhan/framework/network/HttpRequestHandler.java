package com.zhan.framework.network;

import com.zhan.framework.R;
import com.zhan.framework.common.context.GlobalContext;

/**
 * Created by WuYue on 2015/12/3.
 */
public abstract class HttpRequestHandler implements HttpRequestCallback {
    public enum ResultCode {
        noNetwork, timeout, failed, success, canceled
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onRequestFailed(String errorMsg) {
        onRequestFinished(ResultCode.failed,errorMsg);
    }

    @Override
    public void onRequestFailedNoNetwork() {
        onRequestFinished(ResultCode.noNetwork,GlobalContext.getInstance().getString(R.string.network_disconnect));
    }

    @Override
    public void onTimeout() {
        onRequestFinished(ResultCode.timeout, GlobalContext.getInstance().getString(R.string.network_timeout));
    }

    @Override
    public void onRequestCanceled() {
        onRequestFinished(ResultCode.canceled,GlobalContext.getInstance().getString(R.string.request_canceled));
    }

    @Override
    public void onRequestSucceeded(String content) {
        onRequestFinished(ResultCode.success,content);
    }

    public void onRequestFinished(ResultCode resultCode,String result) {
    }
}
