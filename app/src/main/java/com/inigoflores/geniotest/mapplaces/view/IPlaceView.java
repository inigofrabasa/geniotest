package com.inigoflores.geniotest.mapplaces.view;

import com.inigoflores.geniotest.mapplaces.repository.PlaceDetails;
import com.inigoflores.geniotest.mapplaces.repository.PlacePredictions;

/**
 * Created by inigo on 16/11/17.
 */

public interface IPlaceView {
    void setPlacePredictions(PlacePredictions placePredictions);
    void setPlaceDetail(PlaceDetails placeDetail);
}
