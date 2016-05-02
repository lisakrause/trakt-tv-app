package com.task.rheinfabrik.traktapp.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.commonsware.cwac.endless.EndlessAdapter;
import com.task.rheinfabrik.traktapp.model.IMovie;
import com.task.rheinfabrik.traktapp.network.SearchForMoviesRequester;

import java.util.List;

/**
 * Provides lazy loading of the found movies by populating the ListAdapter with
 * additional movies every time the bottom of the list is reached.
 */
public class EndlessSearchListAdapter extends EndlessAdapter
{
    /**
     * The search query that was entered by the user.
     * Needed to request further search results to populate the list with.
     */
    private String mSearchQuery;

    /**
     * The current page of search results that needs to be loaded when
     * the user scrolls to the end of the list.
     */
    private int mCurrentPage = 1;

    /**
     * The list of movies that has been loaded when the end of the list was reached.
     */
    private List<IMovie> mCachedMovies;

    /**
     * The maximum number of search results pages that can be requested.
     */
    private final static int PAGE_LIMIT = 10;

    /**
     * Constructs an EndlessSearchListAdapter.
     *
     * @param listAdapter The adapter that actually holds the list of movies that is used
     *                    to populate it (decorator pattern).
     * @param searchQuery The search query that was entered by the user.
     */
    public EndlessSearchListAdapter(MovieListAdapter listAdapter, String searchQuery)
    {
        super(listAdapter, true);

        this.mSearchQuery = searchQuery;
    }

    /**
     * Has to be overridden in order for the EndlessAdapter to function.
     * Fetches the next page of movies on the background thread and saves them
     * in a private member variable. Returns true on success, false otherwise.
     */
    @Override
    protected boolean cacheInBackground() throws Exception
    {
        //If the end of search result pages has been reached...
        if(this.mCurrentPage > PAGE_LIMIT)
        {
            //do not try to download more
            return false;
        }

        try
        {
            //try to download the next page of search results
            this.mCachedMovies = SearchForMoviesRequester.getSearchResults(this.mSearchQuery,
                    this.mCurrentPage);
        }catch(Exception e)
        {
            //since an exception occurred, do not try to add more items to the list
            //(indicated by false)
            return false;
        }

        if(this.mCachedMovies == null)
        {
            //since no movies have been found, do not try to add more items to the list
            //(indicated by false)
            return false;
        }

        //increment the current page number and...
        this.mCurrentPage++;
        //... tell EndlessAdapter that there is more data available which
        //can be added to the list
        return true;
    }

    /**
     * Has to be overridden in order for the EndlessAdapter to function.
     * Is automatically called when cacheInBackground returned true and adds
     * the just fetched page of movies to the MovieListAdapter.
     */
    @Override
    protected void appendCachedData()
    {
        if(this.mCachedMovies == null)
        {
            return;
        }

        MovieListAdapter adapter =  (MovieListAdapter)this.getWrappedAdapter();
        adapter.addAll(this.mCachedMovies);
    }

    /**
     * Has to be overridden in order for the EndlessAdapter to function.
     * Provides a view that signals that a new page of movies is being fetched.
     */
    @Override
    protected View getPendingView(ViewGroup parent) {

        return new ProgressBar(parent.getContext());
    }

}
