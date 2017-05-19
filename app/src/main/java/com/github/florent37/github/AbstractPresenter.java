package com.github.florent37.github;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.github.florent37.github.repo.RepoPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.arch.lifecycle.Lifecycle.Event.ON_DESTROY;

/**
 * Created by florentchampigny on 19/05/2017.
 */

public abstract class AbstractPresenter<V> implements LifecycleObserver {

    private final CompositeDisposable compositeDisposable;
    protected V view;

    public AbstractPresenter() {
        this.compositeDisposable = new CompositeDisposable();
    }

    public void bind(LifecycleRegistry lifecycleRegistry, V view) {
        this.view = view;
        lifecycleRegistry.addObserver(this);
    }

    protected void call(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @OnLifecycleEvent(ON_DESTROY)
    public void onViewDestroyed() {
        unbind();
    }

    public void unbind() {
        this.view = null;
        compositeDisposable.clear();
    }
}
