package com.virtusa.weatherapp.utility.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.model.CitiesModel;
import com.virtusa.weatherapp.model.Le;
import com.virtusa.weatherapp.model.WeatherDataModel;
import com.virtusa.weatherapp.ui.fragments.WAWeatherFragment;
import com.virtusa.weatherapp.utility.constants.WAConstants;
import com.virtusa.weatherapp.utility.log.WALogger;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class handles all the data across the app
 */

public class WADataManager {
    public static final String TAG = WAWeatherFragment.class.getSimpleName();
    private static WADataManager instance = null;
    private static List<Le> citiesModelLeList;
    private static ArrayList<String> citiesList = new ArrayList<String>();

    public static WADataManager getInstance() {
        if (instance == null) {
            instance = new WADataManager();
        }
        return instance;
    }

    public void parseCityData(final Context context) {
        try {
            Gson gson = new Gson();
            String cityJSON = getCitiesDataFromJsonFile(context);
            CitiesModel citiesModel = gson.fromJson(cityJSON, CitiesModel.class);
            citiesModelLeList = citiesModel.getLe();
        } catch (Exception e) {
            WALogger.i(TAG, e.getStackTrace().toString());
        }
    }

    public ArrayList<String> getCitiesList() {
        if (citiesList.size() == WAConstants.ZERO && citiesModelLeList != null) {
            Iterator<Le> iterator = citiesModelLeList.iterator();
            while (iterator.hasNext()) {
                citiesList.add(iterator.next().getName());
            }
        }
        return citiesList;
    }

    public int getCityIdByName(String cityName) {
        int cityId = WAConstants.MINUS_ONE;
        if (citiesModelLeList != null) {
            Iterator<Le> iterator = citiesModelLeList.iterator();
            while (iterator.hasNext()) {
                Le leModel = iterator.next();
                if (leModel.getName().equalsIgnoreCase(cityName)) {
                    cityId = leModel.getId();
                    break;
                }
            }
        }
        return cityId;
    }

    public WeatherDataModel parseCityWeatherData(JSONObject response) {
        Gson gson = new Gson();
        WeatherDataModel dataModel = null;
        try {
            dataModel = gson.fromJson(String.valueOf(response), WeatherDataModel.class);
        } catch (Exception e) {
            WALogger.i(TAG, e.getStackTrace().toString());
        }
        return dataModel;
    }

    private String getCitiesDataFromJsonFile(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(context.getString(R.string.wa_cities_json_file_name));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, WAConstants.UTF_8);
        } catch (IOException ex) {
            WALogger.i(TAG, ex.getStackTrace().toString());
            return null;
        }
        return json;
    }

    public static List<Le> getCitiesModelLeList() {
        return citiesModelLeList;
    }
    public static void setCitiesModelLeList(List<Le> list) {
         citiesModelLeList=list;
    }
    public static void setInstance(WADataManager dataManger) {
        instance = dataManger;
    }
}
