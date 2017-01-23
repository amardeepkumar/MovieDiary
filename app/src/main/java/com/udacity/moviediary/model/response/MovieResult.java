package com.udacity.moviediary.model.response;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.udacity.moviediary.BR;

import java.util.List;

public class MovieResult extends BaseObservable implements Parcelable {
    @Bindable
    private transient boolean selected;
    @Bindable
    private transient boolean favourite;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean mIsAdult;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("genre_ids")
    private List<Integer> mGenreIds;
    @SerializedName("id")
    private String mId;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("popularity")
    private float mPopularity;
    @SerializedName("vote_count")
    private int mVoteCount;
    @SerializedName("video")
    private boolean mIsVideo;
    @SerializedName("vote_average")
    private float mVoteAverage;

    public MovieResult() {

    }

    protected MovieResult(Parcel in) {
        posterPath = in.readString();
        mIsAdult = in.readByte() != 0;
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mId = in.readString();
        mOriginalTitle = in.readString();
        mOriginalLanguage = in.readString();
        mTitle = in.readString();
        mBackdropPath = in.readString();
        mPopularity = in.readFloat();
        mVoteCount = in.readInt();
        mIsVideo = in.readByte() != 0;
        mVoteAverage = in.readFloat();
    }

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel in) {
            return new MovieResult(in);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
        notifyPropertyChanged(BR.selected);
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
        notifyPropertyChanged(BR.favourite);
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.posterPath = mPosterPath;
    }

    public boolean getAdult() {
        return mIsAdult;
    }

    public void setAdult(boolean mAdult) {
        this.mIsAdult = mAdult;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Integer> mGenreIds) {
        this.mGenreIds = mGenreIds;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(float mPopularity) {
        this.mPopularity = mPopularity;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public boolean getVideo() {
        return mIsVideo;
    }

    public void setVideo(boolean mVideo) {
        this.mIsVideo = mVideo;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeByte((byte) (mIsAdult ? 1 : 0));
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mId);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mOriginalLanguage);
        parcel.writeString(mTitle);
        parcel.writeString(mBackdropPath);
        parcel.writeFloat(mPopularity);
        parcel.writeInt(mVoteCount);
        parcel.writeByte((byte) (mIsVideo ? 1 : 0));
        parcel.writeFloat(mVoteAverage);
    }
}
