package com.inigoflores.geniotest.mapplaces.presenter;

import com.inigoflores.geniotest.mapplaces.repository.PlaceDetails;
import com.inigoflores.geniotest.mapplaces.repository.PlacePredictions;

/**
 * Created by inigo on 16/11/17.
 */

public interface IPlacesPresenter {
    void getPlacePredictions(String input, String location, String radius, String language, String key);
    void setPlacePredictions(PlacePredictions placePredictions);

    void getPlaceDetail(String placeId, String key);
    void setPlaceDetails(PlaceDetails placeDetails);
}
