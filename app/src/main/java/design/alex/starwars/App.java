package design.alex.starwars;

import android.app.Application;

import design.alex.starwars.data.AppDatabase;
import design.alex.starwars.data.rest.RestApiPeoples;
import design.alex.starwars.di.component.ApplicationComponent;

public class App extends Application {

    private static ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = ApplicationComponent.buildComponent(this);
        mAppComponent.inject(this);
    }

    public static RestApiPeoples getPeopleRestService() {
        return mAppComponent.getRestApiPeoples();
    }

    public static AppDatabase getAppDatabase() {
        return mAppComponent.getDataBase();
    }
}