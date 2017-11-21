package com.virtusa.weatherapp.setup;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.otto.Bus;
import com.virtusa.weatherapp.BuildConfig;
import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.controller.WAFragmentController;
import com.virtusa.weatherapp.event.WABusProvider;
import com.virtusa.weatherapp.model.CitiesModel;
import com.virtusa.weatherapp.model.Le;
import com.virtusa.weatherapp.model.WeatherDataModel;
import com.virtusa.weatherapp.network.WANetworkManager;
import com.virtusa.weatherapp.ui.activity.WeatherActivity;
import com.virtusa.weatherapp.utility.log.WALogger;
import com.virtusa.weatherapp.utility.utils.WADataManager;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

/**
 * This is a base test class which setup the things to be used in overall application test classes
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M, packageName = "com.virtusa.weatherapp")
@RunWith(RobolectricGradleTestRunner.class)
public abstract class WAUIBaseTest {

    public static final String CITY_WEATHER_DATA_FILE_NAME = "test_weather_data.json";

    public WeatherActivity activity;
    @Spy
    public WADataManager dataManager;
    public List<Le> citiesModelLeList;
    @Mock
    public WANetworkManager networkManager;
    @Mock
    public WALogger logger;
    @Mock
    public FragmentManager fragmentManager;
    @Mock
    public Fragment fragmentContainer;
    @Mock
    public Bus bus;

    @Before
    public void setUp() {
        try {
            MockitoAnnotations.initMocks(this);
            activity = Robolectric.buildActivity(WeatherActivity.class)
                    .create()
                    .resume()
                    .get();
            WADataManager.setInstance(dataManager);
            doNothing().when(dataManager).parseCityData(isA(AppCompatActivity.class));
            doReturn(parseCityWeatherDataJsonObject(activity)).when(dataManager).parseCityWeatherData(isA(JSONObject.class));
            citiesModelLeList = parseCitiesModelJsonObject(activity);
            dataManager.setCitiesModelLeList(citiesModelLeList);
            bus = WABusProvider.getInstance();
            bus.register(this);
            networkManager = WANetworkManager.getInstance(activity);
            logger = WALogger.getInstance();
            fragmentManager = new WAFragmentController().getFragmentManager();
            fragmentContainer = activity.getSupportFragmentManager().findFragmentById(R.id.frame_layout_fragment_container);
        } catch (Exception e) {
            logger.log("WAUIBaseTest", e.getMessage());
        }

    }

    public String getJsonContentFromTestMockFile(Context context, String fileName) {
        String jsonContent = null;
        InputStream in = null;
        try {
            in = ClassLoader.getSystemResourceAsStream(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (in != null) {
            try {
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                jsonContent = new String(buffer, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonContent;
    }

    public List<Le> parseCitiesModelJsonObject(Context context) {
        List<Le> baseModel = null;
        try {
            Gson gson = new Gson();
            baseModel = gson.fromJson(getJsonContentFromTestMockFile(context, activity.getString(R.string.wa_cities_json_file_name)), CitiesModel.class).getLe();
        } catch (JsonParseException e) {
        }
        return baseModel;
    }

    public WeatherDataModel parseCityWeatherDataJsonObject(Context context) {
        WeatherDataModel baseModel = null;
        try {
            Gson gson = new Gson();
            baseModel = gson.fromJson(getJsonContentFromTestMockFile(context, CITY_WEATHER_DATA_FILE_NAME), WeatherDataModel.class);
        } catch (JsonParseException e) {
        }
        return baseModel;
    }
}
