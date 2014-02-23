package com.app.jayen.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.sql.SQLException;

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
                            descriptionET.getText().toString().trim());

                }
                else {

                    MainActivity.reminderDataSource.renameReminder(finalReminderToLoad, titleET.getText().toString().trim(),
                            descriptionET.getText().toString().trim());
                    System.out.println("renamed completed");
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
}
