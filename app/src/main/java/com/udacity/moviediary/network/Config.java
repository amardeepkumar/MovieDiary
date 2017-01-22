package com.udacity.moviediary.network;

/**
 * Created by Amardeep on 12/12/2016.
 */
public class Config {
    public interface UrlConstants {
        String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
        String REQUEST_BASE_URL = "http://api.themoviedb.org/";


        String SORT_BY = "sort_by";
        String API_KEY = "api_key";
        String PAGE = "page";
        String SORT_POPULARITY_DESC = "popularity.desc";
        String SORT_RELEASE_DATE_DESC = "release_date.desc";
        String SORT_VOTE_AVERAGE_DESC = "vote_average.desc";
        String SORT_NAME_ASC = "original_title.asc";
        String ID = "id";
        String QUERY = "query";
    }
}
