package com.example.femmy.reloadmore;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Android System on 11/2/2017.
 */

public interface ApiInterface {

    @GET("CityList")
    Call<List<DataModel>> getJSONData(@Query("UserId") String UserId);
}
