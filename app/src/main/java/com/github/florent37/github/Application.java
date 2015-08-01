package com.github.florent37.github;

import com.github.florent37.github.dagger.DaggerGithubComponent;
import com.github.florent37.github.dagger.GithubComponent;
import com.github.florent37.github.dagger.ContextModule;

/**
 * Created by florentchampigny on 03/06/15.
 */
public class Application extends android.app.Application {

    private static Application application;
    private GithubComponent githubComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Application.application = this;

        this.githubComponent = DaggerGithubComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static Application getApplication() {
        return application;
    }

    public GithubComponent getGithubComponent() {
        return githubComponent;
    }

}
