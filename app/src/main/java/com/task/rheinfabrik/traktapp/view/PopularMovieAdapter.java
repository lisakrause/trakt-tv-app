package com.task.rheinfabrik.traktapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.rheinfabrik.traktapp.R;
import com.task.rheinfabrik.traktapp.model.Movie;

import java.util.List;

/**
 * Created by lisa on 28.04.2016.
 */
public class PopularMovieAdapter extends ArrayAdapter<Movie>
{

    public PopularMovieAdapter(Context context, List<Movie> movies)
    {
        super(context, R.layout.popular_movie_list_item, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Movie movie = getItem(position);
        View listItem = convertView;

        if(listItem == null)
        {
            View movieView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.popular_movie_list_item, parent, false);
            TextView titleView = (TextView) movieView.findViewById(R.id.movieTitle);
            ImageView imageView = (ImageView) movieView.findViewById(R.id.movieImage);

            titleView.setText(movie.getTitle());
            //TODO: Bild

            return movieView;
        }else
        {
            return convertView;
        }
    }
}
