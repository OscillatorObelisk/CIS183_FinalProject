package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomePage extends AppCompatActivity {


    Button btn_j_logout;
    Button btn_j_movies;
    Button btn_j_reviews;
    Button btn_j_search;
    Button btn_j_update;
    TextView tv_j_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        btn_j_logout = findViewById(R.id.btn_v_welcome_logout);
        btn_j_movies = findViewById(R.id.btn_v_welcome_movies);
        btn_j_reviews = findViewById(R.id.btn_v_welcome_reviews);
        btn_j_search = findViewById(R.id.btn_v_welcome_search);
        btn_j_update = findViewById(R.id.btn_v_welcome_updateuser);
        tv_j_user = findViewById(R.id.tv_v_welcome_user);

        //set text for user's profile
        setProfileText();

        setOnClickListeners();

    }
    @Override
    protected void onResume() {
        super.onResume();

        setProfileText();
    }

    private void setProfileText()
    {
        String loggedUserText = SessionData.getLoggedInUser().getUname() + "'s' Profile";
        tv_j_user.setText(loggedUserText);
    }

    private void setOnClickListeners()
    {
        //logout button
        btn_j_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SessionData.setLoggedInUser(null);
                finish();
            }
        });
        //View Your Movies button
        btn_j_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, YourMovies.class));
            }
        });
        //View Your Reviews Button
        btn_j_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, YourReviews.class));
            }
        });
        //Search Movies button
        btn_j_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, SearchMovies.class));
            }
        });
        //view/update user info
        btn_j_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, UpdateUser.class));
            }
        });
    }
}