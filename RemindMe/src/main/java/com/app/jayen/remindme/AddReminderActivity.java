package com.app.jayen.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class was created by Jayen on 22/02/14.
 */
public class AddReminderActivity extends Activity {

    EditText titleET;
    EditText descriptionET;
    EditText locationET;
    Button cancelButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addreminderactivity);

        titleET = (EditText) findViewById(R.id.titleET);
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        locationET = (EditText) findViewById(R.id.locationET);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        saveButton = (Button) findViewById(R.id.save);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(AddReminderActivity.this,MainActivity.class);
                startActivity(goBack);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reminder reminder = new Reminder();
                reminder.setTitle(titleET.getText().toString().trim());
                reminder.setDescription(descriptionET.getText().toString().trim());

            }
        });



    }
}
