package com.chaoxing.player;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HuWei on 2017/8/31.
 */

public class Video implements Parcelable {

    private String dataSource;
    private String subtitle;

    public Video() {
    }

    public Video(String dataSource, String subtitle) {
        this.dataSource = dataSource;
        this.subtitle = subtitle;
    }

    protected Video(Parcel in) {
        dataSource = in.readString();
        subtitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dataSource);
        dest.writeString(subtitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
