package com.virtusa.weatherapp.setup.utility;

import com.virtusa.weatherapp.model.WeatherDataModel;
import com.virtusa.weatherapp.setup.WAUIBaseTest;
import com.virtusa.weatherapp.utility.constants.WAConstants;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

/**
 *
 */

public class WADataManagerTest extends WAUIBaseTest {
    private static final Integer CITY_ID = 1259229;
    private static final String  CITY_NAME = "Birim";

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(dataManager);

    }

    @Test
    public void shouldBeSingleton() {
        assertSame(dataManager.getInstance(), dataManager);
    }

    @Test
    public void shouldGetCitiesStringArrayList() {
        dataManager.parseCityData(activity);
        assertNotNull(dataManager.getCitiesList());
    }

   /* @Test
    public void shouldReturnCityIDForCorrectName() {
        Integer cityId = dataManager.getCityIdByName(PUNE_CITY_NAME);
        assertNotNull(cityId);
        assertTrue(cityId.equals(PUNE_CITY_ID));
    }
*/
    @Test
    public void shouldNotReturnCityIDForInCorrectName() {
        dataManager.parseCityData(activity);
        Integer cityId = dataManager.getCityIdByName("1");
        assertNotNull(cityId);
        assertTrue(cityId.equals(WAConstants.MINUS_ONE));
    }

    @Test
    public void shouldParseCityWeatherDataOnCallParseCityWeatherData() {
        JSONObject jsonObject = new JSONObject();
        WeatherDataModel dataModel = dataManager.parseCityWeatherData(jsonObject);
        assertTrue(dataModel.getName().equalsIgnoreCase(CITY_NAME));
    }

}
