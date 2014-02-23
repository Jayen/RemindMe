package com.app.jayen.remindme;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.List;

/**
 * This class was created by Jayen on 22/02/14.
 */
public class DatabaseActivity extends ListActivity {
    private ReminderDataSource reminderDataSource;

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

    public void onClick(View view) {
        ArrayAdapter<Reminder> adapter = (ArrayAdapter<Reminder>) getListAdapter();
        Reminder reminder = new Reminder();
        reminder.setTitle("My Title");
        reminder.setDescription("My Description");

        switch(view.getId()) {
            case R.id.action_add:
                String[] reminders = new String[] {reminder.getTitle()};
                reminder = reminderDataSource.createReminder(reminder.getTitle(), reminder.getDescription(),
                        reminder.getLocation(), reminder.getLat(), reminder.getLng());
                adapter.add(reminder);
                break;
        }
        adapter.notifyDataSetChanged();
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
}
