package com.github.florent37.github.repo.repository;

import com.github.florent37.github.repo.Repo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by florentchampigny on 22/02/2017.
 */

public class RepositoryProxy implements Repository {

    private final RepositoryNetwork repositoryNetwork;
    private final RepositoryLocal repositoryLocal;

    public RepositoryProxy(RepositoryNetwork repositoryNetwork, RepositoryLocal repositoryLocal) {
        this.repositoryNetwork = repositoryNetwork;
        this.repositoryLocal = repositoryLocal;
    }

    @Override
    public Observable<List<Repo>> loadRepos(String user) {
        final Observable<List<Repo>> observable = repositoryLocal.loadRepos(user);
        return Observable
                .merge(
                        observable,
                        repositoryNetwork.loadRepos(user).flatMap(repositoryLocal::save)
                )
                .flatMap(Observable::fromIterable)
                .toSortedList(Repo::compareTo)
                .toObservable();
    }
}
