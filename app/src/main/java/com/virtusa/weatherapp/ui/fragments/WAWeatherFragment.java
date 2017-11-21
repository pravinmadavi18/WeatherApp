package com.virtusa.weatherapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.otto.Subscribe;
import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.base.WABaseFragment;
import com.virtusa.weatherapp.model.Main;
import com.virtusa.weatherapp.model.Weather;
import com.virtusa.weatherapp.model.WeatherDataModel;
import com.virtusa.weatherapp.network.WANetworkManager;
import com.virtusa.weatherapp.network.WANetworkRequestHandler;
import com.virtusa.weatherapp.utility.constants.WAConstants;
import com.virtusa.weatherapp.utility.log.WALogger;
import com.virtusa.weatherapp.utility.utils.WADataManager;
import com.virtusa.weatherapp.utility.utils.WASharedPreferencesUtility;
import com.virtusa.weatherapp.utility.utils.WAUtility;
import com.virtusa.weatherapp.views.WARelativeLayout;
import com.virtusa.weatherapp.views.WATextView;

import java.util.ArrayList;

/**
 * This Fragment is to show the current weather for particular city and date.
 */
public class WAWeatherFragment extends WABaseFragment {

    public static final String TAG = WAWeatherFragment.class.getSimpleName();

    private AutoCompleteTextView citiesTextView;
    private WATextView dateTV, tempratureTV, conditionTV;
    private NetworkImageView weatherConditionNIV;
    private ArrayList<String> citiesList;
    private ProgressBar progressBar;
    private WARelativeLayout weatherInformationHeaderLayout,pressureLayout, humidityLayout, tempMinLayout, tempMaxLayout, seaLevelLayout, groundLevelLayout;
    WATextView pressureTypeTV, pressureValueTV, humidityTypeTV, humidityValueTV, tempMinTypeTV, tempMinValueTV, tempMaxTypeTV,
            tempMaxValueTV, seaLevelTypeTV, seaLevelValueTV, groundLevelTypeTV, groundLevelValueTV;

    public WAWeatherFragment() {
        // Required empty public constructor
    }
    public static WAWeatherFragment newInstance() {
        WAWeatherFragment fragment = new WAWeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_waweather, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {

        weatherInformationHeaderLayout = view.findViewById(R.id.rl_wa_weather_information);
        citiesTextView = view.findViewById(R.id.weather_acv_cities);
        dateTV = view.findViewById(R.id.weather_tv_date);
        tempratureTV = view.findViewById(R.id.weather_tv_temprature);
        weatherConditionNIV = view.findViewById(R.id.image_weather_condition);
        conditionTV = view.findViewById(R.id.tv_weather_condition);

        pressureLayout = view.findViewById(R.id.rl_wa_weather_details_pressure);
        humidityLayout = view.findViewById(R.id.rl_wa_weather_details_humidity);
        tempMinLayout = view.findViewById(R.id.rl_wa_weather_details_temp_min);
        tempMaxLayout = view.findViewById(R.id.rl_wa_weather_details_temp_max);
        seaLevelLayout = view.findViewById(R.id.rl_wa_weather_details_sea_level);
        groundLevelLayout = view.findViewById(R.id.rl_wa_weather_details_ground_level);

        pressureTypeTV = pressureLayout.findViewById(R.id.tv_weather_type);
        pressureTypeTV.setText(R.string.text_pressure);
        pressureValueTV = pressureLayout.findViewById(R.id.tv_weather_value);

        humidityTypeTV = humidityLayout.findViewById(R.id.tv_weather_type);
        humidityTypeTV.setText(R.string.text_humidity);
        humidityValueTV = humidityLayout.findViewById(R.id.tv_weather_value);

        tempMinTypeTV = tempMinLayout.findViewById(R.id.tv_weather_type);
        tempMinTypeTV.setText(R.string.text_min_temp);
        tempMinValueTV = tempMinLayout.findViewById(R.id.tv_weather_value);

        tempMaxTypeTV = tempMaxLayout.findViewById(R.id.tv_weather_type);
        tempMaxTypeTV.setText(R.string.text_max_temp);
        tempMaxValueTV = tempMaxLayout.findViewById(R.id.tv_weather_value);

        seaLevelTypeTV = seaLevelLayout.findViewById(R.id.tv_weather_type);
        seaLevelTypeTV.setText(R.string.text_sea_level);
        seaLevelValueTV = seaLevelLayout.findViewById(R.id.tv_weather_value);

        groundLevelTypeTV = groundLevelLayout.findViewById(R.id.tv_weather_type);
        groundLevelTypeTV.setText(R.string.text_ground_level);
        groundLevelValueTV = groundLevelLayout.findViewById(R.id.tv_weather_value);

        progressBar = view.findViewById(R.id.progress_bar);
        setDataToComponents();
    }

    private void setDataToComponents() {

        /* There are two options we could handle this scenario
         1. without showing the loader on ui we can load default cities data and
         later at runtime get the cities json in background using the service or volley as data size is huge 4mb.
        2. By showing the loader if we dont want default data and fetch data @ runtime on app launch using volley.
        Due to time constraint this is lagging,*/

        citiesList = WADataManager.getInstance().getCitiesList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, citiesList);
        citiesTextView.setThreshold(WAConstants.THRESHOLD_VALUE_1);
        citiesTextView.setAdapter(arrayAdapter);
        citiesTextView.setOnItemClickListener(citiesOnItemClickListener);

        String citySearchedName = WASharedPreferencesUtility.getLastCitySearchedName(getContext());
        if (citySearchedName != null) {
            citiesTextView.setText(citySearchedName);
            Integer citySearchedID = WASharedPreferencesUtility.getLastCitySearchedID(getContext());
            getWeatherData(citySearchedID);
        }
    }

