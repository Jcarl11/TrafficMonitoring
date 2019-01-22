package com.joey.cnseats;

public class DataItem
{
    String movieID;
    String movieTitle;
    String movieTimeSlot;

    public DataItem(String movieID,String movieTitle,String timeSlot)
    {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.movieTimeSlot = timeSlot;
    }
}
