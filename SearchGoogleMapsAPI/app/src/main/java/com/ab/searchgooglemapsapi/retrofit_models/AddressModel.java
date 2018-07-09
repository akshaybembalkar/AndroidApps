package com.ab.searchgooglemapsapi.retrofit_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AddressModel implements Parcelable {
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("formatted_address")
    private String formatted_address;
    @SerializedName("geometry")
    private GeometryModel geometry;

    public AddressModel(String place_id, String formatted_address, GeometryModel geometry) {
        this.geometry = geometry;
        this.place_id = place_id;
        this.formatted_address = formatted_address;
    }

    protected AddressModel(Parcel in) {
        geometry = in.readParcelable(GeometryModel.class.getClassLoader());
        place_id = in.readString();
        formatted_address = in.readString();
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    public String getPlaceId() {
        return place_id;
    }

    public String getFormattedAddress() {
        return formatted_address;
    }

    public GeometryModel getGeometry() {
        return geometry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(geometry, i);
        parcel.writeString(place_id);
        parcel.writeString(formatted_address);
    }
}
