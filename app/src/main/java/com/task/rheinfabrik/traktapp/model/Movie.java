package com.task.rheinfabrik.traktapp.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A Movie represents a movie in the trakt.tv database, including information as title and year.
 */
public class Movie implements IMovie{

    /**
     * The year of production.
     */
    private int mYear;

    /**
     * The title of the movie.
     */
    private String mTitle;

    /**
     * The plot overview.
     */
    private String mOverview;

    /**
     * The poster URL.
     */
    private URL mImageUrl;

    /**
     * The trakt ID.
     */
    private int mID = -1;

    /**
     * False, if this movie has no poster attached.
     */
    private boolean mHasImage = false;

    /**
     * False, if this movie has no year attached.
     */
    private boolean mHasYear = false;

    /**
     * False, if this movie has no ID attached.
     */
    private boolean mHasID = false;



    /**
     * Constructs a Movie.
     *
     * @param id The trakt ID of this movie. The String needs to contain a valid integer.
     * @param title The title of this movie.
     */
    public Movie(String id, String title){
        try{
            this.mID = Integer.parseInt(id);
            this.mHasID = true;
        }catch(NumberFormatException e)
        {
            //Do nothing since everything remains as initialized
        }

        this.mTitle = title;
        this.mYear = -1;
    }


    /**
     * Sets the URL pointing to the movie poster.
     *
     * @param imageUrl The URL as a String. The URL is only set if it is in a valid format,
     *                 otherwise this movie still has no image attached.
     */
    public void setImageUrl(String imageUrl)
    {
        try{
            this.mImageUrl = new URL(imageUrl);
            this.mHasImage = true;

        }catch(MalformedURLException e)
        {
            this.mHasImage = false;
        }
    }


    /**
     * Sets the production year of this movie by using a String.
     *
     * @param yearString A String that contains the production year. If the String is not valid,
     *                   the year is reset.
     */
    public void setYear(String yearString)
    {
        try
        {
            //try to parse the String to a production year
            //TODO: actually check if the String really contains not only a number but a year
            this.mYear = Integer.parseInt(yearString);
            this.mHasYear = true;

        }catch(NumberFormatException nfe)
        {
            //If the String does not contain a year, reset the year
            this.mYear = -1;
            this.mHasYear = false;
        }

    }

    /**
     * Set the plot overview of this movie.
     *
     * @param mOverview The plot overview as a String.
     */
    public void setOverview(String mOverview)
    {
        this.mOverview = mOverview;
    }

    @Override
    public boolean hasImage()
    {
        return this.mHasImage;
    }

    @Override
    public int getYear() {
        return mYear;
    }

    @Override
    public URL getImageUrl() {
        return mImageUrl;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getOverview() {
        return mOverview;
    }

    @Override
    public boolean hasYear()
    {
        return this.mHasYear;
    }

    @Override
    public int getID()
    {
        return this.mID;
    }

    @Override
    public boolean hasID()
    {
        return this.mHasID;
    }

}
