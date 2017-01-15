package com.udacity.moviediary.activity;


import android.app.SearchManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.udacity.moviediary.R;
import com.udacity.moviediary.databinding.ActivityMovieBinding;

/**
 * Created by Amardeep Kumar on 1/14/2017.
 */
public class SearchableActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        binding.toolbar.setTitle(R.string.app_name);
        setSupportActionBar(binding.toolbar);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
//            doMySearch(query);
        }
    }
}
