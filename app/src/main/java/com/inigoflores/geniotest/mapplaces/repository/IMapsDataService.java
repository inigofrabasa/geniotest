package com.inigoflores.geniotest.mapplaces.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by inigo on 16/11/17.
 */

public interface IMapsDataService {
    @GET("json")
    Call<PlacePredictions> getPlacePredictions
            (@Query("input") String input,
             @Query("location") String location,
             @Query("radius") String radius,
             @Query("language") String language,
             @Query("key") String key);

    @GET("json")
    Call<PlaceDetails> getPlaceDetails
            (@Query("placeid") String placeid,
             @Query("key") String key);
}
