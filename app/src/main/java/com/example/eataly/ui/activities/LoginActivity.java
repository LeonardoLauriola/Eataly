package com.example.eataly.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eataly.R;
import com.example.eataly.Utility;
import com.example.eataly.datamodels.User;
import com.example.eataly.services.RestController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener{
    private Button loginBtn,registerBtn;
    private EditText emailEt,passwordEt;

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
        String email = emailEt.getText().toString();
        String pswd = passwordEt.getText().toString();
        Map<String, String> log = new HashMap<>();
        log.put("identifier", email);
        log.put("password",pswd);
        RestController restController = new RestController(this);
        restController.postRequest(User.LOGIN_ENDPOINT,log,this,this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String token = jsonObject.getString("jwt");
            Log.d("response",response);

            setResult(Activity.RESULT_OK);
            finish();
         /*   SharedPreferences.Editor editor = getSharedPreferences(sharedPreferences, MODE_PRIVATE).edit();
            editor.putString(TOKEN_PREF, token);
            editor.apply();*/
        } catch (JSONException e) {
            Log.e("response",response);
            e.printStackTrace();
        }
    }
}