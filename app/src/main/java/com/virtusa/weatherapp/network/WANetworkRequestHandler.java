package com.virtusa.weatherapp.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.event.WABusProvider;
import com.virtusa.weatherapp.utility.constants.WAConstants;
import com.virtusa.weatherapp.utility.log.WALogger;
import com.virtusa.weatherapp.utility.utils.WADataManager;
import com.virtusa.weatherapp.utility.utils.WAUtility;

import org.json.JSONObject;

/**
 * This class handles the  all network request
 */

public class WANetworkRequestHandler {
    public static final String TAG = WANetworkRequestHandler.class.getSimpleName();

    /**
     * This method handles the request for json file AppConfigDataJsonFile
     *
     * @param context
     */
    public static void getWeatherDataByCityId(final Context context, Integer cityID) {
        if (WAUtility.isNetworkAvailable(context)) {
            final String url = context.getString(R.string.wa_base_url) + WAConstants.KEY_ID + WAConstants.EQUAL + cityID + WAConstants.KEY_APP_ID + WAConstants.getApiKey(context);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null, new WANetworkResponseListener(new WANetworkResponseSuccessInterface() {
                @Override
                public void onSuccess(JSONObject response) {
                    WALogger.i(TAG, response.toString());
                    WABusProvider.getInstance().post(WADataManager.getInstance().parseCityWeatherData(response));
                }
            }), new WANetworkResponseListener(new WANetworkResponseFailureInterface() {
                @Override
                public void onFailure(VolleyError error) {
                    WALogger.i(TAG, error.getMessage());
                }
            }));
            WANetworkManager.getInstance(context).addToRequestQueue(jsonObjReq);
        }
    }

}
