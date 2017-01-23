package com.udacity.moviediary.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.udacity.moviediary.R;
import com.udacity.moviediary.activity.MovieActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Implementation of App Widget functionality.
 */
public class CollectionWidget extends AppWidgetProvider {
    private static final String TAG = CollectionWidget.class.getSimpleName();
    public static String WIDGET_BUTTON = "com.udacity.moviediary.WIDGET_BUTTON";

    void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        /*if (NetworkUtil.isConnectionAvailable(context)) {
            NetworkManager.requestMovies(Config.UrlConstants.SORT_RELEASE_DATE_DESC, 1, new Callback<DiscoverMovieResponse>() {
                @Override
                public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {
                    if (response != null && response.isSuccessful()
                            && response.body() != null) {
                        Log.d(TAG, "onResponse: updateAppWidget onResponse");
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget);

                        Intent intent = new Intent(context, CollectionWidget.class);
                        intent.setAction(WIDGET_BUTTON);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                        views.setOnClickPendingIntent(R.id.widget_layout_main, pendingIntent);
                        // Set up the collection
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            setRemoteAdapter(context, views, appWidgetId, response.body().getResults());
                        } else {
                            setRemoteAdapterV11(context, views, appWidgetId, response.body().getResults());
                        }
                        // Instruct the widget manager to update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                }

                @Override
                public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: updateAppWidget onFailure");
                }
            });
        }*/



            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, getClass());
        intent.setAction(WIDGET_BUTTON);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_layout_main, pendingIntent );
        // Set up the collection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, views, appWidgetId);
        } else {
            setRemoteAdapterV11(context, views, appWidgetId);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     * @param appWidgetId
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views, int appWidgetId/*, List<MovieResult> movieResults*/) {
        final Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        intent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, new ArrayList<Parcelable>(movieResults));
        views.setRemoteAdapter(R.id.widget_list, intent);
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     * @param appWidgetId
     */
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views, int appWidgetId) {
        final Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        /*intent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, new ArrayList<Parcelable>(movieResults));*/
        views.setRemoteAdapter(0, R.id.widget_list, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (WIDGET_BUTTON.equals(intent.getAction())) {
            Log.d(TAG, "onReceive: WIDGET_BUTTON");
            final Intent intent1 = new Intent(context, MovieActivity.class);
            intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}

