package com.app.jayen.remindme;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * This class was created by Jayen on 22/02/14.
 */
public class AddReminderActivity extends Activity {

    EditText titleET;
    EditText descriptionET;
    EditText locationET;
    Button cancelButton;
    Button saveButton;
    ImageButton locationButton;
    private Geocoder geocoder;
    private List<Address> addresses;
    private String locality;
    private String country;
    LatLng geoCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addreminderactivity);

        titleET = (EditText) findViewById(R.id.titleET);
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        locationET = (EditText) findViewById(R.id.locationET);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        saveButton = (Button) findViewById(R.id.save);
        locationButton = (ImageButton) findViewById(R.id.locationIB);

        Reminder reminderToLoad = null;
        try {
            reminderToLoad = (Reminder) getIntent().getExtras().get("reminder");
        }
        catch (NullPointerException e) {

        }
        if(reminderToLoad!=null) {
            titleET.setText(reminderToLoad.getTitle());
            descriptionET.setText(reminderToLoad.getDescription());
            locationET.setText(reminderToLoad.getLocation());
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

        final Reminder finalReminderToLoad = reminderToLoad;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.reminderDataSource.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(finalReminderToLoad==null) {
                    MainActivity.reminderDataSource.createReminder(titleET.getText().toString().trim(),
                            descriptionET.getText().toString().trim(),locationET.getText().toString().trim(), geoCoord.latitude, geoCoord.longitude);

                }
                else {
                    MainActivity.reminderDataSource.renameReminder(finalReminderToLoad, titleET.getText().toString().trim(),
                            descriptionET.getText().toString().trim(),locationET.getText().toString().trim(), geoCoord.latitude, geoCoord.longitude);
                }

                

                Intent goBack = new Intent(AddReminderActivity.this,MainActivity.class);
                goBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.reminderDataSource.close();
                startActivity(goBack);
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMap = new Intent(AddReminderActivity.this,MapsActivity.class);
                startActivityForResult(openMap,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK) {
            geoCoord = new LatLng((Double)data.getExtras().get("lat"),(Double)data.getExtras().get("lon"));
            geocoder = new Geocoder(this.getBaseContext(), Locale.getDefault());
            addresses = null;
            try {
                addresses = geocoder.getFromLocation(geoCoord.latitude,geoCoord.longitude,1);
                locality = addresses.get(0).getLocality();
                country = addresses.get(0).getCountryName();
            }
            catch(IndexOutOfBoundsException e) {
                Log.d("LOCATION ERROR", "IndexOutOfBound in address arraylist");
            }
            catch (IOException e) {
            }
            locationET.setText(addresses.get(0).getPostalCode());
        }
    }
}
