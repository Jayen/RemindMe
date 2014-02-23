package com.app.jayen.remindme;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends ListActivity {

    ListView reminderListView;
    public static ReminderDataSource reminderDataSource;
    public static ArrayAdapter<Reminder> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reminderListView = getListView();
        reminderDataSource = new ReminderDataSource(this);
        try {
            reminderDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final List<Reminder> values = reminderDataSource.getAllReminders();

        final ArrayAdapter<Reminder> adapter = new ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewReminder = new Intent(MainActivity.this,AddReminderActivity.class);
                Reminder reminder = values.get(position);
                viewReminder.putExtra("reminder",reminder);
                startActivity(viewReminder);
            }
        });

        reminderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Take action");

                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayAdapter<Reminder> adapter = (ArrayAdapter)reminderListView.getAdapter();
                        reminderDataSource.deleteReminder(adapter.getItem(position));
                        adapter.notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;
            }
        });

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
//        int id = item.getItemId();
//        if (id == R.id.action_add) {
//            Intent addReminderIntent = new Intent(this,AddReminderActivity.class);
//            startActivity(addReminderIntent);
//            return true;
//        }
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
