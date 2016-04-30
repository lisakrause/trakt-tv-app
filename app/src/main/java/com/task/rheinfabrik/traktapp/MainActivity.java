package com.task.rheinfabrik.traktapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.task.rheinfabrik.traktapp.model.Movie;
import com.task.rheinfabrik.traktapp.presenter.MoviesPresenter;
import com.task.rheinfabrik.traktapp.view.FoundMovieAdapter;
import com.task.rheinfabrik.traktapp.view.MoviesView;
import com.task.rheinfabrik.traktapp.view.PopularMovieAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MoviesView{

    private MoviesPresenter mMoviesPresenter;

    private PopularMovieAdapter mMoviesAdapter;
    private FoundMovieAdapter mSearchAdapter;

    private SearchView mSearchView;
    private TextView mStatusText;
    private ImageView mErrorView;

    private ListView mSearchResultsList;
    private ListView mPopularMoviesList;
    private ProgressBar mLoadSpinner;

    private final static String POPULAR_MOVIES = "Popular Movies";
    private final static String SEARCH_RESULTS = "Search Results";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mMoviesPresenter = new MoviesPresenter();
        this.mMoviesPresenter.attachView(this);

        this.mMoviesAdapter = new PopularMovieAdapter(this, new ArrayList<Movie>());
        this.mSearchAdapter = new FoundMovieAdapter(this, new ArrayList<Movie>());

        this.mSearchView = (SearchView) findViewById(R.id.searchView);

        this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(!newText.isEmpty())
                {
                    mStatusText.setText(SEARCH_RESULTS);
                    mMoviesPresenter.searchMovies(mSearchView.getQuery().toString());
                }

                return true;
            }
        });

        this.mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mStatusText.setText(POPULAR_MOVIES);
                showPopularMovies();

                return false;
            }
        });

        this.mPopularMoviesList = (ListView) findViewById(R.id.popularMoviesList);

        this.mSearchResultsList = (ListView) findViewById(R.id.searchResultsList);
        this.mSearchResultsList.setVisibility(View.GONE);

        this.mLoadSpinner = (ProgressBar) findViewById(R.id.loadIndicator);
        this.mLoadSpinner.setVisibility(View.GONE);

        this.mErrorView = (ImageView) findViewById(R.id.errorView);
        this.mStatusText = (TextView) findViewById(R.id.statusText);

        this.mSearchResultsList.setAdapter(this.mSearchAdapter);
        this.mPopularMoviesList.setAdapter(this.mMoviesAdapter);

        this.mStatusText.setText(POPULAR_MOVIES);
        this.mMoviesPresenter.getPopularMovies();
    }


    @Override
    public void showPopularMovies(final List<Movie> moviesList)
    {

        this.mLoadSpinner.setVisibility(View.GONE);
        this.mErrorView.setVisibility(View.GONE);
        this.mSearchResultsList.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.VISIBLE);

        this.mMoviesAdapter.clear();
        this.mMoviesAdapter.addAll(moviesList);
    }

    @Override
    public void showSearchResults(final List<Movie> moviesList)
    {

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
    public void showError()
    {
        this.mErrorView.setVisibility(View.VISIBLE);
        this.mLoadSpinner.setVisibility(View.GONE);

        this.mSearchResultsList.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.GONE);
    }

    private void showPopularMovies()
    {
        this.mLoadSpinner.setVisibility(View.GONE);
        this.mErrorView.setVisibility(View.GONE);
        this.mSearchResultsList.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.VISIBLE);
    }
}
