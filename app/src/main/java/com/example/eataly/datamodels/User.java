package com.example.eataly.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public static final String REGISTER_ENDPOINT = "auth/local/register";
    public static final String LOGIN_ENDPOINT = "auth/local";

    private String id, username, email, accessToken;

    public User(JSONObject object, String accessToken) {
        this.accessToken = accessToken;
        try {
            this.id = object.getString("id");
            this.username = object.getString("username");
            this.email = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
