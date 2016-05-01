package com.task.rheinfabrik.traktapp.model;

import java.net.URL;


/**
 * Provides an interface for the movie data model.
 */
public interface IMovie
{

    /**
     * Indicates whether this movie has an image attached or not.
     *
     * @return True, if this movie has an image attached.
     */
    boolean hasImage();

    /**
     * Indicates whether this movie has a production year attached or not.
     *
     * @return True, if this movie has a production year attached.
     */
    boolean hasYear();

    /**
     * Indicates whether this movie has a trakt ID attached or not.
     *
     * @return True, if this movie has a trakt ID attached.
     */
    boolean hasID();

    /**
     * Returns the URL to a movie poster that has been attached to this movie in the database.
     *
     * @return Returns the image URL.
     */
    URL getImageUrl();


    /**
     * Returns the title of the movie.
     *
     * @return The movie title as a String.
     */
    String getTitle();

    /**
     * Returns the production year of the movie.
     *
     * @return Returns the year as integer.
     */
    int getYear();

    /**
     * Returns the plot overview of the movie.
     *
     * @return The plot overview as a String.
     */
    String getOverview();

    /**
     * Returns the trakt ID of the movie.
     *
     * @return The trakt ID as an integer.
     */
    int getID();

}
