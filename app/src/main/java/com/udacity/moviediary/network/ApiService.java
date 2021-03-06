package com.udacity.moviediary.network;

import com.udacity.moviediary.model.response.DiscoverMovieResponse;
import com.udacity.moviediary.model.response.MovieDetailResponse;
import com.udacity.moviediary.model.response.MovieReviewResponse;
import com.udacity.moviediary.model.response.MovieVideoResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Amardeep on 12/12/2016.
 */
public interface ApiService {

    @GET("/3/discover/movie")
    Call<DiscoverMovieResponse> requestMovies(@QueryMap Map<String, String> options);

    @GET("/3/search/movie")
    Call<DiscoverMovieResponse> searchMovies(@QueryMap Map<String, String> queryMap);

    @GET("/3/movie/{id}")
    Call<MovieDetailResponse> requestMovieDetail(@Path(Config.UrlConstants.ID) String movieId,
                                                 @Query(Config.UrlConstants.API_KEY) String apiKey);

    @GET("/3/movie/{id}/videos")
    Call<MovieVideoResponse> requestMovieTrailers(@Path(Config.UrlConstants.ID) String movieId,
                                                @Query(Config.UrlConstants.API_KEY) String apiKey);

    @GET("/3/movie/{id}/reviews")
    Call<MovieReviewResponse> requestMovieReviews(@Path(Config.UrlConstants.ID) String movieId,
                                                  @Query(Config.UrlConstants.API_KEY) String apiKey);
}
