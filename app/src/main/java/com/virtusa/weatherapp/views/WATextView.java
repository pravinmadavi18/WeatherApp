package com.virtusa.weatherapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This is a base textview class to be used for common functionality like themes and colors or any other props.
 */

public class WATextView extends TextView {
    public WATextView(Context context) {
        super(context);
    }
    public WATextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WATextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
