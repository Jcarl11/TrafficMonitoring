package com.example.joey11.imagestolistviewfromdb;

import android.graphics.Bitmap;

/**
 * Created by joey11 on 9/19/2017.
 */

public class DataItem
{
    Bitmap movieImage;
    String movieID;
    String movieTitle;
    String movieDescription;

    public DataItem(Bitmap movieImage, String movieID, String movieTitle, String movieDescription)
    {
        this.movieImage = movieImage;
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
    }


}
