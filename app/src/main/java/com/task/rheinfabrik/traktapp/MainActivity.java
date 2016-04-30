package com.task.rheinfabrik.traktapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

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

    private EditText mSearchEdit;
    private ListView mMoviesList;
    private ProgressBar mLoadSpinner;
    private ImageView mErrorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mMoviesPresenter = new MoviesPresenter();
        this.mMoviesPresenter.attachView(this);

        this.mMoviesAdapter = new PopularMovieAdapter(this, new ArrayList<Movie>());
        this.mSearchAdapter = new FoundMovieAdapter(this, new ArrayList<Movie>());

        this.mSearchEdit = (EditText) findViewById(R.id.searchEdit);

        this.mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        this.mMoviesList = (ListView) findViewById(R.id.popularMoviesList);
        this.mLoadSpinner = (ProgressBar) findViewById(R.id.loadIndicator);
        this.mErrorView = (ImageView) findViewById(R.id.errorView);

        //this.mMoviesList.setAdapter(this.mMoviesAdapter);
        this.mMoviesList.setAdapter(this.mSearchAdapter);


        //this.mMoviesPresenter.getPopularMovies();
        this.mMoviesPresenter.searchMovies("avengers");


    }


    @Override
    public void showMovies(final List<Movie> moviesList)
    {

        this.mLoadSpinner.setVisibility(View.GONE);
        this.mErrorView.setVisibility(View.GONE);
        this.mMoviesList.setVisibility(View.VISIBLE);


        //this.mMoviesAdapter.clear();
        //this.mMoviesAdapter.addAll(moviesList);

        this.mSearchAdapter.clear();
        this.mSearchAdapter.addAll(moviesList);


    }

    @Override
    public void showLoading() {
        this.mLoadSpinner.setVisibility(View.VISIBLE);
        this.mMoviesList.setVisibility(View.GONE);
        this.mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showError()
    {
        this.mErrorView.setVisibility(View.VISIBLE);
        this.mMoviesList.setVisibility(View.GONE);
        this.mLoadSpinner.setVisibility(View.GONE);
    }
}
