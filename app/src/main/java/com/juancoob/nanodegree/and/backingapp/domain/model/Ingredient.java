package com.juancoob.nanodegree.and.backingapp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan Antonio Cobos Obrero on 18/04/18.
 */
public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    private float mQuantity;

    @SerializedName("measure")
    private String mMeasure;

    @SerializedName("ingredient")
    private String mIngredient;

    protected Ingredient(Parcel in) {
        mQuantity = in.readFloat();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public float getQuantity() {
        return mQuantity;
    }

    public void setQuantity(float mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String mIngredient) {
        this.mIngredient = mIngredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mIngredient);
    }
}
