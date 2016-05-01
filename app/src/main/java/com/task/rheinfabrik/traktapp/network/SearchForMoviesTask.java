package com.task.rheinfabrik.traktapp.network;


import android.os.AsyncTask;

import com.task.rheinfabrik.traktapp.model.Movie;
import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.presenter.MoviesPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SearchForMoviesTask downloads movies from the database depending on a search query that was
 * entered by the user.
 */
public class SearchForMoviesTask extends AsyncTask<Void, Void, List<IMovie>>
{

    /**
     * The presenter of this app to connect to the UI.
     */
    private MoviesPresenter mPresenter;

    /**
     * The query that was entered by the user to search for movies.
     */
    private String mSearchQuery;

    /**
     * The URL that is used to query the trakt.tv database in JSON format.
     */
    final static String SEARCH_URL = "https://api-v2launch.trakt.tv/search.json";

    /**
     * The parameter that is used in the URL to set the type that is searched for,
     * e.g. movies in this case.
     */
    final static String TYPE_PARAM = "type";

    /**
     * The parameter that is used in the URL to set the search query.
     */
    final static String QUERY_PARAM = "query";

    /**
     * The parameter that is used in the URL to set the pagination.
     */
    final static String PAGE_PARAM = "page";

    /**
     * Used to set the type of data that is looked up by this search.
     * This app can only be used to search movies.
     */
    final static String MOVIE_TYPE = "movie";

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
     * The character set that is used to encode the search query that was entered by the user.
     */
    final static String CHAR_SET = "UTF-8";


    /**
     * Constructs a SearchForMoviesTask.
     *
     * @param presenter The presenter that expects the download results to pass it to the UI.
     * @param searchQuery The search query that was entered by the user.
     */
    public SearchForMoviesTask(MoviesPresenter presenter, String searchQuery)
    {
        this.mPresenter = presenter;
        this.mSearchQuery = searchQuery;
    }


    @Override
    protected List<IMovie> doInBackground(Void...params)
    {

        try
        {
            //-------------------download------------------------

            //setup parameters for search
            HashMap<String, String> parameters = new HashMap<String, String>();

            parameters.put(TYPE_PARAM, MOVIE_TYPE);
            parameters.put(QUERY_PARAM, URLEncoder.encode(this.mSearchQuery, CHAR_SET));
            //TODO: Update page by scrolling
            parameters.put(PAGE_PARAM, "1");

            //if this task has been cancelled in the meantime,...
            if(isCancelled())
            {
                //...stop here and do not download anything
               return null;
            }

            //download
            String movies = TraktConnector.getMoviesFromTrakt(SEARCH_URL, parameters);

            //--------------------parse---------------------------

            //Get the array of movies from the response body
            JSONArray jsonArray = new JSONArray(movies);

            //init
            List<IMovie> moviesList = new ArrayList<IMovie>();

            //iterate through array to get movie information
            for(int i = 0; i < jsonArray.length(); i++)
            {
                //Every time we parse one movie,
                //we check if this task has been cancelled in the meantime
                if(isCancelled())
                {
                    //and stop parsing immediately
                    return null;
                }

                //get search result from the array
                JSONObject metaMovieObject = jsonArray.getJSONObject(i);

                //get concrete movie
                JSONObject movieObject = metaMovieObject.getJSONObject(MOVIE_TYPE);

                //parse information...
                String title = movieObject.getString(MOVIE_TITLE);
                String yearString = movieObject.getString(MOVIE_YEAR);

                String overview = "";
                if(movieObject.has(MOVIE_OVERVIEW))
                {
                    overview = movieObject.getString(MOVIE_OVERVIEW);
                }

                JSONObject imageOverviewArray = movieObject.getJSONObject(MOVIE_IMAGE_OVERVIEW);
                JSONObject imageObject = imageOverviewArray.getJSONObject(MOVIE_IMAGE);

                String imageUrl = imageObject.getString(MOVIE_IMAGE_SIZE);

                //...and add it to the data model
                Movie movie = new Movie(title);
                movie.setYear(yearString);
                movie.setOverview(overview);
                movie.setImageUrl(imageUrl);

                moviesList.add(movie);
            }

            //return search result
            return moviesList;

        } catch (MalformedURLException e) {
            // URL is invalid
            //TODO
        } catch (SocketTimeoutException e) {
            // data retrieval or connection timed out
            //TODO
        } catch (IOException e) {
            // could not read response body
            // (could not create input stream)
            //TODO
        } catch (JSONException e) {
            // response body is no valid JSON string
            //TODO
        }

        //cancel this task in case of failure to be sure that onPostExecute() is not called
        cancel(true);
        return null;
    }

    @Override
    protected void onPostExecute(List<IMovie> movies)
    {
        //Just to be sure even though this should never be reached when this
        //task has been cancelled
        if(!isCancelled())
        {
            mPresenter.receiveFoundMoviesList(movies);
        }

    }

}
