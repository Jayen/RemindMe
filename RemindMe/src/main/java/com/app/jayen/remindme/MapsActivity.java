package com.app.jayen.remindme;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.MapView;

/**
 * This class was created by Jayen on 23/02/14.
 */
public class MapsActivity extends Activity{

    private MapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
    }
}
