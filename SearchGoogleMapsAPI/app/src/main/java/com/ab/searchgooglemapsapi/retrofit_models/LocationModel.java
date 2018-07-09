package com.ab.searchgooglemapsapi.retrofit_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LocationModel implements Parcelable{
    @SerializedName("lat")
    double latitude;
    @SerializedName("lng")
    double longitude;

    public LocationModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected LocationModel(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}
