package com.example.joey11.listviewwithimages;

public class DataItem
{
    int resIdThumbnail;
    String countryName;
    String movieDetails;
    double rating;

    public DataItem(int resIdThumbnail, String countryName,String details, double rate)
    {
        this.resIdThumbnail = resIdThumbnail;
        this.countryName = countryName;
        this.movieDetails = details;
        this.rating = rate;
    }
}
