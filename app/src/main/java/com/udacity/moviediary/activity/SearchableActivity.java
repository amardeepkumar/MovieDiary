package com.udacity.moviediary.activity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.udacity.moviediary.R;
import com.udacity.moviediary.adapter.MovieGalleryCursorAdapter;
import com.udacity.moviediary.data.MovieContract;
import com.udacity.moviediary.databinding.ActivitySearchBinding;
import com.udacity.moviediary.fragment.MovieListFragment;

/**
 * Created by Amardeep Kumar on 1/14/2017.
 */
public class SearchableActivity extends BaseActivity implements SearchView.OnQueryTextListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = SearchableActivity.class.getSimpleName();
    private static final int MOVIE_SEARCH_LOADER = 1;
    private static String GMS_SEARCH_ACTION = "com.google.android.gms.actions.SEARCH_ACTION";
    private ActivitySearchBinding mActivitySearchBinding;
    private String mQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mActivitySearchBinding.movieList.setLayoutManager(gridLayoutManager);
        final MovieGalleryCursorAdapter adapter = new MovieGalleryCursorAdapter(SearchableActivity.this, null, null);
        mActivitySearchBinding.movieList.setAdapter(adapter);
        setSupportActionBar(mActivitySearchBinding.toolbar);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SEARCH) ||
                action.equals(GMS_SEARCH_ACTION)) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            getSupportLoaderManager().initLoader(MOVIE_SEARCH_LOADER, null, this);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mQuery = s;
        getSupportLoaderManager().restartLoader(MOVIE_SEARCH_LOADER, null, this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        if (searchView != null) {
            if (mQuery != null) {
                searchView.setQuery(mQuery, false);
            }
            searchView.setIconifiedByDefault(false);
            searchView.setSearchableInfo(searchManager.
                    getSearchableInfo(getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(this);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri searchUri = MovieContract.MovieEntry.buildSearchUri(mQuery);
        String[] selectionArgs = null;
        String selection = null;
//        if (getArguments() != null && getArguments().getBoolean(Constants.BundleKeys.IS_FAVOURITE_MODE)) {
            selection = MovieContract.MovieEntry.COLUMN_FAVOURITE + " = ?";
            selectionArgs = new String[]{"1"};
//        }
        return new CursorLoader(this,
                searchUri,
                MovieListFragment.MOVIE_PROJECTION,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            ((MovieGalleryCursorAdapter) mActivitySearchBinding.movieList.getAdapter())
                    .swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((MovieGalleryCursorAdapter) mActivitySearchBinding.movieList.getAdapter())
                .swapCursor(null);
    }
}
