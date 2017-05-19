package com.github.florent37.github;

import com.github.florent37.github.repo.Repo;
import com.github.florent37.github.user.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by florentchampigny on 30/07/15.
 */
public interface GithubAPI {

    @GET("/users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user, @Query("sort") String sort);

    @Headers("Cache-Control: max-age=640000")
    @FormUrlEncoded
    @GET("/users/{user}")
    Observable<User> user(@Path("user") String user);

}
