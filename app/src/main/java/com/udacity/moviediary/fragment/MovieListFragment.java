package com.udacity.moviediary.fragment;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.moviediary.R;
import com.udacity.moviediary.adapter.MovieGalleryCursorAdapter;
import com.udacity.moviediary.data.CustomAsyncQueryHandler;
import com.udacity.moviediary.data.MovieContract;
import com.udacity.moviediary.databinding.FragmentMovieGalleryBinding;
import com.udacity.moviediary.model.response.DiscoverMovieResponse;
import com.udacity.moviediary.model.response.MovieResult;
import com.udacity.moviediary.network.Config;
import com.udacity.moviediary.network.NetworkManager;
import com.udacity.moviediary.utility.Constants;
import com.udacity.moviediary.utility.DialogUtils;
import com.udacity.moviediary.utility.FireBaseManager;
import com.udacity.moviediary.utility.NetworkUtil;
import com.udacity.moviediary.utility.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amardeep on 12/12/2016.
 */
public class MovieListFragment extends BaseFragment  implements LoaderManager.LoaderCallbacks<Cursor>,
        Callback<DiscoverMovieResponse> {

    private static final String TAG = MovieListFragment.class.getSimpleName();

    public static final String[] MOVIE_PROJECTION = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_BACK_DROP_PATH,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_FAVOURITE,
            MovieContract.MovieEntry.COLUMN_IS_SELECTED
    };

    // These indices are tied to MOVIE_PROJECTION.  If MOVIE_PROJECTION changes, these
    // must change.
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_MOVIE_ID = 1;
    public static final int COLUMN_BACK_DROP_PATH = 2;
    public static final int COLUMN_POSTER_PATH = 3;
    public static final int COLUMN_FAVOURITE = 4;
    public static final int COLUMN_IS_SELECTED = 5;

    private static final int MOVIE_GALLERY_LOADER = 0;
    private static final String STATE_PAGE_LOADED = "STATE_PAGE_LOADED";

    private FragmentMovieGalleryBinding binding;
    private boolean loading;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private int mCurrentPage;
    private MovieGalleryCursorAdapter.OnItemClickListener mItemClickListener;
    private RecyclerView.OnScrollListener mOnScrollListener;

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
            binding.progressBar.setVisibility(View.GONE);
        }
    };

    public static Fragment getInstance(boolean isFavouriteMode) {
        MovieListFragment movieListFragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.BundleKeys.IS_FAVOURITE_MODE, isFavouriteMode);
        movieListFragment.setArguments(args);
        return movieListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mItemClickListener = (MovieGalleryCursorAdapter.OnItemClickListener) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentPage = savedInstanceState != null
                ? savedInstanceState.getInt(STATE_PAGE_LOADED, 0)
                : 0;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_GALLERY_LOADER, null, this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_PAGE_LOADED, mCurrentPage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_gallery, container, false);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        binding.movieList.setLayoutManager(gridLayoutManager);
        binding.movieList.setAdapter(new MovieGalleryCursorAdapter(mContext, null, mItemClickListener));

        boolean isFavMode = false;

        if (getArguments() != null) {
            isFavMode = getArguments().getBoolean(Constants.BundleKeys.IS_FAVOURITE_MODE);

            //Add listener if sort pref is not SORT_BY_FAVOURITE
            if (!isFavMode) {
                mOnScrollListener = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        /*check for scroll down*/
                        if (dy > 0) {
                            visibleItemCount = gridLayoutManager.getChildCount();
                            totalItemCount = gridLayoutManager.getItemCount();
                            firstVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();

                            if (!loading) {
                                if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                                    loadMore(MovieListFragment.this);
                                }
                            }
                        }
                    }
                };
                binding.movieList.addOnScrollListener(mOnScrollListener); //Infinite loading
            }
        }

        //Initial loading the data if fresh launch
        if (savedInstanceState == null && !isFavMode) {
            loadMore(this);
        }
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_popular:
                if (!item.isChecked()) {
                    binding.movieList.addOnScrollListener(mOnScrollListener);
                    PreferenceManager.getInstance().setInt(Constants.BundleKeys.SORT_PREFERENCE,
                            Constants.SortPreference.SORT_BY_POPULARITY);
                    getLoaderManager().restartLoader(MOVIE_GALLERY_LOADER, null, this);
                    sortList(item);
                }
                return true;

            case R.id.sort_by_release_date:
                if (!item.isChecked()) {
                    binding.movieList.addOnScrollListener(mOnScrollListener);
                    PreferenceManager.getInstance().setInt(Constants.BundleKeys.SORT_PREFERENCE,
                            Constants.SortPreference.SORT_BY_RELEASE_DATE);
                    getLoaderManager().restartLoader(MOVIE_GALLERY_LOADER, null, this);
                    sortList(item);
                }
                return true;

            case R.id.sort_by_rating:
                if (!item.isChecked()) {
                    binding.movieList.addOnScrollListener(mOnScrollListener);
                    PreferenceManager.getInstance().setInt(Constants.BundleKeys.SORT_PREFERENCE,
                            Constants.SortPreference.SORT_BY_RATING);
                    getLoaderManager().restartLoader(MOVIE_GALLERY_LOADER, null, this);
                    sortList(item);
                }
                return true;

            case R.id.sort_by_name:
                if (!item.isChecked()) {
                    binding.movieList.addOnScrollListener(mOnScrollListener);
                    PreferenceManager.getInstance().setInt(Constants.BundleKeys.SORT_PREFERENCE,
                            Constants.SortPreference.SORT_BY_NAME);
                    getLoaderManager().restartLoader(MOVIE_GALLERY_LOADER, null, this);
                    sortList(item);
                }
                return true;
            case R.id.app_bar_search:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortList(MenuItem item) {
        item.setChecked(true);
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
            FireBaseManager.getFireBaseAnalytics().logEvent(Constants.AnalyticsKeys.LOAD_MORE_MOVIE, params);
            //************Analytics code end*******************
            loading = true;
            if (binding != null) {
                binding.progressBar.setVisibility(View.VISIBLE);
            }

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
            if (binding != null) {
                binding.progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {
        saveDataAndUpdateUi(response);
    }

    @Override
    public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
        loading = false;
        DialogUtils.showToast(R.string.response_failed, mContext);
        if (binding != null) {
            binding.progressBar.setVisibility(View.GONE);
        }
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
                    if (binding != null && binding.progressBar.getVisibility() == View.VISIBLE) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.movieList.clearOnScrollListeners();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] selectionArgs = null;
        String selection = null;
        if (getArguments() != null && getArguments().getBoolean(Constants.BundleKeys.IS_FAVOURITE_MODE)) {
            selection = MovieContract.MovieEntry.COLUMN_FAVOURITE + " = ?";
            selectionArgs = new String[]{"1"};
        }

        return new CursorLoader(mContext,
                MovieContract.MovieEntry.CONTENT_URI,
                MOVIE_PROJECTION,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            binding.noDataFound.setVisibility(View.GONE);
            ((MovieGalleryCursorAdapter)binding.movieList.getAdapter()).swapCursor(data);
        } else {
            binding.noDataFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((MovieGalleryCursorAdapter)binding.movieList.getAdapter()).swapCursor(null);
    }
}
