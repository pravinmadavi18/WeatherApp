package com.virtusa.weatherapp.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.virtusa.weatherapp.setup.WAUIBaseTest;
import com.virtusa.weatherapp.ui.fragments.WAWeatherFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

/**
 *
 */

public class WAFragmentControllerTest extends WAUIBaseTest {
    WAFragmentController subject;


    @Before
    public void setUp() {
        super.setUp();
        subject = new WAFragmentController();
    }

    @Test
    public void setContainer(){
        FragmentActivity fragmentActivity = Robolectric.buildActivity(FragmentActivity.class).create().get();
        int id = 92;
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        subject.setContainer(fragmentActivity, id, fragmentManager);

        Assert.assertSame(fragmentActivity, subject.getActivity());
        Assert.assertSame(fragmentManager, subject.getFragmentManager());
        Assert.assertEquals(id, subject.getContainerId());
    }

    @Test
    public void changeFragments(){
        FragmentActivity fragmentActivity = Robolectric.buildActivity(FragmentActivity.class).create().get();
        int id = 92;
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        subject.setContainer(fragmentActivity, id, fragmentManager);

        Fragment fragment = new Fragment();
        subject.changeFragment(fragment);
        Assert.assertSame(fragment, subject.getCurrentFragment());

    }

    @Test
    public void back(){
        FragmentActivity fragmentActivity = Robolectric.buildActivity(FragmentActivity.class).create().get();
        subject = new WAFragmentController();
        int id = 94;
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        subject.setContainer(fragmentActivity, id, fragmentManager);

        WAWeatherFragment weatherFragment = new WAWeatherFragment();
        subject.changeFragment(weatherFragment);
        Fragment fragment2 = new Fragment();
        subject.changeFragment(fragment2);
        Assert.assertSame(fragment2, subject.getCurrentFragment());
        //subject.back();
       // Assert.assertTrue(subject.getCurrentFragment().getClass().getSimpleName().equalsIgnoreCase(WAWeatherFragment.class.getSimpleName()));
    }
}
