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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    EditText et_j_username;
    EditText et_j_password;
    Button btn_j_login;
    Button btn_j_register;
    TextView tv_j_error;


    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //GUI
        et_j_username = findViewById(R.id.et_v_main_username);
        et_j_password = findViewById(R.id.et_v_main_password);
        btn_j_login = findViewById(R.id.btn_v_main_login);
        btn_j_register = findViewById(R.id.btn_v_main_register);
        tv_j_error = findViewById(R.id.tv_v_main_error);

        //initialize database
        dbHelper = new DatabaseHelper(this);
        dbHelper.initAllTables();


        setOnClickListeners();

    }




    private void setOnClickListeners()
    {
        //login button
        btn_j_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //entered username and password
                String enteredUname = et_j_username.getText().toString();
                String enteredPword = et_j_password.getText().toString();

                User user = dbHelper.getUserGivenUname(enteredUname);

                //if the db function found the uname in the db
                if(user == null)
                {
                    tv_j_error.setVisibility(View.VISIBLE);
                    return;
                }

                //if the password entered matches the one tied to the username
                if(!user.getPassword().equals(enteredPword))
                {
                    tv_j_error.setVisibility(View.VISIBLE);
                    return;
                }

                tv_j_error.setVisibility(View.INVISIBLE);

                SessionData.setLoggedInUser(user);
                //log the user in
                startActivity(new Intent(MainActivity.this, WelcomePage.class));
            }
        });

        //register button
        btn_j_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, NewUser.class));

                tv_j_error.setVisibility(View.INVISIBLE);
            }
        });
    }
}