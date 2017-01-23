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
import android.view.View;

import com.udacity.moviediary.R;
import com.udacity.moviediary.adapter.MovieGalleryCursorAdapter;
import com.udacity.moviediary.adapter.MovieSearchListAdapter;
import com.udacity.moviediary.data.MovieContract;
import com.udacity.moviediary.databinding.ActivitySearchBinding;
import com.udacity.moviediary.fragment.MovieListFragment;
import com.udacity.moviediary.model.response.DiscoverMovieResponse;
import com.udacity.moviediary.network.NetworkManager;
import com.udacity.moviediary.utility.CollectionUtils;
import com.udacity.moviediary.utility.Constants;
import com.udacity.moviediary.utility.DialogUtils;
import com.udacity.moviediary.utility.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amardeep Kumar on 1/14/2017.
 */
public class SearchableActivity extends BaseActivity implements SearchView.OnQueryTextListener,
        LoaderManager.LoaderCallbacks<Cursor>, Callback<DiscoverMovieResponse> {

    private static final String TAG = SearchableActivity.class.getSimpleName();
    private static final int MOVIE_SEARCH_LOADER = 1;
    private static final int MOVIES = 0;
    private static final int FAVOURITE = 1;
    private static String GMS_SEARCH_ACTION = "com.google.android.gms.actions.SEARCH_ACTION";
    private ActivitySearchBinding mActivitySearchBinding;
    private String mQuery;
    private int mTabIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mActivitySearchBinding.movieList.setLayoutManager(gridLayoutManager);
        setSupportActionBar(mActivitySearchBinding.toolbar);

        if (getIntent() != null) {
            mTabIndex = getIntent().getIntExtra(Constants.BundleKeys.IS_FAVOURITE_MODE, MOVIES);
        }
        mActivitySearchBinding.movieList.setAdapter(mTabIndex ==  FAVOURITE ? new MovieGalleryCursorAdapter(SearchableActivity.this, null, null) : new MovieSearchListAdapter(this, null));

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SEARCH) ||
                action.equals(GMS_SEARCH_ACTION)) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            if (mTabIndex ==  FAVOURITE) {
                mActivitySearchBinding.progressBar.setVisibility(View.VISIBLE);
                getSupportLoaderManager().initLoader(MOVIE_SEARCH_LOADER, null, this);
            } else {
                doSearch();
            }
        }
    }

    private void doSearch() {
        if (NetworkUtil.isConnectionAvailable(this)) {
            mActivitySearchBinding.progressBar.setVisibility(View.VISIBLE);
            NetworkManager.searchMovies(mQuery, this);
        } else {
            mActivitySearchBinding.progressBar.setVisibility(View.GONE);
            DialogUtils.showToast(R.string.no_network, this);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mQuery = s;
        if (mTabIndex ==  FAVOURITE) {
            mActivitySearchBinding.progressBar.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(MOVIE_SEARCH_LOADER, null, this);
        } else {
            doSearch();
        }
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
        String[] selectionArgs;
        String selection;
        selection = MovieContract.MovieEntry.COLUMN_FAVOURITE + " = ?";
        selectionArgs = new String[]{"1"};
        return new CursorLoader(this,
                searchUri,
                MovieListFragment.MOVIE_PROJECTION,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mActivitySearchBinding.progressBar.setVisibility(View.GONE);
        if (data.getCount() > 0) {
            mActivitySearchBinding.noResultFound.setVisibility(View.GONE);
            ((MovieGalleryCursorAdapter) mActivitySearchBinding.movieList.getAdapter())
                    .swapCursor(data);
        } else {
            mActivitySearchBinding.noResultFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((MovieGalleryCursorAdapter) mActivitySearchBinding.movieList.getAdapter())
                .swapCursor(null);
    }

    @Override
    public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {
        mActivitySearchBinding.progressBar.setVisibility(View.GONE);
        if (response != null && response.isSuccessful()
                && response.body() != null) {
            if (CollectionUtils.isEmpty(response.body().getResults())) {
                mActivitySearchBinding.noResultFound.setVisibility(View.VISIBLE);
            } else {
                mActivitySearchBinding.noResultFound.setVisibility(View.GONE);
            }
            ((MovieSearchListAdapter) mActivitySearchBinding.movieList.getAdapter()).setList(response.body().getResults());
        } else {
            DialogUtils.showToast("response failed", this);
        }
    }

    @Override
    public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
        mActivitySearchBinding.progressBar.setVisibility(View.GONE);
        DialogUtils.showToast("response failed", this);
    }
}
