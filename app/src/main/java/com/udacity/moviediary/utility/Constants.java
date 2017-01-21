package com.udacity.moviediary.utility;

/**
 * Created by Amardeep on 29/2/16.
 */
public class Constants {


    public interface BundleKeys {
        String ID = "com.udacity.moviediary." + "id";
        String SORT_PREFERENCE = "com.udacity.moviediary." + "sort_preference";
        String TAB_NAME =  "com.udacity.moviediary." +  "tab_name";
        String IS_FAVOURITE_MODE = "com.udacity.moviediary." + "is_fav";
    }

    public interface SortPreference {
        int SORT_BY_POPULARITY = 1001;
        int SORT_BY_RELEASE_DATE = 1002;
        int SORT_BY_RATING = 1003;
        int SORT_BY_NAME = 1004;
    }

    public interface AnalyticsKeys {
        String LOAD_MORE_MOVIE = "load_more_movie";
        String LOAD_MOVIE_DETAILS = "load_movie_details";
        String MOVIE_DETAILS_FAV_CLICKED = "movie_details_fav_clicked";
        String PAGE_NUMBER = "page_number";
        String MOVIE_ID = "movie_id";
        String IS_FAVOURITE = "is_favourite";
        String WATCH_HISTORY_CLICKED = "watch_history_clicked";
        String TRAILER_CLICKED = "trailer_clicked";
        String WATCH_HISTORY_DELETED = "watch_history_deleted";
    }

    public static final String PREV_SELECTION = "prev_selection";
}
