package com.inigoflores.geniotest.mapplaces.repository;

/**
 * Created by inigo on 16/11/17.
 */

public class PlaceAutoComplete {
    private String place_id;
    private String description;

    public String getPlaceDesc() {
        return description;
    }

    public void setPlaceDesc(String placeDesc) {
        description = placeDesc;
    }

    public String getPlaceID() {
        return place_id;
    }

    public void setPlaceID(String placeID) {
        place_id = placeID;
    }

}
