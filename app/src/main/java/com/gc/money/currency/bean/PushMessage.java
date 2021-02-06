package com.gc.money.currency.bean;

public class PushMessage {


    private String createTime;
    private String pushTopic;
    private String pushContent;
    private String url;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPushTopic() {
        return pushTopic;
    }

    public void setPushTopic(String pushTopic) {
        this.pushTopic = pushTopic;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "createTime='" + createTime + '\'' +
                ", pushTopic='" + pushTopic + '\'' +
                ", pushContent='" + pushContent + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
