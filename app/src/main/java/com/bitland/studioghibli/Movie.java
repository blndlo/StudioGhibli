package com.bitland.studioghibli;

public class Movie {

    private String mTitle;
    private String mDescription;
    private String mProducer;
    private String mDirector;
    private int mYearOfRelease;
    private int mRatings;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getProducer() {
        return mProducer;
    }

    public void setProducer(String producer) {
        mProducer = producer;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setDirector(String director) {
        mDirector = director;
    }

    public int getYearOfRelease() {
        return mYearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        mYearOfRelease = yearOfRelease;
    }

    public int getRatings() {
        return mRatings;
    }

    public void setRatings(int ratings) {
        mRatings = ratings;
    }
}
