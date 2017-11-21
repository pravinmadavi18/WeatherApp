package com.virtusa.weatherapp.network;

import org.json.JSONObject;

/**
 * Interface for On Success response network call so that all network call should use this.
 * will be useful for the common things to apply for all the network class like logging etc.
 */

public interface WANetworkResponseSuccessInterface {
    void onSuccess(JSONObject response);
}
