package com.task.rheinfabrik.traktapp.presenter;

import android.view.View;

import com.task.rheinfabrik.traktapp.model.Movie;
import com.task.rheinfabrik.traktapp.network.GetPopularMoviesTask;
import com.task.rheinfabrik.traktapp.network.SearchForMoviesTask;
import com.task.rheinfabrik.traktapp.view.MoviesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 28.04.2016.
 */
public class MoviesPresenter implements Presenter<MoviesView>
{
   private MoviesView mMoviesView;

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

        GetPopularMoviesTask popularTask = new GetPopularMoviesTask();

        popularTask.execute(this);
    }

    public void receivePopularMovies(List<Movie> popularMovies)
    {
        this.mMoviesView.showMovies(popularMovies);
    }

    public void searchMovies(String searchText)
    {
        this.mMoviesView.showLoading();

        SearchForMoviesTask searchTask = new SearchForMoviesTask();

        searchTask.execute(this);
    }

    public void receiveFoundMoviesList(List<Movie> foundMovies)
    {
        //TODO: Zwischen Suche und Populär unterscheiden
        this.mMoviesView.showMovies(foundMovies);
    }
}
