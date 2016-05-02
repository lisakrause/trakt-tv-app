package com.task.rheinfabrik.traktapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.view.EndlessSearchListAdapter;
import com.task.rheinfabrik.traktapp.view.MovieListAdapter;

import java.util.ArrayList;

/**
 * The SearchActivity is a child activity of MainActivity that is initiated as soon as the
 * user presses the search button in the app bar.
 */
public class SearchActivity extends AppCompatActivity {

    /**
     * The adapter that is used to populate the list of search results.
     */
    private MovieListAdapter mSearchAdapter;

    /**
     * The adapter that is used to implement the scrolling in the list of
     * search results.
     */
    private EndlessSearchListAdapter mEndlessAdapter;

    /**
     * The view in which the user can enter a search query.
     */
    private EditText mSearchEdit;


    /**
     * The list that shows the results of the search query that has been entered by the user.
     */
    private ListView mSearchResultsList;

    /**
     * The view that indicates that there has been an error while downloading movies
     * (usually a connection error).
     */
    private ImageView mErrorView;

    /**
     * This view is shown when there are no results found.
     */
    private TextView mNoResultView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //---init up button to MainActivity
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //----init views
        this.mSearchAdapter = new MovieListAdapter(this, new ArrayList<IMovie>());

        this.mSearchResultsList = (ListView) findViewById(R.id.searchResultsList);

        this.mErrorView = (ImageView) findViewById(R.id.errorView);
        this.mNoResultView = (TextView) findViewById(R.id.noResultsView);

        this.setupSearchView();

    }

    /**
     * Initializes the search view including setting up listener to react to entries or closing
     * the search.
     */
    private void setupSearchView() {
        //init
       this.mSearchEdit = (EditText) findViewById(R.id.searchEdit);
        this.mSearchEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String newText = s.toString();

                if (!newText.isEmpty())
                {

                    //and stop former searches by dropping the endless adapter object
                    if(mEndlessAdapter != null)
                    {
                        mEndlessAdapter.stopAppending();
                        mSearchAdapter = new MovieListAdapter(SearchActivity.this, new ArrayList<IMovie>());
                    }

                    mEndlessAdapter = new EndlessSearchListAdapter(mSearchAdapter, newText);
                    //setting new adapter to start new search
                    mSearchResultsList.setAdapter(mEndlessAdapter);

                    //setup view visibilities for search
                    mErrorView.setVisibility(View.GONE);
                    //needs to be done since otherwise endless adapter will not start
                    //to populate the list initially
                    mSearchResultsList.setVisibility(View.VISIBLE);
                    mNoResultView.setVisibility(View.GONE);

                }else
                {
                    if(mEndlessAdapter != null){
                        mEndlessAdapter.stopAppending();
                    }

                    mSearchAdapter.clear();
                    mNoResultView.setVisibility(View.VISIBLE);

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //Do nothing, since we only want to start to search as soon
                // as the user is finished with a change
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //Do nothing, since we only want to start to search as soon
                // as the user is finished with a change
            }
        });
    }
}
