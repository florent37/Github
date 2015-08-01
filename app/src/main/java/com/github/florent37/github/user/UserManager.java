package com.github.florent37.github.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class UserManager {

    protected User user;
    protected Gson gson;
    protected final static String PREFS_USER = "PREFS_USER";
    protected final static String USER = "USER";
    protected SharedPreferences sharedPreferences;

    public UserManager(Gson gson) {
        this.gson = gson;
    }

    public void onStart(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_USER,Context.MODE_PRIVATE);
    }

    public void onStop(){
        sharedPreferences = null;
    }

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
        Log.d("user", json);
        return user;
    }

    public User getUser() {
        return user;
    }

    public User setUser(User user) {
        if (user != null) {
            this.user = user;
            this.save();
        }
        return this.user;
    }
}
