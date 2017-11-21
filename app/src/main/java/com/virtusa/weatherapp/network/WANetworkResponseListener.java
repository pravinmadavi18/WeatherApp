package com.virtusa.weatherapp.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.virtusa.weatherapp.utility.log.WALogger;

import org.json.JSONObject;

/**
 * This class is generic for all response of network request;
 * we could use this class to handle the generic things lik manage all the logs,progress dialog , generic error for all network request
 */

public class WANetworkResponseListener implements Response.Listener<JSONObject>, Response.ErrorListener {
    public static final String TAG = WANetworkResponseListener.class.getSimpleName();

    protected WANetworkResponseSuccessInterface mNetworkResponseSuccessInterface;
    protected WANetworkResponseFailureInterface mNetworkResponseFailureInterface;

    public WANetworkResponseListener(WANetworkResponseSuccessInterface networkResponseSuccessInterface) {
        this.mNetworkResponseSuccessInterface = networkResponseSuccessInterface;
    }

    public WANetworkResponseListener(WANetworkResponseFailureInterface networkResponseFailureInterface) {
        this.mNetworkResponseFailureInterface = networkResponseFailureInterface;
    }

    @Override
    public void onResponse(JSONObject response) {
        WALogger.i(TAG, response.toString());
        this.mNetworkResponseSuccessInterface.onSuccess(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        WALogger.i(TAG, error.getStackTrace().toString());
        this.mNetworkResponseFailureInterface.onFailure(error);
    }
}
