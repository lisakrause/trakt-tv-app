package com.task.rheinfabrik.traktapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.presenter.MoviesPresenter;
import com.task.rheinfabrik.traktapp.view.MovieListAdapter;
import com.task.rheinfabrik.traktapp.view.IMoviesView;

import java.util.ArrayList;
import java.util.List;


/**
 * The class MainActivity represents the main view of this app in which
 * the user can scroll through movies and enter a search query.
 */
public class MainActivity extends AppCompatActivity implements IMoviesView {

    /**
     * The presenter of this app.
     */
    private MoviesPresenter mMoviesPresenter;

    /**
     * The adapter that is used to populate the list of the most popular movies.
     */
    private MovieListAdapter mPopularAdapter;

    /**
     * The adapter that is used to populate the list of search results.
     */
    private MovieListAdapter mSearchAdapter;

    /**
     * The view in which the user can enter a search query.
     */
    private SearchView mSearchView;
    /**
     * The view that indicates which kind of movie list is shown currently.
     */
    private TextView mStatusText;

    /**
     * The view that indicates that there has been an error while downloading movies
     * (usually a connection error).
     */
    private ImageView mErrorView;

    /**
     * The list that shows the results of the search query that has been entered by the user.
     */
    private ListView mSearchResultsList;

    /**
     * The list that shows the 10 most popular movies.
     */
    private ListView mPopularMoviesList;

    /**
     * The progress bar that indicates loading by showing a spinner.
     */
    private ProgressBar mLoadSpinner;

    /**
     * The text for the status view when popular movies are shown.
     */
    private final static String POPULAR_MOVIES = "Popular Movies";

    /**
     * The text for the status view when search results are shown.
     */
    private final static String SEARCH_RESULTS = "Search Results";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---init presenter
        this.mMoviesPresenter = new MoviesPresenter();
        this.mMoviesPresenter.attachView(this);

        this.mPopularAdapter = new MovieListAdapter(this, new ArrayList<IMovie>());
        this.mSearchAdapter = new MovieListAdapter(this, new ArrayList<IMovie>());

        //---init views
        initViews();

        //attach data adapter to list
        this.mSearchResultsList.setAdapter(this.mSearchAdapter);
        this.mPopularMoviesList.setAdapter(this.mPopularAdapter);

        //start to retrieve popular movies
        this.mStatusText.setText(POPULAR_MOVIES);
        this.mMoviesPresenter.getPopularMovies();
    }

    /**
     * Initializes all views of this app including initial visibility.
     */
    private void initViews() {

        this.mPopularMoviesList = (ListView) findViewById(R.id.popularMoviesList);
        this.mPopularMoviesList.setVisibility(View.GONE);

        this.mSearchResultsList = (ListView) findViewById(R.id.searchResultsList);
        this.mSearchResultsList.setVisibility(View.GONE);

        this.mLoadSpinner = (ProgressBar) findViewById(R.id.loadIndicator);
        this.mLoadSpinner.setVisibility(View.GONE);

        this.mErrorView = (ImageView) findViewById(R.id.errorView);
        this.mErrorView.setVisibility(View.GONE);
        this.mStatusText = (TextView) findViewById(R.id.statusText);

        this.setupSearchView();
    }


    /**
     * Initializes the search view including setting up listener to react to entries or closing
     * the search.
     */
    private void setupSearchView() {
        //init
        this.mSearchView = (SearchView) findViewById(R.id.searchView);

        //attach listener to start search as soon as user starts to enter a query
        this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    mStatusText.setText(SEARCH_RESULTS);
                    mMoviesPresenter.searchMovies(mSearchView.getQuery().toString());
                }

                return true;
            }
        });

        //attach listener to show popular movies when user leaves the search view
        this.mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mStatusText.setText(POPULAR_MOVIES);
                showPopularMovies();

                return false;
            }
        });
    }


    @Override
    public void showPopularMovies(final List<IMovie> moviesList) {

        this.mLoadSpinner.setVisibility(View.GONE);
        this.mErrorView.setVisibility(View.GONE);
        this.mSearchResultsList.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.VISIBLE);

        this.mPopularAdapter.clear();
        this.mPopularAdapter.addAll(moviesList);
    }

    @Override
    public void showSearchResults(final List<IMovie> moviesList) {

        this.mLoadSpinner.setVisibility(View.GONE);
        this.mErrorView.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.GONE);
        this.mSearchResultsList.setVisibility(View.VISIBLE);

        this.mSearchAdapter.clear();
        this.mSearchAdapter.addAll(moviesList);
    }

    @Override
    public void showLoading() {
        this.mLoadSpinner.setVisibility(View.VISIBLE);
        this.mErrorView.setVisibility(View.GONE);

        this.mPopularMoviesList.setVisibility(View.GONE);
        this.mSearchResultsList.setVisibility(View.GONE);


    }


    @Override
    public void showError() {
        this.mErrorView.setVisibility(View.VISIBLE);
        this.mLoadSpinner.setVisibility(View.GONE);

        this.mSearchResultsList.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.GONE);
    }

    private void showPopularMovies() {
        this.mLoadSpinner.setVisibility(View.GONE);
        this.mErrorView.setVisibility(View.GONE);
        this.mSearchResultsList.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
