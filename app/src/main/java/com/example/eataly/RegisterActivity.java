package com.example.eataly;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class RegisterActivity extends AppCompatActivity {
    Button registerBtn;
    EditText passwordEt,emailEt,numberEt;
    Boolean control[]={false,false,false};

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        numberEt=findViewById(R.id.number_et);
        registerBtn=findViewById(R.id.register_Btn);
        registerBtn.setEnabled(false);

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                registerBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                control[0]=false;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Utility.verifyEmail(s.toString())){
                    control[0]=true;
                    doControl();
                }
                else
                {
                    control[0]=false;
                }
            }
        });

        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                registerBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                control[1]=false;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Utility.verifyPassword(s.toString())){
                    control[1]=true;
                    doControl();
                }else
                {
                    control[1]=false;
                }
            }
        });

        numberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                registerBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                control[2]=false;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Utility.verifyPhone(s.toString())){
                    control[2]=true;
                    doControl();
                }
                else
                {
                    control[2]=false;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    public void doControl()
    {
        for(int i=0; i< control.length; i++){
            if(!control[i]){
                return;
            }
        }

        showToast("Register Successful");
        registerBtn.setEnabled(true);
    }
}