package com.task.rheinfabrik.traktapp.presenter;

import com.task.rheinfabrik.traktapp.view.MoviesView;

/**
 * Created by lisa on 28.04.2016.
 */
public interface Presenter<T extends MoviesView> {

    void onCreate();
    void onStart();
    void onStop();
    void onPause();
    void attachView(T view);
}
