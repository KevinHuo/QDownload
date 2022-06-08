package com.qiniu.qdownload.core.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.qiniu.qdownload.core.util.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Task.db";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TaskEntry.TABLE_NAME + " (" +
                    TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    TaskEntry.COLUMN_NAME_HASH + " TEXT," +
                    TaskEntry.COLUMN_NAME_FSIZE + " INTEGER," +
                    TaskEntry.COLUMN_NAME_PRIORITY + " INTEGER," +
                    TaskEntry.COLUMN_NAME_WIFI + " BOOLEAN," +
                    TaskEntry.COLUMN_NAME_JOBS + " INTEGER," +
                    TaskEntry.COLUMN_NAME_URL + " TEXT," +
                    TaskEntry.COLUMN_NAME_ETAG + " TEXT," +
                    TaskEntry.COLUMN_NAME_UDT + " INTEGER," +
                    TaskEntry.COLUMN_NAME_PROGRESS + " INTEGER," +
                    TaskEntry.COLUMN_NAME_BITS + " INTEGER," +
                    TaskEntry.COLUMN_NAME_NAB + " TEXT)";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public void insert(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);
        Logger.DEFAULT.i(TAG, " insert : " + values.toString() + ", newRowId : " + newRowId);
    }

}
