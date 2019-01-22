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

public class PastMoviesAdapter extends ArrayAdapter<PastMoviesDataItem>
{
    Context context;
    int layoutID;
    List<PastMoviesDataItem>data = null;
    public PastMoviesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PastMoviesDataItem> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        this.data = objects;
    }

    static class Holder
    {
        TextView movie_Title, movie_Rating, movie_Date, movie_Desc;
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
            holder.movie_Title = (TextView)convertView.findViewById(R.id.TEXTVIEW_TITLE4);
            holder.movie_Rating = (TextView)convertView.findViewById(R.id.TEXTVIEW_RATING4);
            holder.movie_Date = (TextView)convertView.findViewById(R.id.TEXTVIEW_TIME4);
            holder.movie_Desc = (TextView)convertView.findViewById(R.id.TEXTVIEW_DESCRIPTION);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }
        PastMoviesDataItem dataItem = data.get(position);
        holder.movie_Title.setText(dataItem.movieTitle);
        holder.movie_Rating.setText(dataItem.movieRating);
        holder.movie_Date.setText(dataItem.moviePlayDate);
        holder.movie_Desc.setText(dataItem.movieDescription);
        return convertView;
    }
}
