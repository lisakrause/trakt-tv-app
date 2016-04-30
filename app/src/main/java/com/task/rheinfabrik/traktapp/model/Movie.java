package com.task.rheinfabrik.traktapp.model;

/**
 * TODO
 */
public class Movie {

    private int mYear;
    private String mTitle;
    private String mOverview;
    private String mImageUrl;
    private boolean mHasImage = false;


    public Movie(String title){
        mTitle = title;
    }

    public boolean hasImage()
    {
        return this.mHasImage;
    }

    public void setImageUrl(String imageUrl)
    {
        if(imageUrl.compareTo("null") != 0)
        {
            this.mImageUrl = imageUrl;
            this.mHasImage = true;
        }
    }


    public String getImageUrl() {
        return mImageUrl;
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

    public void setYear(String yearString)
    {
        try
        {
            this.mYear = Integer.parseInt(yearString);

        }catch(NumberFormatException nfe)
        {
            this.mYear = 0;
        }

    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }


}
