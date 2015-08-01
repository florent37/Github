package com.github.florent37.github.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by florentchampigny on 31/07/15.
 */
@Module
public class ContextModule{

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
