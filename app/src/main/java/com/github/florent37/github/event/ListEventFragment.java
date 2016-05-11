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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by florentchampigny on 01/08/15.
 */
public class ListEventFragment extends Fragment {

    @Inject
    GithubAPI githubAPI;

    @Inject
    UserManager userManager;

    @BindView(R.id.carpaccio)
    Carpaccio carpaccio;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

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
            compositeSubscription.add(
                    githubAPI.userEvents(userManager.getUser().getLogin())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<Event>>() {
                                @Override public void onCompleted() {

                                }

                                @Override public void onError(Throwable e) {

                                }

                                @Override public void onNext(List<Event> events) {
                                     carpaccio.mapList("event", events);
                                }
                            }));
    }

    @Override public void onStop() {
        super.onStop();
        compositeSubscription.unsubscribe();
    }
}
