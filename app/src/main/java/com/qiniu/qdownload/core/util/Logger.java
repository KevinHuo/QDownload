package com.qiniu.qdownload.core.util;

import android.util.Log;

public class Logger {

    private static String TAG = "NETR";

    private static int LOG_LEVEL = Log.INFO;

    private final String MODULE_TAG;

    public static final Logger DEFAULT = new Logger("");
    public static final Logger CONNECTION = new Logger("CONNECTION");
    public static final Logger DOWNLOAD = new Logger("DOWNLOAD");
    public static final Logger FILE = new Logger("FILE");
    public static final Logger UTIL = new Logger("UTIL");
    public static final Logger SAMPLE = new Logger("SAMPLE");

    private Logger(String moduleTag) {
        MODULE_TAG = moduleTag;
    }

    public static void setLogLevel(int level) {
        LOG_LEVEL = level;
    }

    public void v(String msg) {
        v(null, msg);
    }

    public void v(String tag, String msg) {
        if (LOG_LEVEL > Log.VERBOSE) {
            return;
        }
        Log.v(TAG, prefix(tag) + msg);
    }

    public void d(String msg) {
        d(null, msg);
    }

    public void d(String tag, String msg) {
        if (LOG_LEVEL > Log.DEBUG) {
            return;
        }
        Log.d(TAG, prefix(tag) + msg);
    }

    public void i(String msg) {
        i(null, msg);
    }

    public void i(String tag, String msg) {
        if (LOG_LEVEL > Log.INFO) {
            return;
        }
        Log.i(TAG, prefix(tag) + msg);
    }

    public void w(String msg) {
        w(null, msg);
    }

    public void w(String tag, String msg) {
        if (LOG_LEVEL > Log.WARN) {
            return;
        }
        Log.w(TAG, prefix(tag) + msg);
    }

    public void e(String msg) {
        e(null, msg);
    }

    public void e(String tag, String msg) {
        if (LOG_LEVEL > Log.ERROR) {
            return;
        }
        Log.e(TAG, prefix(tag) + msg);
    }

    private String prefix(String tag) {
        String prefix = "";
        if (MODULE_TAG != null && !"".equals(MODULE_TAG)) {
            prefix += MODULE_TAG + " : ";
        }
        if (tag != null && !"".equals(tag)) {
            prefix += tag + " : ";
        }
        return prefix;
    }
}
