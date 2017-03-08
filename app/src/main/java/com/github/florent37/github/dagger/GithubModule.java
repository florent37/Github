package com.github.florent37.github.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.florent37.github.BuildConfig;
import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.repo.RepoManager;
import com.github.florent37.github.repo.repository.Repository;
import com.github.florent37.github.repo.repository.RepositoryLocal;
import com.github.florent37.github.repo.repository.RepositoryNetwork;
import com.github.florent37.github.repo.repository.RepositoryProxy;
import com.github.florent37.github.user.UserManager;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by florentchampigny on 30/07/15.
 */
@Singleton
@Module
public class GithubModule {

    private final Context context;

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

    @Provides
    @Singleton
    public OkHttpClient provideClient(){
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Singleton
    @Provides
    public GithubAPI provideGithubApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(BuildConfig.URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GithubAPI.class);
    }

    @Singleton
    @Provides
    public Repository provideRepository(RepositoryNetwork repositoryNetwork, RepositoryLocal repositoryLocal ){
        return repositoryNetwork;
    }
}
