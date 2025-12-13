package com.example.cis183_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Movie> listOfMovies;

    public MovieAdapter(Context c, ArrayList<Movie> ls)
    {
        context = c;
        listOfMovies = ls;
    }


    @Override
    public int getCount() {
        return listOfMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null)
        {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.movie_cell, null);
        }

        //find the GUI elements in our custom cell
        TextView director = view.findViewById(R.id.tv_v_moviecell_creator);
        TextView title = view.findViewById(R.id.tv_v_moviecell_title);
        TextView year  = view.findViewById(R.id.tv_v_moviecell_year);
        TextView avgScore = view.findViewById(R.id.tv_v_moviecell_score);

        //we can access different elements based off the position value that is passed to this function
        Movie movie = listOfMovies.get(position);

        DatabaseHelper dbHelper = new DatabaseHelper(context);

        String dtext = "Directed by: " + dbHelper.getUnameGivenId(movie.getUserid());

        double avg = dbHelper.getAverageScoreForMovie(movie.getMovieid());
        String reviewed = "★ " + String.format("%.1f", avg);

        //set the GUI for the custom cell
        director.setText(dtext);
        title.setText(movie.getTitle());
        year.setText(String.valueOf(movie.getYear()));
        if (avg == 0)
        {
            avgScore.setText("No reviews");
        }
        else
        {
            avgScore.setText(reviewed);
        }

        return view;
    }
}
