package com.virtusa.weatherapp.event;

import com.squareup.otto.Bus;

/**
 * Otto Event bus singleton class to provide singleton instance
 */

public class WABusProvider {

    private static final Bus BUS = new Bus();

    /**
     * To get the singleton instance of the bus
     *
     * @return
     */
    public static Bus getInstance() {
        return BUS;
    }

    private WABusProvider() {
        // private constructor to support the singleton pattern
    }
}
