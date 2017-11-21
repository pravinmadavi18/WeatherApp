package com.virtusa.weatherapp.network;

import com.android.volley.VolleyError;

/**
 * Interface for On Failure response network call so that all network call should use this.
 * will be useful for the common things to apply for all the network class like logging etc.
 */

public interface WANetworkResponseFailureInterface {
    void onFailure(VolleyError error);
}
