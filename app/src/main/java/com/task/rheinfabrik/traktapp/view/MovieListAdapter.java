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
import com.task.rheinfabrik.traktapp.model.IMovie;

import java.util.List;

/**
 * Provides an adapter that is used to populate the movie lists (popular, search results).
 */
public class MovieListAdapter extends ArrayAdapter<IMovie>
{

    /**
     * Constructs a MovieListAdapter.
     *
     * @param context The current context.
     * @param movies The list of movies that shall be shown in the list.
     */
    public MovieListAdapter(Context context, List<IMovie> movies)
    {
        super(context, R.layout.movie_list_item, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //---Get the data model to the list item at the current position
        IMovie movie = getItem(position);

        //---Setup view
        convertView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movie_list_item, parent, false);

        TextView titleView = (TextView) convertView.findViewById(R.id.titleView);
        TextView yearView = (TextView) convertView.findViewById(R.id.yearView);

        EditText overviewView = (EditText) convertView.findViewById(R.id.overviewText);

        titleView.setText(movie.getTitle());
        if(movie.hasYear())
        {
            yearView.setText("" + movie.getYear());
        }else
        {
            yearView.setText("unknown year");
        }

        overviewView.setText(movie.getOverview(), TextView.BufferType.EDITABLE);

        //----lazy load image
        ImageView imageView = (ImageView) convertView.findViewById(R.id.movieImage);

        if(movie.hasImage())
        {
            Picasso
                    .with(parent.getContext())
                    .load(movie.getImageUrl().toString())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.image_error)
                    .into(imageView);

        }

        return convertView;

    }

}
