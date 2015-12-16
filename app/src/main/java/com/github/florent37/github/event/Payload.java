package com.github.florent37.github.event;

import com.github.florent37.github.repo.Forkee;

/**
 * Created by florentchampigny on 31/07/15.
 */
public class Payload {
    String action;
    Forkee forkee;

    public Forkee getForkee() {
        return forkee;
    }

    public void setForkee(Forkee forkee) {
        this.forkee = forkee;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName(){
        if(forkee != null)
            return "forked";
        else return action;
    }
}
