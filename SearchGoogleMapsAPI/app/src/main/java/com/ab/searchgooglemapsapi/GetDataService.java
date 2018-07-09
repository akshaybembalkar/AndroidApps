package com.ab.searchgooglemapsapi;

import com.ab.searchgooglemapsapi.retrofit_models.AddressList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("/maps/api/geocode/json?sensor=false")
    Call<AddressList> getLocationByAddress(@Query("address") String address);
}
