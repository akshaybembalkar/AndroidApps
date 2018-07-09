package com.ab.searchgooglemapsapi.retrofit_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressList {
    @SerializedName("results")
    private List<AddressModel> addressList;

    public AddressList(List<AddressModel> results) {
        this.addressList = results;
    }

    public List<AddressModel> getAddressList() {
        return addressList;
    }
}
