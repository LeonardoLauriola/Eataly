package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eataly.R;
import com.example.eataly.Utility;

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener{
    Button loginBtn,registerBtn;
    EditText emailEt,passwordEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn=findViewById(R.id.login_Btn);
        registerBtn=findViewById(R.id.register_Btn);
        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        registerBtn.setVisibility(View.VISIBLE);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(registerBtn.getId()==v.getId()){
            startActivity(new Intent(this,RegisterActivity.class));
        } else{
            doLogin();
        }
    }

    private void doLogin(){
        String email =  emailEt.getText().toString();
        String pswd= passwordEt.getText().toString();

        if(!Utility.verifyEmail(email)){
            Toast.makeText(this,R.string.wrong_email,Toast.LENGTH_SHORT).show();
            return;
        }

        if(pswd.length()<6){
            Toast.makeText(this,R.string.wrong_password, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, R.string.correct_login, Toast.LENGTH_SHORT).show();
    }
}

