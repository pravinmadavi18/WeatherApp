package com.virtusa.weatherapp.ui;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.virtusa.weatherapp.R;
import com.virtusa.weatherapp.setup.WAUIBaseTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 *
 */

public class WeatherActivityTest extends WAUIBaseTest {
    Toolbar mToolbar;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveInitializedViews() throws Exception {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.frame_layout_fragment_container);
        assertNotNull(fragment);
    }

    @Test
    public void shouldHaveToolBarTheme() throws Exception {
        mToolbar = (Toolbar) activity.findViewById(R.id.toolbar_navigation_top_bar);
        Assert.assertNotNull(mToolbar);
    }
}
