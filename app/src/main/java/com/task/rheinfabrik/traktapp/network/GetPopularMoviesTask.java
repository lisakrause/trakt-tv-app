package com.task.rheinfabrik.traktapp.network;


import android.os.AsyncTask;

import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.model.Movie;
import com.task.rheinfabrik.traktapp.presenter.MoviesPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * GetPopularMoviesTask downloads the 10 most popular movies from the trakt.tv
 * database asynchronously.
 */
public class GetPopularMoviesTask extends AsyncTask<Void, Void, List<IMovie>>
{
    /**
     * The presenter of this app to connect to the UI.
     */
    private MoviesPresenter mPresenter;

    /**
     * The URL that is used to query the trakt.tv database in JSON format.
     */
    final static String MOVIES_URL = "https://api-v2launch.trakt.tv/movies/popular.json";

    /**
     * The movie title, used when parsing the response body.
     */
    final static String MOVIE_TITLE = "title";

    /**
     * The production year of the movie, used when parsing the response body.
     */
    final static String MOVIE_YEAR = "year";


    /**
     * Constructs a PopularMoviesTask.
     *
     * @param presenter The presenter that expects the download results to pass it to the UI.
     */
    public GetPopularMoviesTask(MoviesPresenter presenter)
    {
        mPresenter = presenter;
    }


    @Override
    protected List<IMovie> doInBackground(Void...params)
    {
        try {

            //--------------------download-----------------

            //initialize connection to the database and pass URL
            //that is used to query it
            String movies = TraktConnector.getMoviesFromTrakt(MOVIES_URL, null);


            //--------------------parse--------------------

            //Get the array of movies from the response body
            JSONArray jsonArray = new JSONArray(movies);

            //init list for movie data
            List<IMovie> moviesList = new ArrayList<IMovie>();

            //iterate through array to get movie information
            for(int i = 0; i < jsonArray.length(); i++)
            {
                //get concrete movie from the array
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //parse information...
                String title = jsonObject.getString(MOVIE_TITLE);
                String year = jsonObject.getString(MOVIE_YEAR);

                //TODO: get extended info with image and overview

                //...into the data model
                Movie movie = new Movie(title);
                movie.setYear(year);

                moviesList.add(movie);
            }

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
            mPresenter.receivePopularMovies(movies);
        }

    }

}
