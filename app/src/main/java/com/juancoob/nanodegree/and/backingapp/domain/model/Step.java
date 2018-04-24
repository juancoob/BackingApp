package com.juancoob.nanodegree.and.backingapp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan Antonio Cobos Obrero on 18/04/18.
 */
public class Step implements Parcelable {

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @SerializedName("id")
    private Integer mStepId;

    @SerializedName("shortDescription")
    private String mShortDescription;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("videoURL")
    private String mVideoURL;

    @SerializedName("thumbnailURL")
    private String mThumbnailURL;

    protected Step(Parcel in) {
        if (in.readByte() == 0) {
            mStepId = null;
        } else {
            mStepId = in.readInt();
        }
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoURL = in.readString();
        mThumbnailURL = in.readString();
    }

    public Integer getStepId() {
        return mStepId;
    }

    public void setStepId(Integer mStepId) {
        this.mStepId = mStepId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(String mVideoURL) {
        this.mVideoURL = mVideoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String mThumbnailURL) {
        this.mThumbnailURL = mThumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (mStepId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mStepId);
        }
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoURL);
        parcel.writeString(mThumbnailURL);
    }
}
