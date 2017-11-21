package com.virtusa.weatherapp.utility.log;

import android.support.compat.BuildConfig;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;
import com.virtusa.weatherapp.utility.constants.WAConstants;

/**
 * This class acts as a singleton Logger in WA App ; It logs based on the flag LOG
 */

public class WALogger {
    private static boolean LOG = false;
    private static WALogger sInstance;

    private WALogger() {

    }

    public static WALogger getInstance() {
        if (sInstance == null) {
            sInstance = new WALogger();
        }
        return sInstance;
    }

    public static void setLog(boolean isEnabled) {
        if (isEnabled) {
            LOG = isEnabled;
        }
    }

    public static void i(String tag, String msg) {
        if (LOG && BuildConfig.BUILD_TYPE.equalsIgnoreCase(WAConstants.BUILD_TYPE_RELEASE) ||
                BuildConfig.BUILD_TYPE.equalsIgnoreCase(WAConstants.BUILD_TYPE_PROD)) {
            Log.i(tag, msg);
            FirebaseCrash.log(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG && BuildConfig.BUILD_TYPE.equalsIgnoreCase(WAConstants.BUILD_TYPE_DEBUG)
                || BuildConfig.BUILD_TYPE.equalsIgnoreCase(WAConstants.BUILD_TYPE_DEV)) {
            Log.d(tag, msg);
            FirebaseCrash.log(msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG && BuildConfig.BUILD_TYPE.equalsIgnoreCase(WAConstants.BUILD_TYPE_QA)
                || BuildConfig.BUILD_TYPE.equalsIgnoreCase(WAConstants.BUILD_TYPE_UAT)) {
            Log.e(tag, msg);
            FirebaseCrash.log(msg);
        }
    }

    public static void log(String tag, String msg) {
        if (LOG) {
            Log.d(tag, msg);
            Log.i(tag, msg);
            Log.e(tag, msg);
            FirebaseCrash.log(msg);
        }
    }
}


