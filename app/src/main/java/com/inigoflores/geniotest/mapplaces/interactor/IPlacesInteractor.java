package com.inigoflores.geniotest.mapplaces.interactor;

/**
 * Created by inigo on 16/11/17.
 */

public interface IPlacesInteractor {
    void getPlacePredictions(String input, String location, String radius, String language, String key);
    void getPlaceDetail(String placeId, String key);
}