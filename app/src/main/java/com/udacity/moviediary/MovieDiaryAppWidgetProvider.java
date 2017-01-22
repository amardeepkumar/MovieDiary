package com.udacity.moviediary;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.udacity.moviediary.activity.MovieActivity;

/**
 * Created by Amardeep Kumar on 1/21/2017.
 */
public class MovieDiaryAppWidgetProvider extends AppWidgetProvider {

    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    public static final String EXTRA_ITEM = "com.example.edockh.EXTRA_ITEM";


  /*  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int totalWidgetCount = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int index = 0; index < totalWidgetCount; index++) {
            int appWidgetId = appWidgetIds[index];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MovieActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
         //   views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }*/

    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(UPDATE_MEETING_ACTION)) {
            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, MovieDiaryAppWidgetProvider.class));
            Log.e("received", intent.getAction());
            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        }

        super.onReceive(context, intent);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int index = 0; index < appWidgetIds.length; ++index) {
            Intent intent = new Intent(context, ListViewWidgetService.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[index]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
            rv.setRemoteAdapter(appWidgetIds[index], R.id.list_view, intent);
            Intent startActivityIntent = new Intent(context, MovieActivity.class);

            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, startActivityPendingIntent);
            rv.setEmptyView(R.id.list_view, R.id.empty_view);
            appWidgetManager.updateAppWidget(appWidgetIds[index], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}