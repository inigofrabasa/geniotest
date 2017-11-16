package com.inigoflores.geniotest.mapplaces.interactor;

import com.inigoflores.geniotest.application.GenioApplication;
import com.inigoflores.geniotest.mapplaces.presenter.IPlacesPresenter;
import com.inigoflores.geniotest.mapplaces.repository.IMapsDataService;
import com.inigoflores.geniotest.mapplaces.repository.PlaceDetails;
import com.inigoflores.geniotest.mapplaces.repository.PlacePredictions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by inigo on 16/11/17.
 */

public class PlacesInteractor implements IPlacesInteractor {

    IPlacesPresenter placePresenter;

    public PlacesInteractor(IPlacesPresenter placePresenter) {
        this.placePresenter = placePresenter;
    }

    @Override
    public void getPlacePredictions(String input, String location, String radius, String language, String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMapsDataService service =  retrofit.create(IMapsDataService.class);

        Call<PlacePredictions> placePredictionsCall = service.getPlacePredictions(input, location, radius, language, key);
        if(placePredictionsCall != null){
            placePredictionsCall.enqueue(new Callback<PlacePredictions>() {
                @Override
                public void onResponse(Call<PlacePredictions> call, Response<PlacePredictions> response) {
                    if(response.body() != null){
                        GenioApplication.placePredictions = response.body();
                        placePresenter.setPlacePredictions(response.body());
                    }
                }

                @Override
                public void onFailure(Call<PlacePredictions> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void getPlaceDetail(String placeId, String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/details/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMapsDataService service =  retrofit.create(IMapsDataService.class);

        Call<PlaceDetails> placeDetailsCall = service.getPlaceDetails(placeId, key);
        if(placeDetailsCall != null){
            placeDetailsCall.enqueue(new Callback<PlaceDetails>() {
                @Override
                public void onResponse(Call<PlaceDetails> call, Response<PlaceDetails> response) {
                    if(response.body() != null){
                        GenioApplication.placeDetails = response.body();
                        placePresenter.setPlaceDetails(response.body());
                    }
                }

                @Override
                public void onFailure(Call<PlaceDetails> call, Throwable t) {

                }
            });
        }
    }
}
