package com.github.florent37.github.repo.repository;

import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.repo.Repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by florentchampigny on 22/02/2017.
 */

@Singleton
public class RepositoryNetwork implements Repository {

    private final GithubAPI githubAPI;

    @Inject
    public RepositoryNetwork(GithubAPI githubAPI) {
        this.githubAPI = githubAPI;
    }

    public Observable<List<Repo>> loadRepos(String userName) {
        return githubAPI.listRepos(userName, "updated");
    }

}
