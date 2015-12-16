package com.github.florent37.github.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.carpaccio.Carpaccio;
import com.github.florent37.carpaccio.controllers.adapter.Holder;
import com.github.florent37.carpaccio.controllers.adapter.OnItemClickListenerAdapter;
import com.github.florent37.carpaccio.controllers.adapter.RecyclerViewCallbackAdapter;
import com.github.florent37.github.Application;
import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.R;
import com.github.florent37.github.adapter.RepoHolder;
import com.github.florent37.github.user.UserManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class ListRepoFragment extends Fragment {

    @Inject
    RepoManager repoManager;
    @Inject
    UserManager userManager;
    @Inject
    GithubAPI githubAPI;

    @Bind(R.id.carpaccio)
    Carpaccio carpaccio;

    Scheduler.Worker worker;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static Fragment newInstance() {
        return new ListRepoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_repo_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Application.app().component().inject(this);

        carpaccio.onItemClick("repo", new OnItemClickListenerAdapter() {
            @Override
            public void onItemClick(Object item, int position, Holder holder) {
                Toast.makeText(getActivity(), "position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        repoManager.onStart(getActivity());

        compositeSubscription.add(
                repoManager.loadRepos()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(repos -> {
                            carpaccio.mapList("repo", repos);
                        }));

        if (carpaccio.getAdapter("repo") != null)
            carpaccio.getAdapter("repo").setRecyclerViewCallback(new RecyclerViewCallbackAdapter() {
                @Override
                public Holder onCreateViewHolder(View view, int viewType) {
                    return new RepoHolder(view);
                }
            });

        worker = Schedulers.io().createWorker();
        worker.schedule(ListRepoFragment.this::getReposFromNetwork, 1, TimeUnit.MINUTES);
        getReposFromNetwork();
    }

    @Override
    public void onStop() {
        super.onStop();
        worker.unsubscribe();
        repoManager.onStop();

        compositeSubscription.clear();
    }

    protected void getReposFromNetwork() {

        if (userManager.getUser() != null) {
            compositeSubscription.add(githubAPI.listRepos(userManager.getUser().getLogin())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(null)

                            //add to repo manager
                    .flatMap(Observable::from)
                    .toSortedList(Repo::compareTo)

                    .subscribe(new Observer<List<Repo>>() {
                        @Override public void onCompleted() {
                            repoManager.save();
                        }

                        @Override public void onError(Throwable e) {

                        }

                        @Override public void onNext(List<Repo> repos) {
                            if (repos != null) {
                                repoManager.addRepos(repos).save();
                                carpaccio.mapList("repo", repos);
                            }
                        }
                    }));
        }
    }

}
