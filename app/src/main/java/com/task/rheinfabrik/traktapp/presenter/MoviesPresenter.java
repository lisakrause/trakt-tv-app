package com.task.rheinfabrik.traktapp.presenter;

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

    @Override
    public void onCreate() {
        //TODO
    }

    @Override
    public void onStart() {
        //TODO
    }

    @Override
    public void onStop() {
        //TODO
    }

    @Override
    public void onPause() {
        //TODO
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
        //---indicate loading the view
        this.mMoviesView.showLoading();

        //---download into data model
        GetPopularMoviesTask popularTask = new GetPopularMoviesTask(this);
        popularTask.execute();
    }

    /**
     * Receives a list of popular movies to show them in the view.
     *
     * @param popularMovies The list of the 10 most popular movies.
     */
    public void receivePopularMovies(final List<IMovie> popularMovies)
    {
        this.mMoviesView.showPopularMovies(popularMovies);
    }
}
