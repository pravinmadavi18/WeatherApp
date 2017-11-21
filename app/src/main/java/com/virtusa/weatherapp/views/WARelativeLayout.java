package com.virtusa.weatherapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Base class for relative layout
 */

public class WARelativeLayout extends RelativeLayout {
    public WARelativeLayout(Context context) {
        super(context);
    }

    public WARelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WARelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
