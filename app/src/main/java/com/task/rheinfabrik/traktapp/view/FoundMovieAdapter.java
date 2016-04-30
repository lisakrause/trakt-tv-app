package com.task.rheinfabrik.traktapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.rheinfabrik.traktapp.R;
import com.task.rheinfabrik.traktapp.model.Movie;

import java.util.List;

/**
 * Created by lisa on 28.04.2016.
 */
public class FoundMovieAdapter extends ArrayAdapter<Movie>
{

    public FoundMovieAdapter(Context context, List<Movie> movies)
    {
        super(context, R.layout.found_movie_list_item, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Movie movie = getItem(position);
        View listItem = convertView;

        if(listItem == null)
        {
            View movieView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.found_movie_list_item, parent, false);
            TextView titleView = (TextView) movieView.findViewById(R.id.movieTitle);
            TextView yearView = (TextView) movieView.findViewById(R.id.yearView);
            ImageView imageView = (ImageView) movieView.findViewById(R.id.movieImage);
            EditText overviewView = (EditText) movieView.findViewById(R.id.overviewText);

            titleView.setText(movie.getTitle());
            yearView.setText(movie.getYear());
            overviewView.setText(movie.getOverview(), TextView.BufferType.EDITABLE);


            //TODO: Bild

            return movieView;
        }else
        {
            return convertView;
        }
    }
}
