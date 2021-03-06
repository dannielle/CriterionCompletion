package com.dee.android.criterioncompletion;

import java.util.UUID;

public class Film {

    private UUID mId;
    private String mTitle;
    private float mRating;
    private boolean mHasWatched;
    private boolean mIsFavorite;
    private String mCountry;
    private String mDirector;
    private String mYear;

    public Film() {
        mId = UUID.randomUUID();
    }

    public Film(UUID uuid) {
        mId = uuid;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        this.mRating = rating;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public boolean hasWatched() {
        return mHasWatched;
    }

    public void setHasWatched(boolean hasWatched) {
        this.mHasWatched = hasWatched;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setDirector(String director) {
        mDirector = director;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getYear() {
        return mYear;
    }
}
