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

public class YourReviews extends AppCompatActivity {


    ListView lv_j_reviews;
    Button btn_j_back;

    DatabaseHelper dbHelper;
    ReviewAdapter rAdapter;

    static private ArrayList<Review> listOfReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_your_reviews);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        lv_j_reviews = findViewById(R.id.lv_v_yourreviews_reviews);
        btn_j_back = findViewById(R.id.btn_v_yourreviews_back);

        dbHelper = new DatabaseHelper(this);

        fillListView();

        setOnClickListeners();
        setOnClickListenerListview();
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
                Intent intent = new Intent(YourReviews.this, ReviewInfo.class);

                intent.putExtra("reviewid", clickedReview.getReviewid());

                startActivity(intent);
            }
        });
    }

    private void fillListView()
    {
        String uid = String.valueOf(SessionData.getLoggedInUser().getId());

        listOfReviews = dbHelper.getReviewsGivenCriteria("",uid, "", true);
        rAdapter = new ReviewAdapter(this, listOfReviews);
        lv_j_reviews.setAdapter(rAdapter);
    }
}