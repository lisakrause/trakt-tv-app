package com.task.rheinfabrik.traktapp.view;

import com.task.rheinfabrik.traktapp.model.IMovie;

import java.util.List;

/**
 * Provides an interface for views of this app.
 */
public interface IMoviesView {

    /**
     * Lets this view show a list of popular movies.
     * @param moviesList The list that contains the popular movies.
     */
    void addPopularMovies(List<IMovie> moviesList);


    /**
     * Lets this view indicate a loading of data to the user.
     */
    void showLoading();

    /**
     * Lets this view present an error to the user.
     */
    void showError();
}
