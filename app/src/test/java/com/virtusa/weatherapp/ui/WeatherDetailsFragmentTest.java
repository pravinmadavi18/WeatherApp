package com.virtusa.weatherapp.ui;

import android.view.View;
import android.widget.AutoCompleteTextView;

import com.squareup.otto.Subscribe;
import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.model.WeatherDataModel;
import com.virtusa.weatherapp.setup.WAUIBaseTest;
import com.virtusa.weatherapp.ui.activity.WeatherActivity;
import com.virtusa.weatherapp.ui.fragments.WAWeatherFragment;
import com.virtusa.weatherapp.utility.constants.WAConstants;
import com.virtusa.weatherapp.utility.utils.WASharedPreferencesUtility;
import com.virtusa.weatherapp.views.WARelativeLayout;
import com.virtusa.weatherapp.views.WATextView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 *
 */

public class WeatherDetailsFragmentTest extends WAUIBaseTest {
    private static final Integer CITY_ID = 1259229;
    private static final String CITY_NAME = "Pune";

    @Mock
    private WAWeatherFragment fragment;
    private AutoCompleteTextView citiesTextView;
    private WATextView dateTV, tempratureTV;
    private WARelativeLayout weatherInformationHeaderLayout, pressureLayout, humidityLayout, tempMinLayout, tempMaxLayout, seaLevelLayout, groundLevelLayout;

    @Before
    public void setUp() {
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        WASharedPreferencesUtility.setLastCitySearched(activity, CITY_ID, CITY_NAME);
        fragment = WAWeatherFragment.newInstance();
        startFragment(fragment, WeatherActivity.class);
        checkViewInitialization(fragment.getView());
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(fragment);
    }

    @Test
    public void testCityACVHasValues() {
        assertNotNull(citiesTextView.getAdapter());
    }

    @Test
    public void testCityACVShouldNotSelectsCityIfSharedPreferencesAreSet() {
        startFragment(fragment, WeatherActivity.class);
        assertTrue(citiesTextView.getText().toString().equalsIgnoreCase(CITY_NAME));
        assertTrue(!dateTV.getText().toString().isEmpty());
        assertTrue(!tempratureTV.getText().toString().isEmpty());
        assertTrue(weatherInformationHeaderLayout.getVisibility() == View.VISIBLE);
        assertTrue(pressureLayout.getVisibility() == View.VISIBLE);
        assertTrue(humidityLayout.getVisibility() == View.VISIBLE);
        assertTrue(tempMinLayout.getVisibility() == View.VISIBLE);
        assertTrue(tempMaxLayout.getVisibility() == View.VISIBLE);
        assertTrue(seaLevelLayout.getVisibility() == View.VISIBLE);
        assertTrue(groundLevelLayout.getVisibility() == View.VISIBLE);
    }

    @Test
    public void testCityACVSelectsCityIfSharedPreferencesAreNotSet_LayoutShouldNotBeInvisible() {
        WASharedPreferencesUtility.setLastCitySearched(activity, WAConstants.MINUS_ONE, null);
        fragment = WAWeatherFragment.newInstance();
        startFragment(fragment, WeatherActivity.class);
        checkViewInitialization(fragment.getView());
        assertTrue(!citiesTextView.getText().toString().equalsIgnoreCase(CITY_NAME));
        assertTrue(weatherInformationHeaderLayout.getVisibility() == View.GONE);
        assertTrue(pressureLayout.getVisibility() == View.GONE);
        assertTrue(humidityLayout.getVisibility() == View.GONE);
        assertTrue(tempMinLayout.getVisibility() == View.GONE);
        assertTrue(tempMaxLayout.getVisibility() == View.GONE);
        assertTrue(seaLevelLayout.getVisibility() == View.GONE);
        assertTrue(groundLevelLayout.getVisibility() == View.GONE);
    }

    @Test
    public void testBusReceivedEvent() {
        bus.post(new WeatherDataModel());
    }

    @Subscribe
    public void testOnDataReceived(WeatherDataModel event) {
        Assert.assertNotNull(event);
    }

    private void checkViewInitialization(View view) {
        citiesTextView = view.findViewById(R.id.weather_acv_cities);
        dateTV = view.findViewById(R.id.weather_tv_date);
        tempratureTV = view.findViewById(R.id.weather_tv_temprature);
        weatherInformationHeaderLayout = view.findViewById(R.id.rl_wa_weather_information);
        pressureLayout = view.findViewById(R.id.rl_wa_weather_details_pressure);
        humidityLayout = view.findViewById(R.id.rl_wa_weather_details_humidity);
        tempMinLayout = view.findViewById(R.id.rl_wa_weather_details_temp_min);
        tempMaxLayout = view.findViewById(R.id.rl_wa_weather_details_temp_max);
        seaLevelLayout = view.findViewById(R.id.rl_wa_weather_details_sea_level);
        groundLevelLayout = view.findViewById(R.id.rl_wa_weather_details_ground_level);
    }
}
