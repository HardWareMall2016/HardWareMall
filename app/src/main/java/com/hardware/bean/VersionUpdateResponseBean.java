package com.hardware.bean;

/**
 * Created by WuYue on 2016/4/14.
 */
public class VersionUpdateResponseBean {
    private int VersionCode;
    private boolean ForcedUpdate;
    private String url;
    private String introduce;

    public int getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(int VersionCode) {
        this.VersionCode = VersionCode;
    }

    public boolean isForcedUpdate() {
        return ForcedUpdate;
    }

    public void setForcedUpdate(boolean ForcedUpdate) {
        this.ForcedUpdate = ForcedUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
