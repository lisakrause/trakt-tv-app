package com.task.rheinfabrik.traktapp.network;

/**
 * The constants that are used when performing requests to the trakt.tv REST service.
 */
public final class TraktConstants
{
    /**
     * The overview of ids of the movie, used when parsing the response body.
     */
    final static String MOVIE_ID_OVERVIEW = "ids";

    /**
     * The trakt ID of the movie, used when parsing the response body.
     */
    final static String MOVIE_TRAKT_ID = "trakt";

    /**
     * The movie title, used when parsing the response body.
     */
    final static String MOVIE_TITLE = "title";

    /**
     * The production year of the movie, used when parsing the response body.
     */
    final static String MOVIE_YEAR = "year";

    /**
     * The plot overview of the movie, used when parsing the response body.
     */
    final static String MOVIE_OVERVIEW = "overview";

    /**
     * The name of the array that holds all images associated with a movie,
     * used when parsing the response body.
     */
    final static String MOVIE_IMAGE_OVERVIEW = "images";

    /**
     * The image of the movie, used when parsing the response body.
     */
    final static String MOVIE_IMAGE = "poster";

    /**
     * The size of the movie image, in this case a medium one.
     */
    final static String MOVIE_IMAGE_SIZE = "medium";

    /**
     * The URL that is used to query the trakt.tv database in JSON format.
     */
    final static String SEARCH_URL = "https://api-v2launch.trakt.tv/search.json";

}
