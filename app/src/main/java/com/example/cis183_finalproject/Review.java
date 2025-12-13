package com.example.cis183_finalproject;

public class Review
{
    private int reviewid;
    private int userid;
    private int movieid;
    private int score;
    private String text;
    private String date;


    public Review()
    {

    }
    public Review(int uid, int mid, int s, String t, String d)
    {
        userid = uid;
        movieid = mid;
        score = s;
        text = t;
        date = d;
    }


    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
