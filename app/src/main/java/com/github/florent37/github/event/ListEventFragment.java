package com.github.florent37.github.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.carpaccio.Carpaccio;
import com.github.florent37.github.Application;
import com.github.florent37.github.GithubAPI;
import com.github.florent37.github.R;
import com.github.florent37.github.user.UserManager;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by florentchampigny on 01/08/15.
 */
public class ListEventFragment extends Fragment {

    @Inject
    GithubAPI githubAPI;

    @Inject
    UserManager userManager;

    @Bind(R.id.carpaccio)
    Carpaccio carpaccio;

    public static Fragment newInstance() {
        return new ListEventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_event_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Application.app().component().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (userManager.getUser() != null)
            githubAPI.userEvents(userManager.getUser().getLogin())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(null)
                    .subscribe(events -> {
                        if (events != null) carpaccio.mapList("event", events);
                    });
    }
}
