package com.github.florent37.github.repo.repository;

import com.github.florent37.github.repo.Repo;
import com.github.florent37.github.repo.RepoManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by florentchampigny on 22/02/2017.
 */

@Singleton
public class RepositoryLocal implements Repository {

    private final RepoManager repoManager;

    @Inject
    public RepositoryLocal(RepoManager repoManager) {
        this.repoManager = repoManager;
    }

    public Observable<List<Repo>> save(List<Repo> repos){
        this.repoManager.addRepos(repos).save();
        return Observable.just(repos);
    }

    @Override
    public Observable<List<Repo>> loadRepos(String user) {
        repoManager.onStart(user);
        return repoManager.loadRepos();
    }
}
