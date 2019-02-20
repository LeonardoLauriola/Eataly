package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eataly.Preferences;
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
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linearLayout = findViewById(R.id.login_view);
        loginBtn=findViewById(R.id.login_Btn);
        registerBtn=findViewById(R.id.register_Btn);
        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        registerBtn.setVisibility(View.VISIBLE);

        linearLayout.setOnClickListener(this);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(registerBtn.getId()==v.getId()){
            startActivity(new Intent(this,RegisterActivity.class));
        } else{
            if(v.getId() == linearLayout.getId()){
                Utility.hideSoftKeyboard(this);
            }else {
                doLogin();
            }
        }
    }

    private void doLogin(){
        String email = emailEt.getText().toString();
        String pswd = passwordEt.getText().toString();
        Log.d("login",email+" "+pswd);
        Map<String, String> log = new HashMap<>();
        log.put("identifier", email);
        log.put("password",pswd);
        RestController restController = new RestController(this);
        restController.postRequest(User.LOGIN_ENDPOINT,log,this,this);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,R.string.login_error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String token = jsonObject.getString("jwt");
            Log.d("token",response);
            Toast.makeText(this,R.string.login_success,Toast.LENGTH_SHORT).show();
            Preferences.saveStringPreferences(this,"TOKEN", token);
            Intent intent = new Intent("login");
            intent.putExtra("response", response);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            finish();
        } catch (JSONException e) {
            Log.e("response",response);
        }
    }


}