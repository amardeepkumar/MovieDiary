package com.udacity.moviediary;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.udacity.moviediary.activity.MovieActivity;

/**
 * Created by Amardeep Kumar on 1/21/2017.
 */
public class MovieDiaryAppWidgetConfigure extends Activity {

    private int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(this.getPackageName(),
                R.layout.appwidget_provider_layout);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);
        Intent intentToLaunch = new Intent(this, MovieActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentToLaunch, 0);

        views.setOnClickPendingIntent(R.id.button, pendingIntent);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
