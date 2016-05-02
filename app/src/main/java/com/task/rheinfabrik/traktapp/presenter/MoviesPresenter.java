package com.task.rheinfabrik.traktapp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.network.GetPopularMoviesTask;
import com.task.rheinfabrik.traktapp.view.IMoviesView;

import java.util.List;

/**
 * MoviesPresenter represents the Presenter of this app, responsible for connecting view and model
 * and
 */
public class MoviesPresenter implements IPresenter<IMoviesView>
{
    /**
     * The view of this app.
     */
    private IMoviesView mMoviesView;

    /**
     * The context of this app.
     */
    private Context mContext;

    /**
     * Constructs a MoviesPresenter.
     *
     * @param context The context of this app.
     */
    public MoviesPresenter(Context context)
    {
        this.mContext = context;
    }

    @Override
    public void attachView(final IMoviesView view)
    {
        this.mMoviesView = view;
    }


    /**
     * Setups a download to get the 10 most popular movies and shows a spinner that indicates
     * loading(in the view) in the meantime.
     */
    public void getPopularMovies()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            //---indicate loading the view
            this.mMoviesView.showLoading();

            //---download into data model
            GetPopularMoviesTask popularTask = new GetPopularMoviesTask(this);
            popularTask.execute();
        } else {

            //show to the user that there is no network connection
            this.mMoviesView.showError();
        }

    }


    /**
     * Receives a list of popular movies to show them in the view.
     *
     * @param popularMovies The list of the 10 most popular movies.
     */
    public void receivePopularMovies(final List<IMovie> popularMovies)
    {
        this.mMoviesView.addPopularMovies(popularMovies);
    }
}
