package com.github.florent37.github.repo;

import com.github.florent37.github.AbstractPresenter;
import com.github.florent37.github.repo.repository.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by florentchampigny on 22/02/2017.
 */

public class RepoPresenter extends AbstractPresenter<RepoPresenter.View> {

    private final Repository repository;
    private final Scheduler.Worker worker;

    @Inject
    public RepoPresenter(Repository repository) {
        super();
        this.repository = repository;

        this.worker = Schedulers.newThread().createWorker();
    }

    public void start(String userName) {
        getRepos(userName);
    }


    protected void getRepos(String userName) {
        repository
                .loadRepos(userName)

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

                .doOnSubscribe(s -> call(s))

                .subscribe(view::displayRepos);
    }

    public interface View {
        void displayRepos(List<Repo> repos);
    }

}
