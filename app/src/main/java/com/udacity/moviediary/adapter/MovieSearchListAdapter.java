package com.udacity.moviediary.adapter;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.udacity.moviediary.R;
import com.udacity.moviediary.databinding.ItemMovieGalleryBinding;
import com.udacity.moviediary.fragment.MovieListFragment;
import com.udacity.moviediary.model.response.MovieResult;

import java.util.List;

/**
 * Created by Amardeep on 23/2/16.
 */
public class MovieSearchListAdapter extends RecyclerView.Adapter<MovieSearchListAdapter.MovieSearchViewHolder> {
    private static final String TAG = MovieSearchListAdapter.class.getSimpleName();
    private final LayoutInflater mLayoutInflater;
    private List<MovieResult> mMovieResultList;

    public MovieSearchListAdapter(Context context, List<MovieResult> movieResultList) {
        mLayoutInflater = LayoutInflater.from(context);
        mMovieResultList = movieResultList;
    }

    @Override
    public MovieSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieGalleryBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_movie_gallery, parent, false);
        return new MovieSearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieSearchViewHolder viewHolder, int position) {
        viewHolder.bindItem();
    }

    @Override
    public int getItemCount() {
        return mMovieResultList == null ? 0 : mMovieResultList.size();
    }

    public void setList(@NonNull List<MovieResult> movieResultList) {
        mMovieResultList = movieResultList;
        notifyDataSetChanged();
    }

    public class MovieSearchViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieGalleryBinding mItemBinding;

        public MovieSearchViewHolder(ItemMovieGalleryBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }

        public void bindItem() {
            mItemBinding.setData(mMovieResultList.get(getAdapterPosition()));
        }
    }
}
