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

public class AddUser extends AppCompatActivity {


    EditText et_j_uname;
    EditText et_j_pword;
    EditText et_j_email;
    TextView tv_j_unameError;
    TextView tv_j_emailError;
    TextView tv_j_emptyError;
    Button btn_j_back;
    Button btn_j_register;


    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        et_j_uname = findViewById(R.id.et_v_newUser_uname);
        et_j_pword = findViewById(R.id.et_v_newUser_pword);
        et_j_email = findViewById(R.id.et_v_newUser_email);
        tv_j_unameError = findViewById(R.id.tv_v_newUser_unameError);
        tv_j_emailError = findViewById(R.id.tv_v_newUser_emailError);
        tv_j_emptyError = findViewById(R.id.tv_v_newUser_emptyError);
        btn_j_back = findViewById(R.id.btn_v_newUser_back);
        btn_j_register = findViewById(R.id.btn_v_newUser_register);

        dbHelper = new DatabaseHelper(this);


        setOnClickListeners();
    }



    private void setOnClickListeners()
    {
        //register button
        btn_j_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //reset error messages
                tv_j_emptyError.setVisibility(View.INVISIBLE);
                tv_j_unameError.setVisibility(View.INVISIBLE);
                tv_j_emailError.setVisibility(View.INVISIBLE);

                String enteredUname = et_j_uname.getText().toString();
                String enteredPword = et_j_pword.getText().toString();
                String enteredEmail = et_j_email.getText().toString();

                //error checking
                if(enteredUname.isEmpty() || enteredPword.isEmpty() || enteredEmail.isEmpty())
                {
                    tv_j_emptyError.setVisibility(View.VISIBLE);
                    return;
                }
                if(dbHelper.doesUsernameExist(enteredUname))
                {
                    tv_j_unameError.setVisibility(View.VISIBLE);
                    return;
                }
                if(dbHelper.doesEmailExist(enteredEmail))
                {
                    tv_j_emailError.setVisibility(View.VISIBLE);
                    return;
                }

                User u = new User(enteredUname, enteredPword, enteredEmail);
                dbHelper.addUserToDatabase(u);

                finish();
            }
        });
        //back button
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}