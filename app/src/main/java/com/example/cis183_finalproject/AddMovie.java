package com.example.cis183_finalproject;

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

import org.w3c.dom.Text;

public class AddMovie extends AppCompatActivity {



    EditText et_j_title;
    EditText et_j_synopsis;
    EditText et_j_year;
    Button btn_j_back;
    Button btn_j_add;
    TextView tv_j_error;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //GUI
        et_j_title = findViewById(R.id.et_v_addmovie_title);
        et_j_synopsis = findViewById(R.id.et_v_addmovie_synopsis);
        et_j_year = findViewById(R.id.et_v_addmovie_year);
        btn_j_back = findViewById(R.id.btn_v_addmovie_back);
        btn_j_add = findViewById(R.id.btn_v_addmovie_add);
        tv_j_error = findViewById(R.id.tv_v_addmovie_error);


        dbHelper = new DatabaseHelper(this);

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
        btn_j_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset error messages
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

                Movie m = new Movie(SessionData.getLoggedInUser().getId(), enteredTitle, enteredSynopsis, Integer.parseInt(enteredYear));
                dbHelper.addMovieToDatabase(m);

                finish();
            }
        });
    }
}