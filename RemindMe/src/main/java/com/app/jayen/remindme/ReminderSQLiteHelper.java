package com.app.jayen.remindme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class was created by Jayen on 22/02/14.
 */
public class ReminderSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_REMINDER = "reminders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LOCATION = "location";

    private static final String DATABASE_NAME = "reminders.db";
    private static final int DATABASE_VERSION = 1;

    //creating the database statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_REMINDER + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TITLE + " text not null, "
            + COLUMN_DESCRIPTION + " text not null, " + COLUMN_LOCATION + " text);";

    public ReminderSQLiteHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ReminderSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        onCreate(db);
    }
}
