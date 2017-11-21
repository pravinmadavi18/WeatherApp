package com.virtusa.weatherapp.utility.constants;

import android.content.Context;

import com.virtusa.weatherapp.R;

/**
 * Constant class
 */

public class WAConstants {
    public static String BUILD_TYPE_DEBUG = "debug";
    public static String BUILD_TYPE_RELEASE = "release";
    public static String BUILD_TYPE_PROD = "PROD";
    public static String BUILD_TYPE_QA = "QA";
    public static String BUILD_TYPE_UAT = "UAT";
    public static String BUILD_TYPE_DEV = "DEV";
    public static int THRESHOLD_VALUE_1 = 1;
    public static int ZERO = 0;
    public static int MINUS_ONE = -1;
    public static int ONE = 1;
    public static String UTF_8 = "UTF-8";
    public static String KEY_WA_SHARED_PREFERENCES = "KEY_WA_SHARED_PREFERENCES";
    public static String KEY_LAST_CITY_SEARCHED_ID = "KEY_LAST_CITY_SEARCHED_ID";
    public static String KEY_LAST_CITY_SEARCHED_NAME = "KEY_LAST_CITY_SEARCHED_NAME";
    public static String KEY_CITIES_LIST = "KEY_CITIES_LIST";
    public static String KEY_ID = "id";
    public static String EQUAL = "=";
    public static String KEY_APP_ID = "&appid=";
    public static String IMAGE_FORMAT_PNG = ".png";

    public static String getApiKey(Context context) {
        return context.getString(R.string.wa_api_key);
    }

    public static String getBaseUrl(Context context) {
        return context.getString(R.string.wa_base_url);
    }

    public static String getBaseIconUrl(Context context) {
        return context.getString(R.string.wa_base_icon_url);
    }
}
