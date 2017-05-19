package com.github.florent37.github;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v4.app.Fragment;

/**
 * Created by florentchampigny on 19/05/2017.
 */

public abstract class AbstractFragment extends Fragment implements LifecycleRegistryOwner {

    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
