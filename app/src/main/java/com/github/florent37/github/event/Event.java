package com.github.florent37.github.event;

import com.github.florent37.github.repo.Repo;
import com.github.florent37.github.user.User;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class Event {

    String id;
    String type;
    User actor;
    Repo repo;
    Payload payload;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
