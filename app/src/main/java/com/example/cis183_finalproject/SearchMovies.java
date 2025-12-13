package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SearchMovies extends AppCompatActivity {


    ListView lv_j_movies;
    Button btn_j_back;

    EditText et_j_director;
    EditText et_j_title;
    EditText et_j_year;


    DatabaseHelper dbHelper;
    MovieAdapter mAdapter;

    static private ArrayList<Movie> listOfMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_movies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        lv_j_movies = findViewById(R.id.lv_v_searchmovies_movies);
        btn_j_back = findViewById(R.id.btn_v_searchmovies_back);
        et_j_director = findViewById(R.id.et_v_searchmovies_director);
        et_j_title = findViewById(R.id.et_v_searchmovies_title);
        et_j_year = findViewById(R.id.et_v_searchmovies_year);

        dbHelper = new DatabaseHelper(this);


        fillListView("","","");

        setTextWatchers();
        setOnClickListener();
        setOnClickListenerListview();
    }
    protected void onResume() {
        super.onResume();

        fillListView("","","");
    }

    private void setOnClickListener()
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
        lv_j_movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //load movie info page
                Movie clickedMovie = (Movie) parent.getItemAtPosition(position);
                Intent intent = new Intent(SearchMovies.this, MovieInfo.class);

                //Log.d("fack", String.valueOf(clickedMovie.getMovieid()));

                intent.putExtra("movieid", clickedMovie.getMovieid());

                startActivity(intent);
            }
        });
    }

    private void setTextWatchers() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                fillListView(
                        et_j_director.getText().toString(),
                        et_j_title.getText().toString(),
                        et_j_year.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };

        et_j_director.addTextChangedListener(watcher);
        et_j_title.addTextChangedListener(watcher);
        et_j_year.addTextChangedListener(watcher);
    }


    private void fillListView(String d, String t, String y)
    {
        if(d.isEmpty() && t.isEmpty() && y.isEmpty())
        {
            listOfMovies = dbHelper.getAllMovies();
            mAdapter = new MovieAdapter(this, listOfMovies);
            lv_j_movies.setAdapter(mAdapter);
        }
        else
        {
            listOfMovies = dbHelper.getAllMoviesGivenCriteria(d, t, y);
            mAdapter = new MovieAdapter(this, listOfMovies);
            lv_j_movies.setAdapter(mAdapter);
        }
    }
}