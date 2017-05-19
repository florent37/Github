package com.github.florent37.github.repo.repository;

import com.github.florent37.github.repo.Repo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by florentchampigny on 22/02/2017.
 */

public interface Repository {

    Observable<List<Repo>> loadRepos(String user);

}
