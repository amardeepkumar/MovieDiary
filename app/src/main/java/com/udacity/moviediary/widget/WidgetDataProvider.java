package com.udacity.moviediary.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.udacity.moviediary.R;
import com.udacity.moviediary.model.response.DiscoverMovieResponse;
import com.udacity.moviediary.utility.Constants;

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";
    private int mAppWidgetId;

    DiscoverMovieResponse response;
    Context mContext;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        if (intent != null) {
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
//            mCollection = intent.getParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
        }
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return response.getResults().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_item);
        //AppWidgetTarget appWidgetTarget = new AppWidgetTarget(mContext, view, R.id.image_view_item, mAppWidgetId);

        Intent intent = new Intent(mContext, CollectionWidget.class);
        intent.setAction(CollectionWidget.WIDGET_BUTTON);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        view.setOnClickPendingIntent(R.id.widget_layout_main, pendingIntent );
        /*SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                view.setImageViewBitmap(R.id.image_view_item, bitmap);
            }
        };*/
        /*Glide.with(mContext.getApplicationContext() ) // safer!
                .load(Config.UrlConstants.IMAGE_BASE_URL + "/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg")
                .asBitmap()
                .into(target);*/
        view.setImageViewResource(R.id.image_view_item, R.drawable.placeholder);
        view.setTextViewText(R.id.movie_name, response.getResults().get(position).getOriginalTitle());
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
//        for (int i = 1; i <= 10; i++) {
            response = new Gson().fromJson(Constants.WIDGET_RESPONSE, DiscoverMovieResponse.class);
//            mCollection.add(new MovieResult());
//        }
    }
}
