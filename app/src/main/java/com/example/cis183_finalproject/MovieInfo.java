package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MovieInfo extends AppCompatActivity {

    TextView tv_j_title;
    TextView tv_j_year;
    TextView tv_j_score;
    TextView tv_j_synopsis;
    Button btn_j_back;
    Button btn_j_viewreviews;
    Button btn_j_yourreview;
    Button btn_j_makereview;
    Button btn_j_edit;
    Button btn_j_delete;


    DatabaseHelper dbHelper;


    //this is for onResume
    private int movieid;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        tv_j_title = findViewById(R.id.tv_v_movieinfo_title);
        tv_j_year = findViewById(R.id.tv_v_movieinfo_year);
        tv_j_synopsis = findViewById(R.id.tv_v_movieinfo_synopsis);
        tv_j_score = findViewById(R.id.tv_v_movieinfo_score);
        btn_j_back = findViewById(R.id.btn_v_movieinfo_back);
        btn_j_viewreviews = findViewById(R.id.btn_v_movieinfo_viewreviews);
        btn_j_yourreview = findViewById(R.id.btn_v_movieinfo_yourreview);
        btn_j_makereview = findViewById(R.id.btn_v_movieinfo_makereview);
        btn_j_edit = findViewById(R.id.btn_v_movieinfo_editmovie);
        btn_j_delete = findViewById(R.id.btn_v_movieinfo_delete);


        dbHelper = new DatabaseHelper(this);


        Intent intent = getIntent();

        movieid = intent.getIntExtra("movieid", -1);

        currentMovie = dbHelper.getMoviegivenId(movieid);


        setText();
        setOnClickListeners();
        buttonVisibility();

    }
    @Override
    protected void onResume() {
        super.onResume();

        currentMovie = dbHelper.getMoviegivenId(movieid);

        tv_j_title.setText(currentMovie.getTitle());
        tv_j_year.setText(String.valueOf(currentMovie.getYear()));
        //tv_j_score.setText();
        tv_j_synopsis.setText(currentMovie.getSynopsis());

        setText();
        setOnClickListeners();
        buttonVisibility();
    }

    private void setText()
    {
        double avg = dbHelper.getAverageScoreForMovie(currentMovie.getMovieid());
        String reviewed = "★ " + String.format("%.1f", avg);

        tv_j_title.setText(currentMovie.getTitle());
        tv_j_year.setText(String.valueOf(currentMovie.getYear()));
        tv_j_synopsis.setText(currentMovie.getSynopsis());
        if (avg == 0)
        {
            tv_j_score.setText("No reviews");
        }
        else
        {
            tv_j_score.setText(reviewed);
        }
    }

    private void buttonVisibility()
    {
        //if you made the movie
        if(currentMovie.getUserid() == SessionData.getLoggedInUser().getId())
        {
            btn_j_edit.setVisibility(View.VISIBLE);
            btn_j_delete.setVisibility(View.VISIBLE);

            btn_j_makereview.setVisibility(View.INVISIBLE);
            btn_j_yourreview.setVisibility(View.INVISIBLE);

            return;
        }

        //not the owner
        btn_j_edit.setVisibility(View.INVISIBLE);
        btn_j_delete.setVisibility(View.INVISIBLE);

        //whether non owner has a review or not
        if (dbHelper.userHasReview(SessionData.getLoggedInUser().getId(), currentMovie.getMovieid()))
        {
            btn_j_makereview.setVisibility(View.INVISIBLE);
            btn_j_yourreview.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_j_yourreview.setVisibility(View.INVISIBLE);
            btn_j_makereview.setVisibility(View.VISIBLE);
        }

    }

    private void setOnClickListeners()
    {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_j_viewreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieInfo.this, SearchReviews.class);

                intent.putExtra("movieid", movieid);

                startActivity(intent);
            }
        });
        btn_j_yourreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MovieInfo.this, ReviewInfo.class);

                intent.putExtra("reviewid", dbHelper.getReviewId(SessionData.getLoggedInUser().getId(), movieid));

                startActivity(intent);
            }
        });
        btn_j_makereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieInfo.this, AddReview.class);

                intent.putExtra("movieid", movieid);

                startActivity(intent);
            }
        });
        btn_j_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieInfo.this, UpdateMovie.class);

                intent.putExtra("movieid", movieid);

                startActivity(intent);
            }
        });
        btn_j_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MovieInfo.this)
                        .setTitle("Delete Movie")
                        .setMessage("Are you sure you want to delete this movie? (This will also delete all reviews associated with it)")
                        .setPositiveButton("Yes", (dialog, which) ->
                        {

                            dbHelper.deleteMovie(movieid);
                            finish();

                        })
                        .setNegativeButton("No", (dialog, which) ->
                        {
                            dialog.dismiss();
                        })
                        .show();
            }
        });
    }
}