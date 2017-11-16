package com.inigoflores.geniotest.messages.repository;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by inigo on 13/11/17.
 */

public interface IDataService {
    @GET("data")
    Call<Messages> getMessages();
}