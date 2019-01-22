package com.joey.cnseats;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joey11 on 9/18/2017.
 */

public class AdapterAvailableMovies extends ArrayAdapter<DataItemAvailableMovies>
{
    Context context;
    int layoutID;
    List<DataItemAvailableMovies>data = null;
    public AdapterAvailableMovies(@NonNull Context context, @LayoutRes int resource, @NonNull List<DataItemAvailableMovies> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        this.data = objects;
    }

    static class Holder
    {
        TextView movie_title,movie_price, movie_rating, movie_time;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Holder holder = null;
        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutID,parent,false);
            holder = new Holder();
            holder.movie_title = (TextView)convertView.findViewById(R.id.TEXTVIEW_TITLE3);
            holder.movie_price = (TextView)convertView.findViewById(R.id.TEXTVIEW_PRICE3);
            holder.movie_rating = (TextView)convertView.findViewById(R.id.TEXTVIEW_RATING3);
            holder.movie_time = (TextView)convertView.findViewById(R.id.TEXTVIEW_TIME3);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }

        DataItemAvailableMovies dataItem = data.get(position);
        holder.movie_title.setText(dataItem.movieTitle);
        holder.movie_price.setText(dataItem.moviePrice);
        holder.movie_rating.setText(dataItem.movieRating);
        holder.movie_time.setText(dataItem.movieTime);

        return convertView;
    }
}
