package com.github.florent37.github.dagger;

import android.app.Application;

import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.MainApplication;
import com.github.florent37.github.MainActivity;
import com.github.florent37.github.user.UserManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        GithubModule.class,
        AndroidInjectionModule.class,
        MainActivityModule.class
})
public interface GithubComponent {

    void inject(MainApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        GithubComponent build();
    }

    GithubAPI githubApi();
    UserManager userManager();
}