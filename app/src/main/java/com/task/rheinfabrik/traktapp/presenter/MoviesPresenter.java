package com.task.rheinfabrik.traktapp.presenter;

import com.task.rheinfabrik.traktapp.model.Movie;
import com.task.rheinfabrik.traktapp.network.GetPopularMoviesTask;
import com.task.rheinfabrik.traktapp.network.SearchForMoviesTask;
import com.task.rheinfabrik.traktapp.view.MoviesView;

import java.util.List;

/**
 * Created by lisa on 28.04.2016.
 */
public class MoviesPresenter implements Presenter<MoviesView>
{
    private MoviesView mMoviesView;

    private SearchForMoviesTask mSearchTask;

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
    public void attachView(MoviesView view)
    {
        this.mMoviesView = view;
    }


    public void getPopularMovies()
    {
        this.mMoviesView.showLoading();

        GetPopularMoviesTask popularTask = new GetPopularMoviesTask(this);

        popularTask.execute();
    }

    public void receivePopularMovies(List<Movie> popularMovies)
    {
        this.mMoviesView.showPopularMovies(popularMovies);
    }

    public void searchMovies(String searchText)
    {
        this.mMoviesView.showLoading();

        if(this.mSearchTask !=  null)
        {
            this.mSearchTask.cancel(true);
        }

        this.mSearchTask = new SearchForMoviesTask(this, searchText);

        this.mSearchTask.execute();
    }

    public void receiveFoundMoviesList(List<Movie> foundMovies)
    {
        this.mMoviesView.showSearchResults(foundMovies);
    }
}
