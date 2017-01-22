package com.udacity.moviediary;

import android.app.Application;

import com.udacity.moviediary.utility.PreferenceManager;

/**
 * Created by Amardeep on 12/12/2016.
 */
public class MovieDiaryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(getApplicationContext());
    }
}
