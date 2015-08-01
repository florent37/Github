package com.github.florent37.github.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

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

    public void save(Context context) {
        new Handler().post(() -> {
            String json = gson.toJson(user);
            sharedPreferences.edit().putString(USER, json).commit();
        });
    }

    public User load(Context context) {
        String json = sharedPreferences.getString(USER, null);
        if (json != null)
            user = gson.fromJson(json, User.class);
        return user;
    }

    public User getUser() {
        return user;
    }

    public User setUser(User user, Context context) {
        if (user != null) {
            this.user = user;
            this.save(context);
        }
        return this.user;
    }
}
