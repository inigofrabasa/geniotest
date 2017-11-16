package com.inigoflores.geniotest.mapplaces.presenter;

import com.inigoflores.geniotest.mapplaces.interactor.IPlacesInteractor;
import com.inigoflores.geniotest.mapplaces.interactor.PlacesInteractor;
import com.inigoflores.geniotest.mapplaces.repository.PlaceDetails;
import com.inigoflores.geniotest.mapplaces.repository.PlacePredictions;
import com.inigoflores.geniotest.mapplaces.view.IPlaceView;

/**
 * Created by inigo on 16/11/17.
 */

public class PlacesPresenter implements IPlacesPresenter {

    private IPlaceView placesView;
    private IPlacesInteractor placesInteractor;

    public PlacesPresenter(IPlaceView placesView) {
        this.placesView = placesView;
        placesInteractor = new PlacesInteractor(this);
    }

    @Override
    public void getPlacePredictions(String input, String location, String radius, String language, String key) {
        placesInteractor.getPlacePredictions(input, location, radius, language, key);
    }

    @Override
    public void setPlacePredictions(PlacePredictions placePredictions) {
        placesView.setPlacePredictions(placePredictions);
    }

    @Override
    public void getPlaceDetail(String placeId, String key) {
        placesInteractor.getPlaceDetail(placeId, key);
    }

    @Override
    public void setPlaceDetails(PlaceDetails placeDetails) {
        placesView.setPlaceDetail(placeDetails);
    }
}
