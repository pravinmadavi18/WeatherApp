package com.virtusa.weatherapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.event.WABusProvider;

/**
 * This is a abstract fragment class used as a base fragment,every fragment will extend this class.
 * It would do some common stuff for all fragments like logging etc.
 */
public abstract class WABaseFragment extends Fragment {
    public WABaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wabase, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        WABusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        WABusProvider.getInstance().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
