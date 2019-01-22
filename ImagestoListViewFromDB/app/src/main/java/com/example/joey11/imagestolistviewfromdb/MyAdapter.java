package com.example.joey11.imagestolistviewfromdb;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<DataItem>
{
    Context context;
    int layoutID;
    List<DataItem>data;
    public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DataItem> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        this.data = objects;
    }

    static class Holder
    {
        TextView movie_ID, movie_Title, movie_Description;
        ImageView movie_Image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Holder holder = null;
        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutID, parent, false);
            holder = new Holder();
            holder.movie_ID = (TextView)convertView.findViewById(R.id.TEXTVIEW_ID);
            holder.movie_Title = (TextView)convertView.findViewById(R.id.TEXTVIEW_TITLE);
            holder.movie_Description = (TextView)convertView.findViewById(R.id.TEXTVIEW_DESCRIPTION);
            holder.movie_Image = (ImageView)convertView.findViewById(R.id.IMAGEVIEW);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }
        DataItem dataItem = data.get(position);
        holder.movie_ID.setText(dataItem.movieID);
        holder.movie_Title.setText(dataItem.movieTitle);
        holder.movie_Description.setText(dataItem.movieDescription);
        holder.movie_Image.setImageBitmap(dataItem.movieImage);
        return convertView;
    }
}
