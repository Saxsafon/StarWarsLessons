package design.alex.starwars.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import design.alex.starwars.data.AppDatabase;

/**
 *  Модуль для работы с базой данных
 */
@Module
public class DataBaseModule {

    /** Экземпляр БД */
    private AppDatabase mAppDatabase;

    public DataBaseModule(Context context) {
        mAppDatabase = Room.databaseBuilder(context, AppDatabase.class, "star-wars").build();
    }

    @Singleton
    @Provides
    AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
