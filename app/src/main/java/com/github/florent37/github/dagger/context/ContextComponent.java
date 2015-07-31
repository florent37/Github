package com.github.florent37.github.dagger.context;

import android.content.Context;

import com.github.florent37.github.dagger.context.ContextModule;

import dagger.Component;

/**
 * Created by florentchampigny on 31/07/15.
 */
@Component(modules = ContextModule.class)
public interface ContextComponent {

    Context context();

}
