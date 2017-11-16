package com.inigoflores.geniotest.mapplaces.view;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inigoflores.geniotest.R;
import com.inigoflores.geniotest.application.GenioApplication;
import com.inigoflores.geniotest.mapplaces.adapter.AutoCompleteAdapter;
import com.inigoflores.geniotest.mapplaces.presenter.IPlacesPresenter;
import com.inigoflores.geniotest.mapplaces.presenter.PlacesPresenter;
import com.inigoflores.geniotest.mapplaces.repository.PlaceAutoComplete;
import com.inigoflores.geniotest.mapplaces.repository.PlaceDetails;
import com.inigoflores.geniotest.mapplaces.repository.PlacePredictions;

public class PlaceActivity extends AppCompatActivity implements
        IPlaceView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback {

    private View placesView;
    private ListView mAutoCompleteList;
    private EditText Address;
    private ImageView searchBtn;
    private SupportMapFragment mapFragment;

    private AutoCompleteAdapter mAutoCompleteAdapter;

    private int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 20;
    private static final int MY_PERMISSIONS_REQUEST_LOC = 30;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private IPlacesPresenter placePresenter;

    private Location mLastLocation;
    private double latitude;
    private double longitude;

    private Marker marker;

    private final String key = "AIzaSyCti-tS8PKHtyeDtAlcJUjPnhptbfuJ_p0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_predictions);

        placesView          = findViewById(R.id.rl_activity_places_view);
        Address             = (EditText) findViewById(R.id.adressText);
        mAutoCompleteList   = (ListView) findViewById(R.id.lv_searchResult);
        searchBtn           = (ImageView) findViewById(R.id.search);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        placePresenter = new PlacesPresenter(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fetchLocation();
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOC);
            } else {
                fetchLocation();
            }
        }

        Address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Address.getText().length() > 0) {
                    mAutoCompleteList.setVisibility(View.VISIBLE);
                    searchBtn.setVisibility(View.GONE);

                    placePresenter.getPlacePredictions(
                            Address.getText().toString(),
                            latitude + "," + longitude,
                            "500",
                            "en",
                            key
                    );
                }
                else{
                    mAutoCompleteList.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        Address.setSelection(Address.getText().length());

        mAutoCompleteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAutoCompleteList.setVisibility(View.GONE);

                PlaceAutoComplete placeAutoComplete = GenioApplication.placePredictions.getPlaces().get(position);
                placePresenter.getPlaceDetail(placeAutoComplete.getPlaceID(), key);
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();

                mapFragment.getMapAsync(this);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void fetchLocation(){
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOC: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();

                } else {
                    Toast.makeText(this, "Please grant permission for using this app!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void setPlacePredictions(PlacePredictions placePredictions) {
        searchBtn.setVisibility(View.VISIBLE);

        if (mAutoCompleteAdapter == null) {
            mAutoCompleteAdapter = new AutoCompleteAdapter(this, placePredictions.getPlaces(), PlaceActivity.this);
            mAutoCompleteList.setAdapter(mAutoCompleteAdapter);
        } else {
            mAutoCompleteAdapter.clear();
            mAutoCompleteAdapter.addAll(placePredictions.getPlaces());
            mAutoCompleteAdapter.notifyDataSetChanged();
            mAutoCompleteList.invalidate();
        }
    }

    @Override
    public void setPlaceDetail(PlaceDetails placeDetail) {

        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(placesView.getWindowToken(), 0);

        LatLng myLocation = new LatLng(
                placeDetail.getResult().getGeometry().getLocation().getLat(),
                placeDetail.getResult().getGeometry().getLocation().getLng());

        marker.remove();
        marker = mMap.addMarker(
                new MarkerOptions().position(myLocation).title(placeDetail.getResult().getAddressComponents().get(0).getLongName()));

        CameraPosition camera = new CameraPosition.Builder()
                .target(myLocation)
                .zoom(18)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng myLocation = new LatLng(latitude, longitude);
        marker = mMap.addMarker(new MarkerOptions().position(myLocation).title("I am here"));

        CameraPosition camera = new CameraPosition.Builder()
                .target(myLocation)
                .zoom(17)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
    }
}
