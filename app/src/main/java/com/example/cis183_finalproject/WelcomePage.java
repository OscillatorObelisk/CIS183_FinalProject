package com.example.cis183_finalproject;

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
        tv_j_user = findViewById(R.id.tv_v_welcome_user);

        //set text for user's profile
        String loggedUserText = SessionData.getLoggedInUser().getUname() + "'s' Profile";
        tv_j_user.setText(loggedUserText);

        setOnClickListeners();

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
    }
}