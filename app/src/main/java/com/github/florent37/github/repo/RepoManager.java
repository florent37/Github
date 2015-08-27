package com.github.florent37.github.repo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class RepoManager {

    protected List<Repo> repos = new ArrayList<>();
    protected Gson gson;
    protected final static String PREFS_REPOS = "PREFS_REPOS";
    protected final static String REPOS = "REPOS";
    protected SharedPreferences sharedPreferences;

    public RepoManager(Gson gson) {
        this.gson = gson;
    }

    public void onStart(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_REPOS,Context.MODE_PRIVATE);
    }

    public void onStop(){
        sharedPreferences = null;
    }

    public Repo addRepo(Repo repo) {
        if (repos.contains(repo)) {
            Repo oldRepo = repos.get(repos.indexOf(repo));
            if(repo.getStargazers_count() != oldRepo.getStargazers_count()) {
                oldRepo.setNewStarsCount(repo.getStargazers_count() - oldRepo.getStargazers_count());
                oldRepo.setStargazers_count(repo.getStargazers_count());
            }
            if(repo.getForks_count() != oldRepo.getForks_count()) {
                oldRepo.setNewForksCount(repo.getForks_count() - oldRepo.getForks_count());
                oldRepo.setForks_count(repo.getForks_count());
            }

            return oldRepo;
        }else{
            repos.add(repo); //a new repo
            return repo;
        }
    }

    public List<Repo> load() {
        String json = sharedPreferences.getString(REPOS, null);
        if (json != null) {
            List<Repo> saved = gson.fromJson(json, new TypeToken<List<Repo>>() {
            }.getType());
            if (saved != null)
                this.repos = saved;
        }
        return getRepos();
    }

    public Observable<List<Repo>> loadRepos(){
        return Observable.create(new Observable.OnSubscribe<List<Repo>>() {
            @Override
            public void call(Subscriber<? super List<Repo>> subscriber) {
                subscriber.onNext(load());
                subscriber.onCompleted();
            }
        });
    }

    public void save() {
        String json = gson.toJson(repos);
        sharedPreferences.edit().putString(REPOS, json).apply();
    }

    public List<Repo> getRepos() {
        return repos;
    }
}