    private OnItemClickListener citiesOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cityName = parent.getItemAtPosition(position).toString();
            int cityId = WADataManager.getInstance().getCityIdByName(cityName);
            if (cityId != WAConstants.MINUS_ONE) {
                WASharedPreferencesUtility.setLastCitySearched(getContext(), cityId, cityName);
                getWeatherData(cityId);
            }
        }
    };

    public void getWeatherData(Integer cityID) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        WANetworkRequestHandler.getWeatherDataByCityId(getContext(), cityID);
    }

    @Subscribe
    public void onWeatherDataSuccess(WeatherDataModel weatherDataModel) {
        progressBar.setVisibility(View.GONE);
        setWeatherDataToComponent(weatherDataModel);
    }

    private void setWeatherDataToComponent(WeatherDataModel dataModel) {
        try {
            Main main = dataModel.getMain();
            if(main.getPressure()!=null)
            {
                pressureValueTV.setText(main.getPressure().toString());
                pressureLayout.setVisibility(View.VISIBLE);
            }
            if(main.getHumidity()!=null)
            {
                humidityValueTV.setText(main.getHumidity().toString());
                humidityLayout.setVisibility(View.VISIBLE);
            }
            if(main.getTempMin()!=null)
            {
                tempMinValueTV.setText(main.getTempMin().toString());
                tempMinLayout.setVisibility(View.VISIBLE);
            }
            if(main.getTempMax()!=null)
            {
                tempMaxValueTV.setText(main.getTempMax().toString());
                tempMaxLayout.setVisibility(View.VISIBLE);
            }
            if(main.getSeaLevel()!=null) {
                seaLevelValueTV.setText(main.getSeaLevel().toString());
                seaLevelLayout.setVisibility(View.VISIBLE);
            }
            if(main.getGrndLevel()!=null) {
                groundLevelValueTV.setText(main.getGrndLevel().toString());
                groundLevelLayout.setVisibility(View.VISIBLE);
            }
            if(main.getTemp()!=null) {
                dateTV.setText(WAUtility.getDateTime());
                tempratureTV.setText(main.getTemp().toString());
            }
            Weather weather = dataModel.getWeather().get(WAConstants.ZERO);
            conditionTV.setText(weather.getDescription());
            String iconUrl = getString(R.string.wa_base_icon_url) + weather.getIcon() + WAConstants.IMAGE_FORMAT_PNG;
            weatherConditionNIV.setImageUrl(iconUrl, WANetworkManager.getInstance(getContext()).getImageLoader());
            weatherInformationHeaderLayout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            WALogger.log(TAG, e.getStackTrace().toString());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
