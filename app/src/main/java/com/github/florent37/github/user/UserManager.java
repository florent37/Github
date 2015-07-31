package com.github.florent37.github.user;

import android.content.SharedPreferences;
import android.os.Handler;

import com.google.gson.Gson;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class UserManager {

    protected User user;
    protected SharedPreferences sharedPreferences;
    protected Gson gson;
    protected final static String USER = "USER";

    public void save() {
        new Handler().post(() -> {
            String json = gson.toJson(user);
            sharedPreferences.edit().putString(USER, json).commit();
        });
    }

    public User load() {
        String json = sharedPreferences.getString(USER, null);
        if (json != null)
            user = gson.fromJson(json, User.class);
        return user;
    }

    public UserManager(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public User getUser() {
        return user;
    }

    public User setUser(User user) {
        if (user != null) {
            this.user = user;
            save();
        }
        return this.user;
    }
}
