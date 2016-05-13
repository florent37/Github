package com.github.florent37.github.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.florent37.github.BuildConfig;
import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.repo.RepoManager;
import com.github.florent37.github.user.UserManager;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by florentchampigny on 30/07/15.
 */
@Singleton
@Module
public class GithubModule {

    Context context;

    public GithubModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    @Named(RepoManager.PREFS_REPOS)
    public SharedPreferences provideSharedPrefsRepo(Context context) {
        return context.getSharedPreferences(RepoManager.PREFS_REPOS, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    public UserManager provideUserManager(Gson gson) {
        return new UserManager(gson);
    }

    @Singleton
    @Provides
    public GithubAPI provideGithubApi() {
        return new Retrofit.Builder()
            .baseUrl(BuildConfig.URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(GithubAPI.class);
    }

}
