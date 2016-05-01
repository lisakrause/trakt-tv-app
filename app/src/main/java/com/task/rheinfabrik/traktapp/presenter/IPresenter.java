package com.task.rheinfabrik.traktapp.presenter;

import com.task.rheinfabrik.traktapp.view.IMoviesView;


/**
 * Provides an interface for the Presenter of this app.
 *
 * @param <T> The view that shall be attached to this Presenter.
 */
public interface IPresenter<T extends IMoviesView> {

    //TODO: fill methods or remove
    void onCreate();
    void onStart();
    void onStop();
    void onPause();

    /**
     * Attach the view to this Presenter.
     *
     * @param view The view that shall be handled by this Presenter.
     */
    void attachView(T view);
}
