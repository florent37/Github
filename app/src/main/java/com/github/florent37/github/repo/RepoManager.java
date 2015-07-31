package com.github.florent37.github.repo;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class RepoManager {

    protected List<Repo> repos = new ArrayList<>();
    protected SharedPreferences sharedPreferences;
    protected Gson gson;
    protected final static String REPOS = "REPOS";

    public RepoManager(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
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
            repos.add(repo);
            return repo;
        }
    }

    public List<Repo> load() {
        String json = sharedPreferences.getString(REPOS, null);
        if (json != null) {
            List<Repo> saved = gson.fromJson(json, new TypeToken<List<Repo>>() {
            }.getType());
            if (saved != null)
                this.repos.addAll(saved);
        }
        return getRepos();
    }

    public void save() {
        String json = gson.toJson(repos);
        sharedPreferences.edit().putString(REPOS, json).apply();
    }

    public List<Repo> getRepos() {
        return repos;
    }
}
