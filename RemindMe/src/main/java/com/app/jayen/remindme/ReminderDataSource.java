package com.app.jayen.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class was created by Jayen on 22/02/14.
 */
public class ReminderDataSource {
    private SQLiteDatabase db;
    private ReminderSQLiteHelper dbHelper;
    private String[] allColumns = {ReminderSQLiteHelper.COLUMN_ID, ReminderSQLiteHelper.COLUMN_TITLE, ReminderSQLiteHelper.COLUMN_DESCRIPTION, ReminderSQLiteHelper.COLUMN_LOCATION};

    public ReminderDataSource(Context context) {
        dbHelper = new ReminderSQLiteHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Reminder createReminder(String title, String description, String location) {
        ContentValues values = new ContentValues();

        values.put(ReminderSQLiteHelper.COLUMN_TITLE, title);
        values.put(ReminderSQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(ReminderSQLiteHelper.COLUMN_LOCATION, location);
        long insertID = db.insert(ReminderSQLiteHelper.TABLE_REMINDER, null, values);
        Cursor cursor = db.query(ReminderSQLiteHelper.TABLE_REMINDER, allColumns, ReminderSQLiteHelper.COLUMN_ID + " = " + insertID, null, null, null, null);
        cursor.moveToFirst();
        Reminder newReminder = cursorToReminder(cursor);
        cursor.close();
        return newReminder;
    }

    public void renameReminder(Reminder reminder, String newTitle, String newDescription, String newLocation) {
        deleteReminder(reminder);
        createReminder(newTitle, newDescription, newLocation);
    }

    public void deleteReminder(Reminder reminder) {
        long id = reminder.getID();
        System.out.println("Comment deleted with id: " + id);
        db.delete(ReminderSQLiteHelper.TABLE_REMINDER, ReminderSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    private Reminder cursorToReminder(Cursor cursor) {
        Reminder reminder = new Reminder();
        reminder.setID(cursor.getLong(0));
        reminder.setTitle(cursor.getString(1));
        reminder.setDescription(cursor.getString(2));
        reminder.setLocation(cursor.getString(3));
        return reminder;
    }

    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<Reminder>();

        Cursor cursor = db.query(ReminderSQLiteHelper.TABLE_REMINDER, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Reminder reminder = cursorToReminder(cursor);
            reminders.add(reminder);
            cursor.moveToNext();
        }
        cursor.close();
        return reminders;
    }

}