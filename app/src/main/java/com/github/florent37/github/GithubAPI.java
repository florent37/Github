package com.github.florent37.github;

import com.github.florent37.github.event.Event;
import com.github.florent37.github.repo.Repo;
import com.github.florent37.github.user.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by florentchampigny on 30/07/15.
 */
public interface GithubAPI {

    @GET("/users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);

    @GET("/users/{user}")
    Observable<User> user(@Path("user") String user);

    @GET("/users/{user}/received_events")
    Observable<List<Event>> userEvents(@Path("user") String user);
}
