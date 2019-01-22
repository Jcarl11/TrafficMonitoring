package com.example.joey11.listviewwithimages;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<DataItem>
{
    Context context;
    int layoutResourceId;
    List<DataItem> data = null;
    public CustomAdapter(Context context,int resource,List<DataItem> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = objects;
    }

    static class DataHolder
    {
        ImageView ivFlag, starRating;
        TextView tvCountryName,tvDetailsName;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        DataHolder holder = null;
        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId,parent,false);
            holder = new DataHolder();
            holder.ivFlag = (ImageView)convertView.findViewById(R.id.ivCountry);
            holder.tvCountryName = (TextView)convertView.findViewById(R.id.tvCountry);
            holder.tvDetailsName = (TextView)convertView.findViewById(R.id.tvDetails);
            holder.starRating = (ImageView)convertView.findViewById(R.id.ivRating);
            convertView.setTag(holder);
        }
        else
        {
            holder = (DataHolder)convertView.getTag();
        }

        DataItem dataItem = data.get(position);
        holder.tvCountryName.setText(dataItem.countryName);
        holder.ivFlag.setImageResource(dataItem.resIdThumbnail);
        holder.tvDetailsName.setText(dataItem.movieDetails);
        holder.starRating.setImageResource(getRating(dataItem.rating));

        return convertView;
    }

    public int getRating(double rating)
    {
        int starRepresentation = 0;
        if(rating == 0)
        {
            starRepresentation = R.drawable.zero_0;
        }
        else if(rating == 1)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 1.5)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 2)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 2.5)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 3)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 3.5)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 4)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 4.5)
        {
            starRepresentation = R.drawable.one_star;
        }
        else if(rating == 5)
        {
            starRepresentation = R.drawable.one_star;
        }
        return starRepresentation;
    }
}
