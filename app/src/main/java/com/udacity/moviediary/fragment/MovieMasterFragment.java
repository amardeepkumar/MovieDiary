package com.udacity.moviediary.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.moviediary.R;
import com.udacity.moviediary.adapter.ViewPagerAdapter;
import com.udacity.moviediary.databinding.FragmentMovieMasterBinding;

/**
 * Created by Amardeep on 18/2/16.
 */
public class MovieMasterFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private static final String TAG = MovieMasterFragment.class.getSimpleName();


    private FragmentMovieMasterBinding binding;
    private int mCurrentTabPosition;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_master, container, false);
        setupViewPager(binding.viewpager);

        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.viewpager.addOnPageChangeListener(this);
        return binding.getRoot();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(MovieListFragment.getInstance(false), getString(R.string.movies));
        adapter.addFragment(MovieListFragment.getInstance(true), getString(R.string.favourites));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentTabPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getCurrentTabPosition() {
        return mCurrentTabPosition;
    }
}
