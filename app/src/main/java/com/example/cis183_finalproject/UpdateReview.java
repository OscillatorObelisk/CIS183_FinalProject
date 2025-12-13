package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class UpdateReview extends AppCompatActivity {


    Spinner sp_j_score;
    EditText et_j_text;
    Button btn_j_back;
    Button btn_j_update;
    TextView tv_j_error;


    ArrayAdapter<Integer> spinnerAdapter;
    DatabaseHelper dbHelper;

    private int reviewid;
    private Review currentReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp_j_score = findViewById(R.id.sp_v_updatereview_score);
        et_j_text = findViewById(R.id.et_v_updatereview_text);
        btn_j_back = findViewById(R.id.btn_v_updatereview_back);
        btn_j_update = findViewById(R.id.btn_v_updatereview_update);
        tv_j_error = findViewById(R.id.tv_v_updatereview_error);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        reviewid = intent.getIntExtra("reviewid", -1);

        currentReview = dbHelper.getReviewgivenId(reviewid);


        fillspinner();

        sp_j_score.setSelection(currentReview.getScore() - 1);
        et_j_text.setText(currentReview.getText());


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
                //reset error messages
                tv_j_error.setVisibility(View.INVISIBLE);

                String enteredText = et_j_text.getText().toString();
                int score = sp_j_score.getSelectedItemPosition() + 1;
                String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                //error checking
                if(enteredText.isEmpty())
                {
                    tv_j_error.setVisibility(View.VISIBLE);
                    return;
                }

                Review r = new Review();
                r.setReviewid(reviewid);
                r.setUserid(SessionData.getLoggedInUser().getId());
                r.setMovieid(currentReview.getMovieid());
                r.setScore(score);
                r.setText(enteredText);
                r.setDate(currentDateTime);

                // This calls your ContentValues-based update method
                dbHelper.updateReviewDatabase(r);


                finish();
            }
        });
    }

    private void fillspinner()
    {
        ArrayList<Integer> ratings = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ratings);

        sp_j_score.setAdapter(spinnerAdapter);
    }
}