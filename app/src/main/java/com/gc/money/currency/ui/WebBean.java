package com.gc.money.currency.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class WebBean implements Parcelable {

    public String title;
    public String url;
    public boolean hasTitleBar;
    public boolean rewriteTitle;
    public String stateBarTextColor;
    public String titleTextColor;
    public String titleColor;
    public boolean webBack;

    public WebBean() {
    }

    protected WebBean(Parcel in) {
        title = in.readString();
        url = in.readString();
        hasTitleBar = in.readByte() != 0;
        rewriteTitle = in.readByte() != 0;
        stateBarTextColor = in.readString();
        titleTextColor = in.readString();
        titleColor = in.readString();
        webBack = in.readByte() != 0;
    }

    public static final Creator<WebBean> CREATOR = new Creator<WebBean>() {
        @Override
        public WebBean createFromParcel(Parcel in) {
            return new WebBean(in);
        }

        @Override
        public WebBean[] newArray(int size) {
            return new WebBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeByte((byte) (hasTitleBar ? 1 : 0));
        dest.writeByte((byte) (rewriteTitle ? 1 : 0));
        dest.writeString(stateBarTextColor);
        dest.writeString(titleTextColor);
        dest.writeString(titleColor);
        dest.writeByte((byte) (webBack ? 1 : 0));
    }
}
