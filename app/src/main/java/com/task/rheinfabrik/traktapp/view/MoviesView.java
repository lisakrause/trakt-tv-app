package com.task.rheinfabrik.traktapp.view;

import com.task.rheinfabrik.traktapp.model.Movie;

import java.util.List;

/**
 * Created by lisa on 28.04.2016.
 */
public interface MoviesView {

    void showPopularMovies(List<Movie> moviesList);
    void showSearchResults(List<Movie> moviesList);
    void showLoading();
    void showError();
}
