package com.github.florent37.github.dagger;

import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.event.ListEventFragment;
import com.github.florent37.github.repo.ListRepoFragment;
import com.github.florent37.github.MainActivity;
import com.github.florent37.github.repo.RepoManager;
import com.github.florent37.github.user.UserManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GithubModule.class, ContextModule.class})
public interface GithubComponent {

    GithubAPI githubApi();
    RepoManager repoManager();
    UserManager userManager();

    void inject(MainActivity mainActivity);
    void inject(ListRepoFragment listRepoFragment);
    void inject(ListEventFragment listEventFragment);
}