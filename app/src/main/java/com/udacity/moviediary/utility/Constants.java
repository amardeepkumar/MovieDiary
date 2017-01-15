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
        int SORT_BY_VOTE_AVG = 1002;
//        int SORT_BY_FAVOURITE = 1003;
    }

    public static final String PREV_SELECTION = "prev_selection";
}
