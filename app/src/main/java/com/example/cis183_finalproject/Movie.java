package com.example.cis183_finalproject;

public class Movie
{
    private int movieid;
    private int userid;
    private String title;
    private String synopsis;
    private int year;

    public Movie()
    {

    }
    public Movie(int uid, String t, String s, int y)
    {
        userid = uid;
        title = t;
        synopsis = s;
        year = y;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
