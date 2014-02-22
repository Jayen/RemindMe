package com.app.jayen.remindme;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends ListActivity {

    private ReminderDataSource reminderDataSource;
    public static ArrayAdapter<Reminder> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reminderDataSource = new ReminderDataSource(this);
        try {
            reminderDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Reminder> values = reminderDataSource.getAllReminders();

        ArrayAdapter<Reminder> adapter = new ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    protected void onResume() {
        try {
            reminderDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    protected void onPause() {
        reminderDataSource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent addReminderIntent = new Intent(this,AddReminderActivity.class);
            startActivity(addReminderIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addReminder(MenuItem item) {
        adapter = (ArrayAdapter<Reminder>) getListAdapter();

        switch(item.getItemId()) {
            case R.id.action_add:
                Intent addReminderIntent = new Intent(this,AddReminderActivity.class);
                startActivity(addReminderIntent);
                break;
            //TODO delete functionality 
        }
        adapter.notifyDataSetChanged();
    }
}
