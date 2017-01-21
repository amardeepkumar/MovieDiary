package com.udacity.moviediary.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.udacity.moviediary.R;
import com.udacity.moviediary.data.CustomAsyncQueryHandler;
import com.udacity.moviediary.data.MovieContract;
import com.udacity.moviediary.utility.Constants;

/**
 * Created by Amardeep Kumar on 1/21/2017.
 */

public class WatchHistoryDeleteConfirmationFragment extends DialogFragment {

    private OnDeleteListener mOnDeleteListener;
    public interface OnDeleteListener {
        void OnDelete();
    }
    private String mMovieId;

    public static WatchHistoryDeleteConfirmationFragment getInstance(String movieId) {
        WatchHistoryDeleteConfirmationFragment deleteConfirmationFragment = new WatchHistoryDeleteConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKeys.ID, movieId);
        deleteConfirmationFragment.setArguments(bundle);
        return deleteConfirmationFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getString(Constants.BundleKeys.ID, null);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_confirmation)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteWatchList();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    private void deleteWatchList() {
        final CustomAsyncQueryHandler queryHandler = new CustomAsyncQueryHandler(getActivity().getContentResolver());
        queryHandler.setAsyncDeleteListener(new CustomAsyncQueryHandler.AsyncDeleteListener() {
            @Override
            public void onDeleteComplete(int token, Object cookie, int result) {
                dismiss();
                mOnDeleteListener.OnDelete();
            }
        });
        String[] selectionArgs = null;
        String selection = null;
        if (mMovieId != null) {
            selection = MovieContract.WatchHistory.TABLE_NAME + "." + MovieContract.WatchHistory.COLUMN_MOVIE_ID + "=?";
            selectionArgs = new String[]{mMovieId};
        }
        queryHandler.startDelete(1, null, MovieContract.WatchHistory.CONTENT_URI, selection, selectionArgs);
    }

    public void setListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }
}
