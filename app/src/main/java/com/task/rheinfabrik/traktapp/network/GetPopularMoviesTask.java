package com.task.rheinfabrik.traktapp.network;


import android.os.AsyncTask;

import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.model.Movie;
import com.task.rheinfabrik.traktapp.presenter.MoviesPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    private final static String MOVIES_URL = "https://api-v2launch.trakt.tv/movies/popular.json";

    /**
     * Used to set the type of id that is looked up when searching for more information on a movie.
     */
    final static String ID_TYPE_PARAM = "id_type";

    /**
     * Used to set the id that is looked up when searching for more information on a movie.
     */
    final static String ID_PARAM = "id";

    /**
     * Used to set the type of id that is looked up by this search.
     * This task only searches for trakt IDs.
     */
    final static String ID_TYPE = "trakt-movie";

    /**
     * The name of the JSON object of the movie, used when parsing the response body.
     */
    final static String ACTUAL_MOVIE = "movie";

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
        //(found out later that there might be a possibility to use Jackson and
        //Spring to do the HTTP connection and the JSON parsing but since it works
        //currently, I left the code like this)

        //TODO: show the user some information when something went wrong while fetching the movies

        try {

            //--------------------download-----------------

            //initialize connection to the database and pass URL
            //that is used to query it
            HashMap<String, String> parameters = new HashMap<String, String>();
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

                //init parsing
                String traktID = "";
                String title = "";
                String year = "";
                String overview = "";
                String imageURL = "";

                if(jsonObject.has(TraktConstants.MOVIE_ID_OVERVIEW))
                {
                    JSONObject idsObject = jsonObject.getJSONObject(TraktConstants.MOVIE_ID_OVERVIEW);
                    if(idsObject.has(TraktConstants.MOVIE_TRAKT_ID))
                    {
                         traktID = idsObject.getString(TraktConstants.MOVIE_TRAKT_ID);
                    }
                }

                if(jsonObject.has(TraktConstants.MOVIE_TITLE))
                {
                    title = jsonObject.getString(TraktConstants.MOVIE_TITLE);
                }

                if(jsonObject.has(TraktConstants.MOVIE_YEAR))
                {
                    year = jsonObject.getString(TraktConstants.MOVIE_YEAR);
                }

                //request more information (overview, image) if an ID is given
                if(!traktID.isEmpty())
                {
                    //setup parameters for ID search
                    HashMap<String, String> searchParameters = new HashMap<String, String>();

                    parameters.put(ID_TYPE_PARAM, ID_TYPE);
                    parameters.put(ID_PARAM, traktID);

                    //download
                    String movieInformation =
                            TraktConnector.getMoviesFromTrakt(TraktConstants.SEARCH_URL, parameters);


                    //parsing
                    JSONArray moviesObject = new JSONArray(movieInformation);

                    //check if there actually has been a movie found for this ID
                    if(moviesObject.length() == 1)
                    {
                        JSONObject movieObject = moviesObject.getJSONObject(0);

                        if(movieObject.has(ACTUAL_MOVIE))
                        {
                            JSONObject infoObject = movieObject.getJSONObject(ACTUAL_MOVIE);

                            //get overview
                            if(infoObject.has(TraktConstants.MOVIE_OVERVIEW))
                            {
                                overview = infoObject.getString(TraktConstants.MOVIE_OVERVIEW);
                            }

                            //traverse through JSON objects...
                            if(infoObject.has(TraktConstants.MOVIE_IMAGE_OVERVIEW))
                            {
                                JSONObject imageOverviewArray =
                                        infoObject.getJSONObject(TraktConstants.MOVIE_IMAGE_OVERVIEW);

                                if(imageOverviewArray.has(TraktConstants.MOVIE_IMAGE))
                                {
                                    JSONObject imageObject =
                                            imageOverviewArray.getJSONObject(TraktConstants.MOVIE_IMAGE);

                                    if(imageObject.has(TraktConstants.MOVIE_IMAGE_SIZE))
                                    {
                                        //...until we find the image URL
                                        imageURL = imageObject.getString(TraktConstants.MOVIE_IMAGE_SIZE);
                                    }


                                }

                            }


                        }


                    }


                }


                //put all information into the data model
                Movie movie = new Movie(traktID, title);
                movie.setYear(year);
                movie.setOverview(overview);
                movie.setImageUrl(imageURL);

                moviesList.add(movie);
            }

            return moviesList;

        } catch (Exception e) {
            //do nothing, since everything is handled in the code below
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
