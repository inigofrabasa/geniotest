package com.inigoflores.geniotest.application;

import android.app.Application;

import com.inigoflores.geniotest.mapplaces.repository.PlaceDetails;
import com.inigoflores.geniotest.mapplaces.repository.PlacePredictions;
import com.inigoflores.geniotest.messages.repository.Messages;

/**
 * Created by inigo on 13/11/17.
 */

public class GenioApplication extends Application {

    public static Messages messages;
    public static PlacePredictions placePredictions;
    public static PlaceDetails placeDetails;

    @Override
    public void onCreate(){
        super.onCreate();
    }
}
