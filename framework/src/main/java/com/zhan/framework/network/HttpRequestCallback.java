package com.zhan.framework.network;

public interface HttpRequestCallback {
	void onPrepare();
	void onRequestFailed(String errorMsg);
	void onRequestFailedNoNetwork();
	void onTimeout();
	void onRequestCanceled();
	void onRequestSucceeded(String content);
}
