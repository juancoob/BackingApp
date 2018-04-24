package com.juancoob.nanodegree.and.backingapp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 18/04/18.
 */
public class Recipe implements Parcelable {

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @SerializedName("id")
    private Integer mRecipeId;

    @SerializedName("name")
    private String mRecipeName;

    @SerializedName("ingredients")
    private List<Ingredient> mIngredients;

    @SerializedName("steps")
    private List<Step> mSteps;

    @SerializedName("servings")
    private int mServings;

    @SerializedName("image")
    private String mImagePath;

    protected Recipe(Parcel in) {
        if (in.readByte() == 0) {
            mRecipeId = null;
        } else {
            mRecipeId = in.readInt();
        }
        mRecipeName = in.readString();
        mServings = in.readInt();
        mImagePath = in.readString();
    }

    public Integer getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(Integer mRecipeId) {
        this.mRecipeId = mRecipeId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String mRecipeName) {
        this.mRecipeName = mRecipeName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int mServings) {
        this.mServings = mServings;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (mRecipeId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mRecipeId);
        }
        parcel.writeString(mRecipeName);
        parcel.writeInt(mServings);
        parcel.writeString(mImagePath);
    }
}
