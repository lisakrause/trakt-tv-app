package com.task.rheinfabrik.traktapp.network;

import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SearchForMoviesRequester downloads movies from the database depending on a search query that was
 * entered by the user.
 */
public class SearchForMoviesRequester
{

    /**
     * The parameter that is used in the URL to set the type that is searched for,
     * e.g. movies in this case.
     */
    private final static String TYPE_PARAM = "type";

    /**
     * The parameter that is used in the URL to set the search query.
     */
    private final static String QUERY_PARAM = "query";

    /**
     * The parameter that is used in the URL to set the pagination.
     */
    private final static String PAGE_PARAM = "page";

    /**
     * Used to set the type of data that is looked up by this search.
     * This app can only be used to search movies.
     */
    private final static String MOVIE_TYPE = "movie";

    /**
     * The character set that is used to encode the search query that was entered by the user.
     */
    private final static String CHAR_SET = "UTF-8";


    /**
     * Retrieves the search result from the trakt.tv database.
     *
     * @param searchQuery The search query that is used to perform the search.
     * @param page The page of search results that shall be downloaded.
     * @return The search result as a list of movies.
     */
    public static List<IMovie> getSearchResults(String searchQuery, int page)
    {
        //(found out later that there might be a possibility to use Jackson and
        //Spring to do the HTTP connection and the JSON parsing but since it works
        //currently, I left the code like this)

        try
        {
            //-------------------download------------------------

            //setup parameters for search
            HashMap<String, String> parameters = new HashMap<String, String>();

            parameters.put(TYPE_PARAM, MOVIE_TYPE);
            parameters.put(QUERY_PARAM, URLEncoder.encode(searchQuery, CHAR_SET));
            parameters.put(PAGE_PARAM, "" + page);

            //download
            String movies = TraktConnector.getMoviesFromTrakt(TraktConstants.SEARCH_URL, parameters);

            //--------------------parse---------------------------

            //Get the array of movies from the response body
            JSONArray jsonArray = new JSONArray(movies);

            //init
            List<IMovie> moviesList = new ArrayList<IMovie>();

            //iterate through array to get movie information
            for(int i = 0; i < jsonArray.length(); i++)
            {
                //get search result from the array
                JSONObject metaMovieObject = jsonArray.getJSONObject(i);

                //get concrete movie
                JSONObject movieObject = metaMovieObject.getJSONObject(MOVIE_TYPE);

                //parse information...
                String title = movieObject.getString(TraktConstants.MOVIE_TITLE);
                String yearString = movieObject.getString(TraktConstants.MOVIE_YEAR);
                String traktID = "";

                if(movieObject.has(TraktConstants.MOVIE_ID_OVERVIEW))
                {
                    JSONObject idsObject = movieObject.getJSONObject(TraktConstants.MOVIE_ID_OVERVIEW);
                    if(idsObject.has(TraktConstants.MOVIE_TRAKT_ID))
                    {
                        traktID = idsObject.getString(TraktConstants.MOVIE_TRAKT_ID);
                    }
                }

                String overview = "";
                if(movieObject.has(TraktConstants.MOVIE_OVERVIEW))
                {
                    overview = movieObject.getString(TraktConstants.MOVIE_OVERVIEW);
                }

                JSONObject imageOverviewArray =
                        movieObject.getJSONObject(TraktConstants.MOVIE_IMAGE_OVERVIEW);
                JSONObject imageObject = imageOverviewArray.getJSONObject(TraktConstants.MOVIE_IMAGE);

                String imageUrl = imageObject.getString(TraktConstants.MOVIE_IMAGE_SIZE);

                //...and add it to the data model
                Movie movie = new Movie(traktID, title);
                movie.setYear(yearString);
                movie.setOverview(overview);
                movie.setImageUrl(imageUrl);

                moviesList.add(movie);
            }

            //return search result
            return moviesList;

        } catch (Exception e) {
            //do nothing, since everything is handled in the code below
        }

        //TODO: show the user some information when something went wrong while fetching the movies
        return null;
    }
}
