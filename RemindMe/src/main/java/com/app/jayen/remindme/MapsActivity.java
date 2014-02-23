package com.app.jayen.remindme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This class was created by Jayen on 23/02/14.
 */
public class MapsActivity extends Activity implements LocationListener{

    LatLng markerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        // Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, (LocationListener) this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double latitude = 0,longitude = 0;
        if(location!=null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        markerLocation = new LatLng(latitude,longitude);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 13));
        map.addMarker(new MarkerOptions().title("move marker to the location you wish to be reminded").position(markerLocation).draggable(true));

        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                markerLocation = marker.getPosition();
            }
        });

        Button confirmButton = (Button) findViewById(R.id.confirmLocationButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("lat",markerLocation.latitude);
                resultIntent.putExtra("lon",markerLocation.longitude);
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
            }
        });
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
