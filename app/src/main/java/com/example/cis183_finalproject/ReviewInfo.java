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

import org.w3c.dom.Text;

public class ReviewInfo extends AppCompatActivity {

    TextView tv_j_score;
    TextView tv_j_text;
    TextView tv_j_author;
    TextView tv_j_date;
    Button btn_j_back;
    Button btn_j_edit;
    Button btn_j_delete;

    DatabaseHelper dbHelper;


    //this is for onResume
    private int reviewid;
    private Review currentReview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        tv_j_score = findViewById(R.id.tv_v_reviewinfo_score);
        tv_j_text = findViewById(R.id.tv_v_reviewinfo_text);
        tv_j_author = findViewById(R.id.tv_v_reviewinfo_author);
        tv_j_date = findViewById(R.id.tv_v_reviewinfo_date);
        btn_j_back = findViewById(R.id.btn_v_reviewinfo_back);
        btn_j_edit = findViewById(R.id.btn_v_reviewinfo_edit);
        btn_j_delete = findViewById(R.id.btn_v_reviewinfo_delete);


        dbHelper = new DatabaseHelper(this);


        Intent intent = getIntent();

        reviewid = intent.getIntExtra("reviewid", -1);

        currentReview = dbHelper.getReviewgivenId(reviewid);

        String sco = "★" + String.valueOf(currentReview.getScore());
        String auth = dbHelper.getUnameGivenId(currentReview.getUserid());
        String fullDate = currentReview.getDate();
        String shortDate = fullDate.split(" ")[0];

        tv_j_score.setText(sco);
        tv_j_text.setText(currentReview.getText());
        tv_j_author.setText(auth);
        tv_j_date.setText(shortDate);

        setOnClickListeners();
        setButtonVisibility();

    }
    @Override
    protected void onResume() {
        super.onResume();

        currentReview = dbHelper.getReviewgivenId(reviewid);

        String sco = "★" + String.valueOf(currentReview.getScore());
        String auth = dbHelper.getUnameGivenId(currentReview.getUserid());
        String fullDate = currentReview.getDate();
        String shortDate = fullDate.split(" ")[0];

        tv_j_score.setText(sco);
        tv_j_text.setText(currentReview.getText());
        tv_j_author.setText(auth);
        tv_j_date.setText(shortDate);

        setOnClickListeners();
        setButtonVisibility();
    }

    private void setButtonVisibility()
    {
        //if you did not make the movie
        if(currentReview.getUserid() != SessionData.getLoggedInUser().getId())
        {
            btn_j_edit.setVisibility(View.INVISIBLE);
            btn_j_delete.setVisibility(View.INVISIBLE);
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
        btn_j_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewInfo.this, UpdateReview.class);

                intent.putExtra("reviewid", reviewid);

                startActivity(intent);
            }
        });
        btn_j_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ReviewInfo.this)
                        .setTitle("Delete Review")
                        .setMessage("Are you sure you want to delete this review?")
                        .setPositiveButton("Yes", (dialog, which) ->
                        {
                            dbHelper.deleteReview(reviewid);
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