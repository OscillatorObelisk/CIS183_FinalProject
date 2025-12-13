package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateMovie extends AppCompatActivity {


    EditText et_j_title;
    EditText et_j_synopsis;
    EditText et_j_year;
    Button btn_j_back;
    Button btn_j_update;
    TextView tv_j_error;


    DatabaseHelper dbHelper;
    private int movieid;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_j_title = findViewById(R.id.et_v_updatemovie_title);
        et_j_synopsis = findViewById(R.id.et_v_updatemovie_synopsis);
        et_j_year = findViewById(R.id.et_v_updatemovie_year);
        btn_j_back = findViewById(R.id.btn_v_updatemovie_back);
        btn_j_update = findViewById(R.id.btn_v_updatemovie_update);
        tv_j_error = findViewById(R.id.tv_v_updatemovie_error);

        dbHelper = new DatabaseHelper(this);



        Intent intent = getIntent();
        movieid = intent.getIntExtra("movieid", -1);


        currentMovie = dbHelper.getMoviegivenId(movieid);

        et_j_title.setText(currentMovie.getTitle());
        et_j_synopsis.setText(currentMovie.getSynopsis());
        et_j_year.setText(String.valueOf(currentMovie.getYear()));




        setOnClickListeners();
    }


    private void setOnClickListeners()
    {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_j_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_j_error.setVisibility(View.INVISIBLE);


                String enteredTitle = et_j_title.getText().toString();
                String enteredSynopsis = et_j_synopsis.getText().toString();
                String enteredYear = et_j_year.getText().toString();

                //error checking
                if(enteredTitle.isEmpty() || enteredSynopsis.isEmpty() || enteredYear.isEmpty())
                {
                    tv_j_error.setVisibility(View.VISIBLE);
                    return;
                }

                Movie m = new Movie();
                m.setMovieid(movieid);
                m.setTitle(enteredTitle);
                m.setSynopsis(enteredSynopsis);
                m.setYear(Integer.parseInt(enteredYear));

                // This calls your ContentValues-based update method
                dbHelper.updateMovieDatabase(m);


                finish();
            }
        });
    }
}