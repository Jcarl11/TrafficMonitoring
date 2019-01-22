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


public class Adapter extends ArrayAdapter<DataItem>
{
    Context context;
    int layoutID;
    List<DataItem>data = null;

    public Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DataItem> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        this.data = objects;
    }

    static class Holder
    {
        TextView movie_ID,movie_Title, movie_TimeSlot;
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
            holder.movie_ID = (TextView) convertView.findViewById(R.id.TEXTVIEW_MOVIETID);
            holder.movie_Title = (TextView)convertView.findViewById(R.id.TEXTVIEW_MOVIETITLE);
            holder.movie_TimeSlot = (TextView)convertView.findViewById(R.id.TEXTVIEW_TIME);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }

        DataItem dataItem = data.get(position);
        holder.movie_ID.setText(dataItem.movieID);
        holder.movie_Title.setText(dataItem.movieTitle);
        holder.movie_TimeSlot.setText(dataItem.movieTimeSlot);

        return convertView;
    }

    public int getRating(double rating)
    {
        int starRepresentation = 0;
        if(rating == 0)
        {
            starRepresentation = R.drawable.star_zero;
        }
        else if(rating == 1)
        {
            starRepresentation = R.drawable.star_one;
        }
        else if(rating == 1.5)
        {
            starRepresentation = R.drawable.star_onef;
        }
        else if(rating == 2)
        {
            starRepresentation = R.drawable.star_two;
        }
        else if(rating == 2.5)
        {
            starRepresentation = R.drawable.star_twof;
        }
        else if(rating == 3)
        {
            starRepresentation = R.drawable.star_three;
        }
        else if(rating == 3.5)
        {
            starRepresentation = R.drawable.star_threef;
        }
        else if(rating == 4)
        {
            starRepresentation = R.drawable.star_four;
        }
        else if(rating == 4.5)
        {
            starRepresentation = R.drawable.star_fourf;
        }
        else if(rating == 5)
        {
            starRepresentation = R.drawable.star_five;
        }
        return starRepresentation;
    }
}
