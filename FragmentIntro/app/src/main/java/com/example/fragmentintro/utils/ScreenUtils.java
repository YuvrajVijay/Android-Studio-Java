package com.example.fragmentintro.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtils {
    private Activity activity;
    private float dpWidth;
    private float dpHeight;

    public ScreenUtils(Activity activity) {
        this.activity = activity;

        Display display=activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        display.getMetrics(displayMetrics);

        float density =activity.getResources().getDisplayMetrics().density;

        dpWidth=displayMetrics.widthPixels/density;
        dpHeight=displayMetrics.heightPixels/density;
    }

    public float getDpWidth() {
        return dpWidth;
    }

    public float getDpHeight() {
        return dpHeight;
    }
}
