package com.virtusa.weatherapp.network;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.setup.WAUIBaseTest;
import com.virtusa.weatherapp.utility.constants.WAConstants;
import com.virtusa.weatherapp.utility.utils.WAUtility;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Holds all the test cases for network request
 */

public class WANetworkRequestTest extends WAUIBaseTest {
    private static final Integer PUNE_CITY_ID = 1259229;
    private static final String PUNE_CITY_NAME = "Pune";
    private static final String KEY_NAME = "name";

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(networkManager);

    }

    @Test
    public void shouldDownloadJsonFileFromServer() throws Exception {

        if (WAUtility.isNetworkAvailable(activity)) {
            final String url = activity.getString(R.string.wa_base_url) + WAConstants.KEY_ID + WAConstants.EQUAL + PUNE_CITY_ID + WAConstants.KEY_APP_ID + WAConstants.getApiKey(activity);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null, new WANetworkResponseListener(new WANetworkResponseSuccessInterface() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        assertNotNull(response);
                        assertTrue(response.has(KEY_NAME));
                        assertTrue(response.getString(KEY_NAME).equalsIgnoreCase(PUNE_CITY_NAME));
                    } catch (Exception e) {

                    }
                }
            }), new WANetworkResponseListener(new WANetworkResponseFailureInterface() {
                @Override
                public void onFailure(VolleyError error) {
                }
            }));

            WANetworkManager.getInstance(activity).addToRequestQueue(jsonObjReq);
        }
    }


    @Test
    public void shouldFailToDownloadJsonFileFromServer() throws Exception {

        if (WAUtility.isNetworkAvailable(activity)) {
            final String url = activity.getString(R.string.wa_base_url);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null, new WANetworkResponseListener(new WANetworkResponseSuccessInterface() {
                @Override
                public void onSuccess(JSONObject response) {

                }
            }), new WANetworkResponseListener(new WANetworkResponseFailureInterface() {
                @Override
                public void onFailure(VolleyError error) {
                    try {
                        assertNotNull(error);
                    } catch (Exception e) {

                    }
                }
            }));

            WANetworkManager.getInstance(activity).addToRequestQueue(jsonObjReq);
        }
    }
}
