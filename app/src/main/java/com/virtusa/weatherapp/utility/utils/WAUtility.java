package com.virtusa.weatherapp.utility.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */

public class WAUtility {

    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager manager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnected();
    }

    public static String getDateTime() {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        return sdf.format(d);
    }
}
