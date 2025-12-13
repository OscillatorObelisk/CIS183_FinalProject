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

public class UpdateUser extends AppCompatActivity {

    EditText et_j_uname;
    EditText et_j_pword;
    EditText et_j_email;
    TextView tv_j_unameError;
    TextView tv_j_emailError;
    TextView tv_j_emptyError;
    Button btn_j_back;
    Button btn_j_update;



    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        et_j_uname = findViewById(R.id.et_v_updateuser_username);
        et_j_pword = findViewById(R.id.et_v_updateuser_password);
        et_j_email = findViewById(R.id.et_v_updateuser_email);
        tv_j_unameError = findViewById(R.id.tv_v_updateuser_unameerror);
        tv_j_emailError = findViewById(R.id.tv_v_updateuser_emailerror);
        tv_j_emptyError = findViewById(R.id.tv_v_updateuser_emptyerror);
        btn_j_back = findViewById(R.id.btn_v_updateuser_back);
        btn_j_update = findViewById(R.id.btn_v_updateuser_update);


        dbHelper = new DatabaseHelper(this);


        et_j_uname.setText(SessionData.getLoggedInUser().getUname());
        et_j_pword.setText(SessionData.getLoggedInUser().getPassword());
        et_j_email.setText(SessionData.getLoggedInUser().getEmail());


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
                if(!enteredUname.equals(SessionData.getLoggedInUser().getUname()))
                {
                    if(dbHelper.doesUsernameExist(enteredUname))
                    {
                        tv_j_unameError.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                if(!enteredEmail.equals(SessionData.getLoggedInUser().getEmail()))
                {
                    if(dbHelper.doesEmailExist(enteredEmail))
                    {
                        tv_j_emailError.setVisibility(View.VISIBLE);
                        return;
                    }
                }

                User u = new User();
                u.setId(SessionData.getLoggedInUser().getId());
                u.setUname(enteredUname);
                u.setPassword(enteredPword);
                u.setEmail(enteredEmail);

                dbHelper.updateUserDatabase(u);

                SessionData.setLoggedInUser(u);

                finish();
            }
        });
    }
}