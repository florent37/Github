package com.github.florent37.github.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.github.AbstractFragment;
import com.github.florent37.github.R;
import com.github.florent37.github.dagger.Injectable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class ListRepoFragment extends AbstractFragment implements Injectable {

    static final String USERNAME = "userName";

    @Inject RepoPresenter repoPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RepoPresenter.View view = new RepoPresenter.View() {
        @Override
        public void displayRepos(List<Repo> repos) {
            ((RepoAdapter) recyclerView.getAdapter()).setItems(repos);
        }
    };

    public static Fragment newInstance(String userName) {
        final ListRepoFragment fragment = new ListRepoFragment();
        final Bundle args = new Bundle();
        args.putString(USERNAME, userName);
        fragment.setArguments(args);
        return fragment;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RepoAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //repoPresenter.bind(getLifecycle(), this.view);
        //repoPresenter.start(getArguments().getString(USERNAME));
    }

}
