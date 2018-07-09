package com.ab.searchgooglemapsapi.retrofit_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GeometryModel implements Parcelable{
    @SerializedName("location")
    LocationModel location;

    public GeometryModel(LocationModel location) {
        this.location = location;
    }

    protected GeometryModel(Parcel in) {
        location = in.readParcelable(LocationModel.class.getClassLoader());
    }

    public static final Creator<GeometryModel> CREATOR = new Creator<GeometryModel>() {
        @Override
        public GeometryModel createFromParcel(Parcel in) {
            return new GeometryModel(in);
        }

        @Override
        public GeometryModel[] newArray(int size) {
            return new GeometryModel[size];
        }
    };

    public LocationModel getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(location, i);
    }
}
