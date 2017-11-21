package com.virtusa.weatherapp.base;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.virtusa.weatherapp.network.WANetworkManager;
import com.virtusa.weatherapp.utility.log.WALogger;
import com.virtusa.weatherapp.utility.utils.WADataManager;

/**
 * This is the application base class to initialize the components before views gets launched.
 */

public class WAApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
        WALogger.getInstance().setLog(true);
        WANetworkManager.getInstance(this);
        WADataManager.getInstance().parseCityData(this);
    }
}
