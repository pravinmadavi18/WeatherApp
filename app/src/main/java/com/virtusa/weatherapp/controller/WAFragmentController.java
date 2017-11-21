package com.virtusa.weatherapp.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.virtusa.weatherapp.R;

/**
 * This class controls the fragments on stack and provides the common functionality to overall app related to fragments
 */

public class WAFragmentController {
    private final String TAG = WAFragmentController.class.getSimpleName();
    private FragmentActivity activity;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private int containerId = R.id.frame_layout_fragment_container;

    public void setContainer(FragmentActivity activity, int containerId, FragmentManager fragmentManager) {
        setActivity(activity);
        setContainerId(containerId);
        setFragmentManager(fragmentManager);
    }

    public WAFragmentController() {

    }

    public void changeFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .addToBackStack(fragment.getClass().getSimpleName())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
        setCurrentFragment(fragment);
    }

    public void back() {
        try {
            fragmentManager.popBackStackImmediate();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public int getContainerId() {
        return containerId;
    }

    public void backInclusive(String id) {
        fragmentManager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public int getBackStackEntryCount() {
        return fragmentManager.getBackStackEntryCount();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
