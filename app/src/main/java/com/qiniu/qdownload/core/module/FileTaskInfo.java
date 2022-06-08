package com.qiniu.qdownload.core.module;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.qiniu.qdownload.core.database.TaskEntry;
import com.qiniu.qdownload.core.util.Logger;

import java.util.Arrays;

public class FileTaskInfo {
    private static final String TAG = "FileTaskInfo";

    public static final char TASK_UNDONE = '0';
    public static final char TASK_DONE = '1';
    public static final char TASK_RUNNING = '2';

    private String key;
    private long fileSize;
    private int priority;
    private int jobs = 5;
    private String url;
    private String eTag;
    private int updateTime;
    private long progress;
    private int bits = 18;
    private char[] nab;
    private boolean wifi;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getJobs() {
        return jobs;
    }

    public void setJobs(int jobs) {
        this.jobs = jobs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public int getBits() {
        return bits;
    }

    public char[] getNab() {
        return nab;
    }

    public void initNab(int blockNum) {
        nab = new char[blockNum];
        Arrays.fill(nab, TASK_UNDONE);
    }

    public void setNab(String nab) {
        this.nab = nab.toCharArray();
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public void setBits(int bits) {
        this.bits = Math.max(bits, 16);
    }

    public long getBlockSize() {
        return 1L << bits;
    }

    public String getNabStr() {
        return new String(nab);
    }

    public void updateNab(int index, char status) {
        if (index > nab.length) {
            Logger.DEFAULT.e(TAG, "updateNab failed, index is " + index + " bigger than nab's length!");
            return;
        }
        nab[index] = status;

        Logger.DEFAULT.i(TAG, "updateNab : " + new String(nab));
    }

    public int getNextNabIndex() {
        for (int i = 0; i < nab.length; i++) {
            if (nab[i] == '0') {
                return i;
            }
        }
        return -1;
    }

    @SuppressLint("Range")
    public static FileTaskInfo createFromDatabase(Cursor cursor) {
            FileTaskInfo info = new FileTaskInfo();
            if (cursor.moveToFirst()) {
                info.setKey(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_HASH)));
                info.setFileSize(cursor.getLong(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_FSIZE)));
                info.setPriority(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_PRIORITY)));
                info.setJobs(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_JOBS)));
                info.setUrl(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_URL)));
                info.seteTag(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_ETAG)));
                info.setUpdateTime(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_UDT)));
                info.setProgress(cursor.getLong(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_PROGRESS)));
                info.setBits(cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_BITS)));
                boolean wifi = cursor.getInt(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_BITS)) > 0;
                info.setWifi(wifi);
                info.setNab(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_NAME_NAB)));

                Logger.DEFAULT.i(TAG,"Info : " + info.toString());
                return info;
            }

        return null;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_HASH, this.key);
        values.put(TaskEntry.COLUMN_NAME_FSIZE, this.fileSize);
        values.put(TaskEntry.COLUMN_NAME_PRIORITY, this.priority);
        values.put(TaskEntry.COLUMN_NAME_WIFI, this.wifi);
        values.put(TaskEntry.COLUMN_NAME_JOBS, this.jobs);
        values.put(TaskEntry.COLUMN_NAME_URL, this.url);
        values.put(TaskEntry.COLUMN_NAME_ETAG, this.eTag);
        values.put(TaskEntry.COLUMN_NAME_UDT, this.updateTime);
        values.put(TaskEntry.COLUMN_NAME_PROGRESS, this.progress);
        values.put(TaskEntry.COLUMN_NAME_BITS, this.bits);
        values.put(TaskEntry.COLUMN_NAME_NAB, getNabStr());
        return values;
    }


    @Override
    public String toString() {
        return "FileTaskInfo{" +
                "key='" + key + '\'' +
                ", fileSize=" + fileSize +
                ", priority=" + priority +
                ", jobs=" + jobs +
                ", url='" + url + '\'' +
                ", eTag='" + eTag + '\'' +
                ", updateTime=" + updateTime +
                ", progress=" + progress +
                ", bits=" + bits +
                ", nab=" + Arrays.toString(nab) +
                ", wifi=" + wifi +
                '}';
    }
}
