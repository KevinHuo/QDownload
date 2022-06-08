package com.qiniu.qdownload.core.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    private static final String SQL_QUERY_HASH_PREFIX =
            "SELECT  * FROM " + TaskEntry.TABLE_NAME + " WHERE " +
                    TaskEntry.COLUMN_NAME_HASH + " = ";

    private static final String SQL_UPDATE_WHERE_PREFIX =
            TaskEntry.COLUMN_NAME_HASH + " = ";

    private static final String SQL_DELETE_WHERE_PREFIX =
            TaskEntry.COLUMN_NAME_HASH + " = ";

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

        Cursor cursor = query((String) values.get(TaskEntry.COLUMN_NAME_HASH));
        if (cursor.getCount() < 1) {
            long newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);
            Logger.DEFAULT.i(TAG, "insert : " + values.toString() + ", newRowId : " + newRowId);
        } else {
            Logger.DEFAULT.e(TAG, "insert failed, table has same file!");
        }
        cursor.close();
    }

    public Cursor query(String hash) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_QUERY_HASH_PREFIX + hash, null);
        Logger.DEFAULT.i(TAG, "query cursor count : " + cursor.getCount());
        return cursor;
    }

    public void update(String hash, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TaskEntry.TABLE_NAME, values, SQL_UPDATE_WHERE_PREFIX + hash, null);
    }

    public void delete(String hash) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskEntry.TABLE_NAME, SQL_DELETE_WHERE_PREFIX + hash, null);
    }
}
