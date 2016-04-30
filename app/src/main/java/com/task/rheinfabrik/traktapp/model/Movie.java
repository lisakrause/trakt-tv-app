package com.task.rheinfabrik.traktapp.model;

/**
 * TODO
 */
public class Movie {

    private int mYear;
    private String mTitle;
    private String mOverview;
    private String mImageUrl;

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    public String getImageUrl() {
        return mImageUrl;
    }



    public Movie(String title, int year){
        mTitle = title;
        mYear = year;
    }


    public String getTitle() {
        return mTitle;
    }

    public int getYear() {
        return mYear;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }


}
