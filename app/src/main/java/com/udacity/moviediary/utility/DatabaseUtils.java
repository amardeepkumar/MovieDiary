package com.udacity.moviediary.utility;

import android.content.ContentValues;
import android.content.Context;

import com.udacity.moviediary.data.CustomAsyncQueryHandler;
import com.udacity.moviediary.data.MovieContract;

/**
 * Created by Amardeep on 12/12/2016.
 */
public class DatabaseUtils {

    public static void setFavourite(Context context, String movieId, int favoriteValue) {
        CustomAsyncQueryHandler queryHandler = new CustomAsyncQueryHandler(context.getContentResolver());
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_FAVOURITE, favoriteValue);

        queryHandler.startUpdate(1, null, MovieContract.MovieEntry.CONTENT_URI,
                values, MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{movieId});
    }
}
