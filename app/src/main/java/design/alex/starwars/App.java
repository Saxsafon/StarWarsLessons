package design.alex.starwars;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import design.alex.starwars.data.AppDatabase;
import design.alex.starwars.data.rest.RestApiPeoples;
import design.alex.starwars.di.component.ApplicationComponent;
import okhttp3.Cache;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private ApplicationComponent mAppComponent;

    // Сервис для получения персонажей
    private static RestApiPeoples mPeopleRestService;

    private static AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = ApplicationComponent.buildComponent(this);
        mAppComponent.inject(this);

        buildRest();
        buildDb();
    }

    private void buildDb() {
        mAppDatabase = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "star-wars")
                .build();
    }

    private void buildRest() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://swapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mPeopleRestService = retrofit.create(RestApiPeoples.class);
    }

    public static RestApiPeoples getPeopleRestService() {
        return mPeopleRestService;
    }

    public static AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
