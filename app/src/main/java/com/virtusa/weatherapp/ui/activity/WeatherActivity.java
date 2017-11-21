package com.virtusa.weatherapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.controller.WAFragmentController;
import com.virtusa.weatherapp.ui.fragments.WAWeatherFragment;
import com.virtusa.weatherapp.utility.constants.WAConstants;

public class WeatherActivity extends AppCompatActivity {
    private WAFragmentController fragmentController;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initialize();
        fragmentController.changeFragment(WAWeatherFragment.newInstance());
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_navigation_top_bar);
        setSupportActionBar(toolbar);
        fragmentController = new WAFragmentController();
        fragmentController.setContainer(this, R.id.frame_layout_fragment_container, getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        if (fragmentController.getBackStackEntryCount() == WAConstants.ONE) {
            finish();
        } else {
            fragmentController.back();
        }
    }
}
