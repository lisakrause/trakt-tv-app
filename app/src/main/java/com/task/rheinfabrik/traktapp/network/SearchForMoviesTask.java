package com.task.rheinfabrik.traktapp.network;


import android.os.AsyncTask;
import android.util.Log;

import com.task.rheinfabrik.traktapp.model.Movie;
import com.task.rheinfabrik.traktapp.presenter.MoviesPresenter;
import com.task.rheinfabrik.traktapp.presenter.Presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lisa on 29.04.2016.
 */
public class SearchForMoviesTask extends AsyncTask<MoviesPresenter, Void, List<Movie>>
{
    private MoviesPresenter mPresenter;

    final static String SEARCH_URL = "https://api-v2launch.trakt.tv/search.json";

    final static String TYPE_PARAM = "type";
    final static String QUERY_PARAM = "query";

    final static String MOVIE_TYPE = "movie";

    final static String MOVIE_TITLE = "title";
    final static String MOVIE_YEAR = "year";
    final static String MOVIE_OVERVIEW = "overview";


    @Override
    protected List<Movie> doInBackground(MoviesPresenter... presenter)
    {
        this.mPresenter = presenter[0];


        try {
            TraktConnector connector = new TraktConnector();

            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put(TYPE_PARAM, MOVIE_TYPE);
            //TODO
            parameters.put(QUERY_PARAM, "batman");

            String movies = connector.getMoviesFromTrakt(SEARCH_URL, parameters);


            JSONArray jsonArray = new JSONArray(movies);

            List<Movie> moviesList = new ArrayList<Movie>();

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject metaMovieObject = jsonArray.getJSONObject(i);

                JSONObject movieObject = metaMovieObject.getJSONObject(MOVIE_TYPE);
                String title = movieObject.getString(MOVIE_TITLE);
                String year = movieObject.getString(MOVIE_YEAR);
                String overview = movieObject.getString(MOVIE_OVERVIEW);

                Movie movie = new Movie(title, Integer.parseInt(year));
                movie.setOverview(overview);

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
            String message = e.getMessage();
            //TODO
        } catch (JSONException e) {
            // response body is no valid JSON string
            //TODO
        }

        //TODO
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies)
    {

        mPresenter.receiveFoundMoviesList(movies);
    }

}
