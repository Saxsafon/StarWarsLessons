package design.alex.starwars.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль приложения
 */
@Module
public class ApplicationModule {

    /** Контекст приложения */
    private Context mContext;

    public ApplicationModule(Application app) {
        mContext = app.getApplicationContext();
    }

    @Singleton
    @Provides
    Context getContext() {
        return mContext;
    }
}
