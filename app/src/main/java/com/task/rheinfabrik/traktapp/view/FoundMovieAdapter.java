package com.task.rheinfabrik.traktapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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



            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.found_movie_list_item, parent, false);

            TextView titleView = (TextView) convertView.findViewById(R.id.titleView);
            TextView yearView = (TextView) convertView.findViewById(R.id.yearView);

            EditText overviewView = (EditText) convertView.findViewById(R.id.overviewText);

            titleView.setText(movie.getTitle());
            if(movie.getYear() != 0)
            {
                yearView.setText("" + movie.getYear());
            }else
            {
                yearView.setText("unknown year");
            }

            overviewView.setText(movie.getOverview(), TextView.BufferType.EDITABLE);




        ImageView imageView = (ImageView) convertView.findViewById(R.id.movieImage);

        Picasso
                .with(parent.getContext())
                .load(movie.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.image_error)
                .into(imageView);

        return convertView;

    }


}
