package com.udacity.moviediary;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.moviediary.data.CustomAsyncQueryHandler;
import com.udacity.moviediary.data.MovieContract;
import com.udacity.moviediary.model.response.DiscoverMovieResponse;
import com.udacity.moviediary.model.response.MovieResult;
import com.udacity.moviediary.network.Config;
import com.udacity.moviediary.network.NetworkManager;
import com.udacity.moviediary.utility.Constants;
import com.udacity.moviediary.utility.DialogUtils;
import com.udacity.moviediary.utility.NetworkUtil;
import com.udacity.moviediary.utility.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VijayLakshmi IN on 1/22/2017.
 */
class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, LoaderManager.LoaderCallbacks<Cursor>,
        Callback<DiscoverMovieResponse> {

    private Context mContext;

    private List<MovieResult> mMovieList;
    private int mCurrentPage;
    private boolean loading;
    private static final String[] MOVIE_PROJECTION = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_BACK_DROP_PATH,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_FAVOURITE,
            MovieContract.MovieEntry.COLUMN_IS_SELECTED};

    public ListViewRemoteViewsFactory(Context context, Intent intent) {

        mContext = context;

    }

    // Initialize the data set.

    public void onCreate() {

        // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,

        // for example downloading or creating content etc, should be deferred to onDataSetChanged()

        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.

        mMovieList = new ArrayList<MovieResult>();
    }

    // Given the position (index) of a WidgetItem in the array, use the item's text value in

    // combination with the app widget item XML file to construct a RemoteViews object.

    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.app_widget_gallery_item);
    //    String data = mMovieList.get(position);
        rv.setImageViewResource(R.id.image_view_item, R.drawable.refresh);
        // end feed row
        // Next, set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in ListViewWidgetProvider.
        Bundle extras = new Bundle();
        extras.putInt(MovieDiaryAppWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("home_screen", mMovieList.get(position).getPosterPath());
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.image_view_item, fillInIntent);

        return rv;

    }

    public int getCount(){

        Log.e("size=", mMovieList.size()+"");

        return mMovieList.size();

    }

    public void onDataSetChanged(){

        // Fetching JSON data from server and add them to mMovieList arraylist

      //  binding.movieList.addOnScrollListener(mOnScrollListener);
        PreferenceManager.getInstance().setInt(Constants.BundleKeys.SORT_PREFERENCE,
                Constants.SortPreference.SORT_BY_RELEASE_DATE);
        sortList();
    }

    public int getViewTypeCount(){

        return 1;
    }

    public long getItemId(int position) {

        return position;

    }

    public void onDestroy(){

        mMovieList.clear();

    }

    public boolean hasStableIds() {

        return true;
    }

    public RemoteViews getLoadingView() {

        return null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] selectionArgs = null;
        String selection = null;
     /*   if (getArguments() != null && getArguments().getBoolean(Constants.BundleKeys.IS_FAVOURITE_MODE)) {
            selection = MovieContract.MovieEntry.COLUMN_FAVOURITE + " = ?";
            selectionArgs = new String[]{"1"};
        }*/

        return new CursorLoader(mContext,
                MovieContract.MovieEntry.CONTENT_URI,
                MOVIE_PROJECTION,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       /* if (data.getCount() > 0) {
            ((MovieGalleryCursorAdapter)movieList.getAdapter()).swapCursor(data);
        }*/
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onResponse(Call<DiscoverMovieResponse> call, final Response<DiscoverMovieResponse> response) {
        {
            //Clearing all the records from all tables and saving new response
            CustomAsyncQueryHandler queryHandler = new CustomAsyncQueryHandler(mContext.getContentResolver());
            queryHandler.setAsyncApplyBatchListener(new CustomAsyncQueryHandler.AsyncApplyBatchListener() {
                @Override
                public void onApplyBatchComplete(int token, Object cookie, ContentProviderResult[] result) {
                    saveDataAndUpdateUi(response);
                }
            });

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            ops.add(ContentProviderOperation.newDelete(MovieContract.MovieEntry.CONTENT_URI)
                    .withSelection(MovieContract.MovieEntry.TABLE_NAME + "."
                            + MovieContract.MovieEntry.COLUMN_FAVOURITE + " = ?", new String[]{"0"})
                    .build());
            ops.add(ContentProviderOperation.newDelete(MovieContract.VideoEntry.CONTENT_URI)
                    .withSelection(null, null)
                    .build());
            ops.add(ContentProviderOperation.newDelete(MovieContract.ReviewEntry.CONTENT_URI)
                    .withSelection(null, null)
                    .build());
            queryHandler.applyBatch(1, null, MovieContract.CONTENT_AUTHORITY, ops);
        }
    }

    @Override
    public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {

    }

    private void saveDataAndUpdateUi(Response<DiscoverMovieResponse> response) {
        loading = false;
        if (response != null && response.isSuccessful()
                && response.body() != null) {
            mCurrentPage = response.body().getPage();

            final List<MovieResult> results = response.body().getResults();

            CustomAsyncQueryHandler queryHandler = new CustomAsyncQueryHandler(mContext.getContentResolver());
            queryHandler.setAsyncBulkInsertListener(new CustomAsyncQueryHandler.AsyncBulkInsertListener() {
                @Override
                public void onBulkInsertComplete(int token, Object cookie, int result) {

                }
            });
            ContentValues[] contentValues = new ContentValues[results.size()];
            int i = 0;
            for (MovieResult movieResult:
                    results) {
                ContentValues contentValue = new ContentValues();
                contentValue.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieResult.getId());
                contentValue.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movieResult.getPosterPath());
                contentValue.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieResult.getOriginalTitle());
                contentValue.put(MovieContract.MovieEntry.COLUMN_BACK_DROP_PATH, movieResult.getBackdropPath());
                contentValue.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieResult.getReleaseDate());
                contentValue.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movieResult.getVoteAverage());
                contentValue.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieResult.getOverview());
                contentValues[i++] = contentValue;
            }
            queryHandler.startBulkInsert(1, null, MovieContract.MovieEntry.CONTENT_URI, contentValues);
        }
    }

    private void sortList() {
        mCurrentPage = 0;
        loadMore(mCallBack);
    }

    /**
     * Loading the movie list
     * @param callBack
     */
    private void loadMore(Callback<DiscoverMovieResponse> callBack) {
        if (NetworkUtil.isConnectionAvailable(mContext)) {

            ///***********Analytics code start*****************
            Bundle params = new Bundle();
            params.putInt(Constants.AnalyticsKeys.PAGE_NUMBER, mCurrentPage);
            //************Analytics code end*******************
            loading = true;

            String sortBy = "";

            switch (PreferenceManager.getInstance().getInt(Constants.BundleKeys.SORT_PREFERENCE,
                    Constants.SortPreference.SORT_BY_POPULARITY)) {
                case Constants.SortPreference.SORT_BY_POPULARITY:
                    sortBy = Config.UrlConstants.SORT_POPULARITY_DESC;
                    break;
                case Constants.SortPreference.SORT_BY_RELEASE_DATE:
                    sortBy = Config.UrlConstants.SORT_RELEASE_DATE_DESC;
                    break;
                case Constants.SortPreference.SORT_BY_RATING:
                    sortBy = Config.UrlConstants.SORT_VOTE_AVERAGE_DESC;
                    break;
                case Constants.SortPreference.SORT_BY_NAME:
                    sortBy = Config.UrlConstants.SORT_NAME_ASC;
                    break;
            }
            NetworkManager.requestMovies(sortBy, mCurrentPage + 1, callBack);
        } else {
            DialogUtils.showToast(R.string.no_network, mContext);
        }
    }

    //Callback for reset adapter.
    private final Callback<DiscoverMovieResponse> mCallBack = new Callback<DiscoverMovieResponse>() {
        @Override
        public void onResponse(Call<DiscoverMovieResponse> call, final Response<DiscoverMovieResponse> response) {
            //Clearing all the records from all tables and saving new response
            CustomAsyncQueryHandler queryHandler = new CustomAsyncQueryHandler(mContext.getContentResolver());
            queryHandler.setAsyncApplyBatchListener(new CustomAsyncQueryHandler.AsyncApplyBatchListener() {
                @Override
                public void onApplyBatchComplete(int token, Object cookie, ContentProviderResult[] result) {
                    saveDataAndUpdateUi(response);
                }
            });

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            ops.add(ContentProviderOperation.newDelete(MovieContract.MovieEntry.CONTENT_URI)
                    .withSelection(MovieContract.MovieEntry.TABLE_NAME + "."
                            + MovieContract.MovieEntry.COLUMN_FAVOURITE + " = ?", new String[]{"0"})
                    .build());
            ops.add(ContentProviderOperation.newDelete(MovieContract.VideoEntry.CONTENT_URI)
                    .withSelection(null, null)
                    .build());
            ops.add(ContentProviderOperation.newDelete(MovieContract.ReviewEntry.CONTENT_URI)
                    .withSelection(null, null)
                    .build());
            queryHandler.applyBatch(1, null, MovieContract.CONTENT_AUTHORITY, ops);
        }

        @Override
        public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
            loading = false;
            DialogUtils.showToast("response failed", mContext);
        }
    };
}
