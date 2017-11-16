package com.inigoflores.geniotest.mapplaces.repository;

import java.util.ArrayList;

/**
 * Created by inigo on 16/11/17.
 */

public class PlacePredictions {

    public ArrayList<PlaceAutoComplete> getPlaces() {
        return predictions;
    }

    public void setPlaces(ArrayList<PlaceAutoComplete> places) {
        this.predictions = places;
    }

    private ArrayList<PlaceAutoComplete> predictions;
}
