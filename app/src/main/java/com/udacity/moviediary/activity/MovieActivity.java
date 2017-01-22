package com.udacity.moviediary.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.moviediary.R;
import com.udacity.moviediary.adapter.MovieGalleryCursorAdapter;
import com.udacity.moviediary.databinding.ActivityMovieBinding;
import com.udacity.moviediary.fragment.MovieDetailFragment;
import com.udacity.moviediary.fragment.MovieMasterFragment;
import com.udacity.moviediary.utility.Constants;
import com.udacity.moviediary.utility.PreferenceManager;

/**
 * Class to show the Movie related UI
 * Created by Amardeep on 18/12/16.
 */
public class MovieActivity extends BaseActivity implements MovieGalleryCursorAdapter.OnItemClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = MovieActivity.class.getSimpleName();
    private MovieDetailFragment mMovieDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        setSupportActionBar(binding.toolbar);
        AdRequest adRequest = new AdRequest.Builder().build();
        ((AdView) findViewById(R.id.adView)).loadAd(adRequest);
        if (getResources().getBoolean(R.bool.isTablet)) {
            mMovieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.movie_detail_fragment);
        } else {
            if (findViewById(R.id.fragment_container) != null) {
                if (savedInstanceState != null) {
                    return;
                }

                // Create a new Fragment to be placed in the activity layout
                MovieMasterFragment movieMasterFragment = new MovieMasterFragment();
                movieMasterFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, movieMasterFragment).commit();
            }
        }
    }

    @Override
    public void OnItemClicked(String movieId) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    MovieDetailFragment.getInstance(movieId)).addToBackStack(null).commit();
        } else if (mMovieDetailFragment != null) {
            mMovieDetailFragment.loadMovieDetails(movieId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.
                    getSearchableInfo(getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(this);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!getResources().getBoolean(R.bool.isTablet)
                && getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof MovieDetailFragment) {
            //Hiding menu for detail fragment in case of phone
            menu.findItem(R.id.sort_by_popular).setVisible(false);
            menu.findItem(R.id.sort_by_release_date).setVisible(false);
            menu.findItem(R.id.sort_by_rating).setVisible(false);
            menu.findItem(R.id.sort_by_name).setVisible(false);
        } else {
            switch (PreferenceManager.getInstance().getInt(Constants.BundleKeys.SORT_PREFERENCE,
                    Constants.SortPreference.SORT_BY_POPULARITY)) {
                case Constants.SortPreference.SORT_BY_POPULARITY:
                    menu.findItem(R.id.sort_by_popular).setChecked(true);
                    break;
                case Constants.SortPreference.SORT_BY_RELEASE_DATE:
                    menu.findItem(R.id.sort_by_release_date).setChecked(true);
                    break;
                case Constants.SortPreference.SORT_BY_RATING:
                    menu.findItem(R.id.sort_by_rating).setChecked(true);
                    break;
                case Constants.SortPreference.SORT_BY_NAME:
                    menu.findItem(R.id.sort_by_name).setChecked(true);
                    break;
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!getResources().getBoolean(R.bool.isTablet)) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(R.string.app_name);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit: query = " + query);
        Intent intent = new Intent(this, SearchableActivity.class);
        int fragmentId;
        if (getResources().getBoolean(R.bool.isTablet)) {
            fragmentId = R.id.movie_gallery_fragment;
        } else {
            fragmentId = R.id.fragment_container;
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(fragmentId);
        if (fragment != null &&
                fragment instanceof MovieMasterFragment) {
            intent.putExtra(Constants.BundleKeys.IS_FAVOURITE_MODE, ((MovieMasterFragment) fragment).getCurrentTabPosition());
        }
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
