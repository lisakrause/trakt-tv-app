package com.task.rheinfabrik.traktapp.presenter;

import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.network.GetPopularMoviesTask;
import com.task.rheinfabrik.traktapp.network.SearchForMoviesTask;
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
     * The last search for movies that has been executed.
     * This is needed to cancel former tasks whenever the user enters a new character into the
     * search view.
     */
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

    /**
     * Performs a search for movies on the basis of the given search query and shows a spinner
     * that indicates loading(in the view) in the meantime.
     *
     * @param searchText The query that has been entered by the user.
     */
    public void searchMovies(final String searchText)
    {
        //----loading
        this.mMoviesView.showLoading();

        //----cancel former searches
        if(this.mSearchTask !=  null)
        {
            this.mSearchTask.cancel(true);
        }

        //---perform search
        this.mSearchTask = new SearchForMoviesTask(this, searchText);
        this.mSearchTask.execute();
    }

    /**
     * Receives a list of movies that have been found when executing a search
     * to show them in the view.
     *
     * @param foundMovies The list of the movies that have been found.
     */
    public void receiveFoundMoviesList(final List<IMovie> foundMovies)
    {
        this.mMoviesView.showSearchResults(foundMovies);
    }
}
