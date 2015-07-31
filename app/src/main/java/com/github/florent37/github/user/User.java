package com.github.florent37.github.user;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class User {

    String login;
    String avatar_url;
    String url;
    String name;
    String email;
    int followers;
    int following;

    public User(String name, String email, String avatar_url) {
        this.avatar_url = avatar_url;
        this.name = name;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
