package com.example.cis183_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Review> listOfReviews;

    public ReviewAdapter(Context c, ArrayList<Review> ls)
    {
        context = c;
        listOfReviews = ls;
    }


    @Override
    public int getCount() {
        return listOfReviews.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfReviews.get(position);
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
            view = mInflater.inflate(R.layout.review_cell, null);
        }

        //find the GUI elements in our custom cell
        TextView reviewer = view.findViewById(R.id.tv_v_reviewcell_creator);
        TextView movie = view.findViewById(R.id.tv_v_reviewcell_movie);
        TextView score  = view.findViewById(R.id.tv_v_reviewcell_score);
        TextView text  = view.findViewById(R.id.tv_v_reviewcell_text);
        TextView date  = view.findViewById(R.id.tv_v_reviewcell_date);


        //we can access different elements based off the position value that is passed to this function

        Review review = listOfReviews.get(position);

        DatabaseHelper dbHelper = new DatabaseHelper(context);

        String rtext = "Review by: " + dbHelper.getUnameGivenId(review.getUserid());
        String mtext = dbHelper.getMovieNameGivenId(review.getMovieid());
        String sco = "★" + String.valueOf(review.getScore());

        String fullText = review.getText();
        String preview;
        if (fullText.length() > 20) {
            preview = fullText.substring(0, 20) + "...";
        } else {
            preview = fullText;
        }

        String fullDate = review.getDate();
        String shortDate = fullDate.split(" ")[0];

        //set the GUI for the custom cell
        reviewer.setText(rtext);
        movie.setText(mtext);
        score.setText(sco);
        text.setText(preview);
        date.setText(shortDate);



        return view;
    }
}
