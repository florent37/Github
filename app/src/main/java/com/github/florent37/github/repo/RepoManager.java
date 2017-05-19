package com.github.florent37.github.repo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by florentchampigny on 31/07/15.
 */

public class RepoManager {

    public final static String PREFS_REPOS = "PREFS_REPOS";
    private final static String REPOS = "REPOS";

    private List<Repo> repos = new ArrayList<>();

    @Inject Gson gson;
    @Inject
    Application application;
    @Inject @Named(PREFS_REPOS) SharedPreferences sharedPreferences;

    @Inject
    RepoManager() {

    }

    public void onStart(String account) {
        repos.clear();
        sharedPreferences = application.getSharedPreferences(PREFS_REPOS + account, Context.MODE_PRIVATE);
    }

    public void onStop() {
        sharedPreferences = null;
        repos.clear();
    }

    public Repo addRepo(Repo repo) {
        if (repos.contains(repo)) {
            Repo oldRepo = repos.get(repos.indexOf(repo));
            if (repo.getStargazers_count() != oldRepo.getStargazers_count()) {
                oldRepo.setNewStarsCount(repo.getStargazers_count() - oldRepo.getStargazers_count());
                oldRepo.setStargazers_count(repo.getStargazers_count());
            }
            if (repo.getForks_count() != oldRepo.getForks_count()) {
                oldRepo.setNewForksCount(repo.getForks_count() - oldRepo.getForks_count());
                oldRepo.setForks_count(repo.getForks_count());
            }

            return oldRepo;
        } else {
            repos.add(repo); //a new repo
            return repo;
        }
    }

    public List<Repo> load() {
        String json = sharedPreferences.getString(REPOS, null);
        if (json != null) {
            List<Repo> saved = gson.fromJson(json, new TypeToken<List<Repo>>() {
            }.getType());
            if (saved != null) {
                this.repos = saved;
            }
        }
        return getRepos();
    }

    public Observable<List<Repo>> loadRepos() {
        return Observable.fromCallable(this::load);
    }

    public void save() {
        String json = gson.toJson(repos);
        sharedPreferences.edit().putString(REPOS, json).apply();
    }

    public List<Repo> getRepos() {
        return repos;
    }

    public RepoManager addRepos(List<Repo> repos) {
        for (Repo repo : repos) {
            addRepo(repo);
        }
        return this;
    }
}
