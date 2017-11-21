package com.virtusa.weatherapp.utility.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.virtusa.weatherapp.utility.constants.WAConstants;

import static android.content.Context.MODE_PRIVATE;

/**
 * This class handles the shared preferences
 */

public class WASharedPreferencesUtility {

    public static void setLastCitySearched(Context context, int cityID,String cityName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(WAConstants.KEY_WA_SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putInt(WAConstants.KEY_LAST_CITY_SEARCHED_ID, cityID);
        editor.putString(WAConstants.KEY_LAST_CITY_SEARCHED_NAME, cityName);
        editor.commit();
    }

    public static String getLastCitySearchedName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(WAConstants.KEY_WA_SHARED_PREFERENCES, MODE_PRIVATE);
        return prefs.getString(WAConstants.KEY_LAST_CITY_SEARCHED_NAME,null);
    }
    public static Integer getLastCitySearchedID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(WAConstants.KEY_WA_SHARED_PREFERENCES, MODE_PRIVATE);
        return prefs.getInt(WAConstants.KEY_LAST_CITY_SEARCHED_ID,WAConstants.MINUS_ONE);
    }
    public static void setCitiesListString(Context context, String citiesList) {
        SharedPreferences.Editor editor = context.getSharedPreferences(WAConstants.KEY_WA_SHARED_PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(WAConstants.KEY_CITIES_LIST, citiesList);
        editor.commit();
    }
    public static String getCitiesListString(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(WAConstants.KEY_WA_SHARED_PREFERENCES, MODE_PRIVATE);
        return prefs.getString(WAConstants.KEY_CITIES_LIST,null);
    }
}
