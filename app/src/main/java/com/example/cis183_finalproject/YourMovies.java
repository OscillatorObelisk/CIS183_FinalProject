package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class YourMovies extends AppCompatActivity {



    ListView lv_j_movies;
    Button btn_j_back;
    Button btn_j_add;


    DatabaseHelper dbHelper;
    MovieAdapter mAdapter;

    static private ArrayList<Movie> listOfMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_your_movies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        lv_j_movies = findViewById(R.id.lv_v_yourmovies_movies);
        btn_j_back = findViewById(R.id.btn_v_yourmovies_back);
        btn_j_add = findViewById(R.id.btn_v_yourmovies_add);

        dbHelper = new DatabaseHelper(this);

        fillListView();

        setOnClickListenersListview();
        setOnClickListeners();
    }
    @Override
    protected void onResume() {
        super.onResume();

        fillListView();
    }


    private void setOnClickListeners()
    {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_j_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YourMovies.this, AddMovie.class));
            }
        });
    }

    private void setOnClickListenersListview()
    {
        lv_j_movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //load movie info page
                Movie clickedMovie = (Movie) parent.getItemAtPosition(position);
                Intent intent = new Intent(YourMovies.this, MovieInfo.class);

                //Log.d("fack", String.valueOf(clickedMovie.getMovieid()));

                intent.putExtra("movieid", clickedMovie.getMovieid());

                startActivity(intent);
            }
        });
    }


    private void fillListView()
    {
        listOfMovies = dbHelper.getMoviesGivenUserId(SessionData.getLoggedInUser().getId());
        mAdapter = new MovieAdapter(this, listOfMovies);
        lv_j_movies.setAdapter(mAdapter);
    }
}