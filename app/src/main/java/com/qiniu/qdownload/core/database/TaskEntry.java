package com.qiniu.qdownload.core.database;

import android.provider.BaseColumns;

public class TaskEntry implements BaseColumns {
    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_NAME_HASH = "hash";
    public static final String COLUMN_NAME_FSIZE = "fsize";
    public static final String COLUMN_NAME_PRIORITY = "priority";
    public static final String COLUMN_NAME_WIFI = "wifi";
    public static final String COLUMN_NAME_JOBS = "jobs";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_ETAG = "etag";
    public static final String COLUMN_NAME_UDT = "udt";
    public static final String COLUMN_NAME_PROGRESS = "progress";
    public static final String COLUMN_NAME_BITS = "bits";
    public static final String COLUMN_NAME_NAB = "nab";
}
