package com.github.florent37.github.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.florent37.github.BuildConfig;
import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.repo.RepoManager;
import com.github.florent37.github.user.UserManager;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by florentchampigny on 30/07/15.
 */
@Singleton
@Module
public class GithubModule {

    @Singleton
    @Provides
    public Gson provideGson(){
        return new Gson();
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences("Github",Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public RepoManager provideRepoManager(Context context, Gson gson, SharedPreferences sharedPreferences){
        return new RepoManager(sharedPreferences,gson);
    }

    @Singleton
    @Provides
    public UserManager provideUserManager(Context context, Gson gson, SharedPreferences sharedPreferences){
        return new UserManager(sharedPreferences,gson);
    }

    @Singleton
    @Provides
    public GithubAPI provideGithubApi(){
        return new RestAdapter.Builder()
                .setEndpoint(BuildConfig.URL_GITHUB)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("Retrofit"))
                .build()
                .create(GithubAPI.class);
    }

}
