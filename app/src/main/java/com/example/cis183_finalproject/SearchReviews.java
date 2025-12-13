package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchReviews extends AppCompatActivity {


    Spinner sp_j_score;
    Spinner sp_j_date;
    ListView lv_j_reviews;
    Button btn_j_back;
    TextView tv_v_title;


    ArrayAdapter<String> dateAdapter;
    ArrayAdapter<String> scoringAdapter;

    DatabaseHelper dbHelper;
    ReviewAdapter rAdapter;

    private int movieid;

    static private ArrayList<Review> listOfReviews;


    private String score;
    private boolean newest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_reviews);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        sp_j_score = findViewById(R.id.sp_v_searchreviews_score);
        sp_j_date = findViewById(R.id.sp_v_searchreviews_date);
        lv_j_reviews = findViewById(R.id.lv_v_searchreviews_reviews);
        btn_j_back = findViewById(R.id.btn_v_searchreviews_back);
        tv_v_title = findViewById(R.id.tv_v_searchmovies_title);

        dbHelper = new DatabaseHelper(this);


        Intent intent = getIntent();

        movieid = intent.getIntExtra("movieid", -1);


        score = "";
        newest = true;
        fillListView();

        String title = "Reviews for " + dbHelper.getMovieNameGivenId(movieid);
        tv_v_title.setText(title);

        fillspinners();
        setOnClickListeners();
        setOnClickListenerListview();
        setSpinnerListeners();
    }
    @Override
    protected void onResume() {
        super.onResume();


        score = "";
        newest = true;
        fillListView();
        fillspinners();

        sp_j_date.setSelection(0);
        sp_j_score.setSelection(0);
    }

    private void setSpinnerListeners()
    {
        sp_j_score.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    score = "";
                    fillListView();
                }
                else if(position == 1)
                {
                    score = "1";
                    fillListView();
                }
                else if(position == 2)
                {
                    score = "2";
                    fillListView();
                }
                else if(position == 3)
                {
                    score = "3";
                    fillListView();
                }
                else if(position == 4)
                {
                    score = "4";
                    fillListView();
                }
                else if(position == 5)
                {
                    score = "5";
                    fillListView();
                }
                else
                {
                    score = "";
                    Log.d("something wrong", "boss");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_j_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    newest = true;
                    fillListView();
                }
                else
                {
                    newest = false;
                    fillListView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setOnClickListeners()
    {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnClickListenerListview()
    {
        lv_j_reviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //load movie info page
                Review clickedReview = (Review) parent.getItemAtPosition(position);
                Intent intent = new Intent(SearchReviews.this, ReviewInfo.class);

                //Log.d("fack", String.valueOf(clickedMovie.getMovieid()));

                intent.putExtra("reviewid", clickedReview.getReviewid());

                startActivity(intent);
            }
        });
    }

    private void fillListView()
    {
        String m = String.valueOf(movieid);

        listOfReviews = dbHelper.getReviewsGivenCriteria(m,"", score, newest);
        rAdapter = new ReviewAdapter(this, listOfReviews);
        lv_j_reviews.setAdapter(rAdapter);
    }

    private void fillspinners()
    {
        ArrayList<String> newest = new ArrayList<>(Arrays.asList("Newest", "Oldest"));
        dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, newest);
        sp_j_date.setAdapter(dateAdapter);

        ArrayList<String> scoring = new ArrayList<>(Arrays.asList("---", "1", "2", "3", "4", "5"));
        scoringAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, scoring);
        sp_j_score.setAdapter(scoringAdapter);
    }
}