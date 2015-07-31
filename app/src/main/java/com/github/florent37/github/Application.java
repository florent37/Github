package com.github.florent37.github;

import com.github.florent37.github.dagger.context.ContextComponent;
import com.github.florent37.github.dagger.context.DaggerContextComponent;
import com.github.florent37.github.dagger.DaggerGithubComponent;
import com.github.florent37.github.dagger.GithubComponent;
import com.github.florent37.github.dagger.context.ContextModule;

/**
 * Created by florentchampigny on 03/06/15.
 */
public class Application extends android.app.Application {

    private static Application application;
    private GithubComponent githubComponent;
    private ContextComponent contextComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Application.application = this;

        contextComponent = DaggerContextComponent.builder().contextModule(new ContextModule(this)).build();
        this.githubComponent = DaggerGithubComponent.builder()
                .contextComponent(contextComponent)
                .build();
    }

    public static Application getApplication() {
        return application;
    }

    public GithubComponent getGithubComponent() {
        return githubComponent;
    }

    public ContextComponent getContextComponent() {
        return contextComponent;
    }
}
