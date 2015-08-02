package com.github.florent37.github.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.carpaccio.Carpaccio;
import com.github.florent37.carpaccio.controllers.adapter.CarpaccioRecyclerViewAdapter;
import com.github.florent37.carpaccio.controllers.adapter.Holder;
import com.github.florent37.carpaccio.controllers.adapter.RecyclerViewCallbackAdapter;
import com.github.florent37.github.Application;
import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.R;
import com.github.florent37.github.adapter.RepoHolder;
import com.github.florent37.github.user.UserManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        carpaccio.onItemClick("repo", (o, i, view1) -> {
            Toast.makeText(getActivity(), "position " + i, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        repoManager.onStart(getActivity());

        if (repoManager.load() != null)
            carpaccio.mapList("repo", repoManager.getRepos());

        CarpaccioRecyclerViewAdapter adapter = carpaccio.getAdapter("repo");
        if (adapter != null)
            adapter.setRecyclerViewCallback(new RecyclerViewCallbackAdapter<Repo>() {
                @Override
                public Holder<Repo> onCreateViewHolder(View view, int viewType) {
                    return new RepoHolder(view);
                }
            });

        worker = Schedulers.io().createWorker();
        worker.schedule(ListRepoFragment.this::getRepos, 1, TimeUnit.MINUTES);
        getRepos();
    }

    @Override
    public void onStop() {
        super.onStop();
        worker.unsubscribe();
        repoManager.onStop();
    }

    protected void getRepos() {

        if (userManager.getUser() != null)
            githubAPI.listRepos(userManager.getUser().getLogin())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(null)

                            //sort
                    .flatMap(Observable::from)
                    .toSortedList(Repo::compareTo)

                            //add to repo manager
                    .flatMap(Observable::from)
                    .map(repoManager::addRepo)
                    .toList()

                            //sort
                    .flatMap(Observable::from)
                    .toSortedList(Repo::compareTo)

                    .finallyDo(repoManager::save)

                    .subscribe(repos -> {
                        if (repos != null)
                            carpaccio.mapList("repo", repos);
                        //swipeRefresh.setRefreshing(false);
                    });
    }

}
