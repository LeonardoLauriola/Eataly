package com.example.eataly.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RestController {
    
    private final static String BASE_URL = "http://138.68.86.70/";
    private final static String VERSION = "";
    private RequestQueue queue;

    public RestController(Context context){
        this.queue= Volley.newRequestQueue(context);
    }

    public void getRequest(String endpoint, Response.Listener<String> success, Response.ErrorListener error){
        StringRequest request= new StringRequest(Request.Method.GET,
                BASE_URL.concat(VERSION).concat(endpoint),
                success,
                error
        );
        queue.add(request);
    }

    public void postRequest(String endpoint, Response.Listener<String> success, Response.ErrorListener error){
        StringRequest request = new StringRequest(Request.Method.POST,
                BASE_URL.concat(VERSION).concat(endpoint),
                success,
                error
                );
        queue.add(request);
    }
}
