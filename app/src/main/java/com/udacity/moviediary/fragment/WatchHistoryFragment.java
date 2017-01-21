package com.udacity.moviediary.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.moviediary.R;
import com.udacity.moviediary.data.CustomAsyncQueryHandler;
import com.udacity.moviediary.data.MovieContract;
import com.udacity.moviediary.databinding.FragmentWatchHistoryBinding;
import com.udacity.moviediary.utility.Constants;

/**
 * Created by Amardeep Kumar on 1/16/2017.
 */
public class WatchHistoryFragment extends DialogFragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>, WatchHistoryDeleteConfirmationFragment.OnDeleteListener {
    // These indices are tied to MOVIE_PROJECTION.  If MOVIE_PROJECTION changes, these
    // must change.
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_MULTIPLEX_NAME = 1;
    public static final int COLUMN_DATE = 2;
    public static final int COLUMN_PLACE = 3;
    public static final int COLUMN_FRIENDS = 4;
    public static final int COLUMN_MOVIE_ID = 5;

    private static final int WATCH_HISTORY_LOADER = 1;
    private FragmentWatchHistoryBinding mBinding;
    private String mMovieId;

    public static WatchHistoryFragment getInstance(String movieId) {
        WatchHistoryFragment watchHistoryFragment = new WatchHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKeys.ID, movieId);
        watchHistoryFragment.setArguments(bundle);
        return watchHistoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_watch_history, container, false);
        mBinding.setClickHandler(this);
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getString(Constants.BundleKeys.ID, null);
        }
        getLoaderManager().initLoader(WATCH_HISTORY_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                MovieContract.WatchHistory.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mMovieId != null && data != null && data.moveToFirst()) {
            if (mBinding != null) {
                String[] selectionArgs = null;
                String selection = null;
                if (mMovieId != null) {
                    selection = MovieContract.WatchHistory.TABLE_NAME + "." + MovieContract.WatchHistory.COLUMN_MOVIE_ID + "=?";
                    selectionArgs = new String[]{mMovieId};
                }

                CustomAsyncQueryHandler queryHandler = new CustomAsyncQueryHandler(getActivity().getContentResolver());
                queryHandler.startQuery(1, null, MovieContract.WatchHistory.CONTENT_URI, null, selection, selectionArgs, null);
                queryHandler.setQueryListener(new CustomAsyncQueryHandler.AsyncQueryListener() {
                    @Override
                    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                        if (cursor != null && cursor.moveToFirst()) {
                            mBinding.multiplexName.setText(cursor.getString(COLUMN_MULTIPLEX_NAME));
                            mBinding.watchedDate.setText(cursor.getString(COLUMN_DATE));
                            mBinding.place.setText(cursor.getString(COLUMN_PLACE));
                            mBinding.friendsName.setText(cursor.getString(COLUMN_FRIENDS));
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_save_button:
                if (mBinding.multiplexName.isEnabled()) {
                    mBinding.editSaveButton.setImageResource(android.R.drawable.ic_menu_edit);
                    mBinding.multiplexName.setEnabled(false);
                    mBinding.watchedDate.setEnabled(false);
                    mBinding.place.setEnabled(false);
                    mBinding.friendsName.setEnabled(false);

                    ContentValues contentValue = new ContentValues();
                    contentValue.put(MovieContract.WatchHistory.COLUMN_MULTIPLEX_NAME, mBinding.multiplexName.getText().toString());
                    contentValue.put(MovieContract.WatchHistory.COLUMN_DATE, mBinding.watchedDate.getText().toString());
                    contentValue.put(MovieContract.WatchHistory.COLUMN_PLACE, mBinding.place.getText().toString());
                    contentValue.put(MovieContract.WatchHistory.COLUMN_FRIENDS, mBinding.friendsName.getText().toString());
                    contentValue.put(MovieContract.WatchHistory.COLUMN_MOVIE_ID, mMovieId);

                    insertOrUpdateData(contentValue);
                } else {
                    mBinding.editSaveButton.setImageResource(android.R.drawable.ic_menu_save);
                    mBinding.multiplexName.setEnabled(true);
                    mBinding.watchedDate.setEnabled(true);
                    mBinding.place.setEnabled(true);
                    mBinding.friendsName.setEnabled(true);
                }
                break;
            case R.id.delete_button:
                final WatchHistoryDeleteConfirmationFragment confirmationFragment = WatchHistoryDeleteConfirmationFragment.getInstance(mMovieId);
                confirmationFragment.setListener(this);
                confirmationFragment.show(getChildFragmentManager(), "");
                break;
        }
    }

    private void insertOrUpdateData(final ContentValues contentValue) {
        final CustomAsyncQueryHandler queryHandler = new CustomAsyncQueryHandler(getActivity().getContentResolver());

        String[] selectionArgs = null;
        String selection = null;
        if (mMovieId != null) {
            selection = MovieContract.WatchHistory.TABLE_NAME + "." + MovieContract.WatchHistory.COLUMN_MOVIE_ID + "=?";
            selectionArgs = new String[]{mMovieId};
        }
        queryHandler.setAsyncUpdateListener(new CustomAsyncQueryHandler.AsyncUpdateListener() {
            @Override
            public void onUpdateComplete(int token, Object cookie, int result) {
                if (result == 0) {
                    queryHandler.startInsert(1, null, MovieContract.WatchHistory.CONTENT_URI, contentValue);
                }
            }
        });
        queryHandler.startUpdate(1, null, MovieContract.WatchHistory.CONTENT_URI, contentValue, selection, selectionArgs);
    }

    @Override
    public void OnDelete() {
        dismiss();
    }
}
