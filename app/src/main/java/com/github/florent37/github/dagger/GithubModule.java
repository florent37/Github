package com.github.florent37.github.dagger;

import android.app.Application;
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

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by florentchampigny on 30/07/15.
 */
@Singleton
@Module
public class GithubModule {

    @Singleton
    @Provides
    @Named(RepoManager.PREFS_REPOS)
    public SharedPreferences provideSharedPrefsRepo(Application application) {
        return application.getSharedPreferences(RepoManager.PREFS_REPOS, Context.MODE_PRIVATE);
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
                .addInterceptor(chain -> {
                    final Request request = chain.request();

                    final Request newRequest = request.newBuilder()
                            //ajoute "baerer: 1234567890" en header de chaque requête
                            .addHeader("bearer","1234567890")
                            .build();

                    return chain.proceed(newRequest);
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Singleton
    @Provides
    public GithubAPI provideGithubApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(BuildConfig.URL_GITHUB)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
