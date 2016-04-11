package com.zhan.framework.network;

import java.io.File;

/**
 * Created by WuYue on 2015/12/3.
 */
public abstract class FileDownloadHandler implements FileDownloadCallback {

    @Override
    public void onPrepare() {

    }

    @Override
    public void onDownloadFailed(String errorMsg) {
        onRequestFinished();
    }

    @Override
    public void onTimeout() {
        onRequestFinished();
    }

    @Override
    public void onNoNetwork() {
        onRequestFinished();
    }

    @Override
    public void onCanceled() {
        onRequestFinished();
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {

    }

    @Override
    public void onDownloadSuccess(File downFile) {
        onRequestFinished();
    }

    public void onRequestFinished(){}
}
