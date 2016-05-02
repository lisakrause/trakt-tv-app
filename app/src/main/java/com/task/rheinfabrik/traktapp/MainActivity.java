package com.task.rheinfabrik.traktapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

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
     * The view that indicates that there has been an error while downloading movies
     * (usually a connection error).
     */
    private LinearLayout mErrorLayout;

    /**
     * The list that shows the 10 most popular movies.
     */
    private ListView mPopularMoviesList;

    /**
     * The progress bar that indicates loading by showing a spinner.
     */
    private ProgressBar mLoadSpinner;

    /**
     * The toolbar of this activity.
     */
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolbar);


        //---init presenter
        this.mMoviesPresenter = new MoviesPresenter(this);
        this.mMoviesPresenter.attachView(this);

        this.mPopularAdapter = new MovieListAdapter(this, new ArrayList<IMovie>());

        //---init views
        initViews();

        //attach data adapter to list
        this.mPopularMoviesList.setAdapter(this.mPopularAdapter);

        if(savedInstanceState == null)
        {
            //start to retrieve popular movies
            this.mMoviesPresenter.getPopularMovies();
        }
    }


    @Override
    public void addPopularMovies(final List<IMovie> moviesList)
    {
        this.mLoadSpinner.setVisibility(View.GONE);
        this.mPopularMoviesList.setVisibility(View.VISIBLE);

        this.mPopularAdapter.clear();
        this.mPopularAdapter.addAll(moviesList);
    }

    @Override
    public void showLoading()
    {
        this.mLoadSpinner.setVisibility(View.VISIBLE);
        this.mErrorLayout.setVisibility(View.GONE);
    }


    @Override
    public void showError() {
        this.mErrorLayout.setVisibility(View.VISIBLE);
        this.mLoadSpinner.setVisibility(View.GONE);

        this.mPopularMoviesList.setVisibility(View.GONE);
    }


    @Override
    public void onStart() {
        super.onStart();
        //TODO
    }

    @Override
    public void onStop() {
        super.onStop();
        //TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:

                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;


            default:
                //action was not recognized
                return super.onOptionsItemSelected(item);

        }
    }


    /**
     * Initializes all views of this app.
     */
    private void initViews()
    {
        this.mPopularMoviesList = (ListView) findViewById(R.id.popularMoviesList);

        this.mLoadSpinner = (ProgressBar) findViewById(R.id.loadIndicator);
        this.mErrorLayout = (LinearLayout) findViewById(R.id.errorLayout);

        this.mErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMoviesPresenter.getPopularMovies();
            }
        });

    }

}
