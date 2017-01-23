package com.udacity.moviediary.utility;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Amardeep Kumar on 1/23/2017.
 */

public class FireBaseManager {
    private static FirebaseAnalytics mFireBaseAnalytics;

    private FireBaseManager() {

    }

    public static FirebaseAnalytics getFireBaseAnalytics() {
        return mFireBaseAnalytics;
    }
    public static void init(Context ctx) {
        mFireBaseAnalytics = FirebaseAnalytics.getInstance(ctx);
    }
}
