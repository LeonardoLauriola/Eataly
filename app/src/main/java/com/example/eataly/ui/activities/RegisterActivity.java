package com.example.eataly.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener{
    private Button registerBtn;
    private EditText passwordEt,emailEt,usernameEt;
    private Boolean control[]={false,false,false};
    private final static String TAG = RegisterActivity.class.getSimpleName();

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        usernameEt=findViewById(R.id.username_et);
        registerBtn=findViewById(R.id.register_Btn);
        registerBtn.setOnClickListener(this);
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

        usernameEt.addTextChangedListener(new TextWatcher() {
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
               control[2]=true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void doControl()
    {
        for(int i=0; i< control.length; i++){
            if(!control[i]){
                return;
            }
        }
        registerBtn.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.register_Btn){
            RestController restController=new RestController(this);
            Map<String, String> register = new HashMap<>();
            register.put("username", usernameEt.getText().toString());
            register.put("email", emailEt.getText().toString());
            register.put("password", passwordEt.getText().toString());
            restController.postRequest(User.REGISTER_ENDPOINT, register,this,this);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String token = jsonObject.getString("jwt");
            User user= new User(jsonObject,token);
           /* SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("TOKEN", token);
            editor.commit();*/
            Log.d("response",response);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}