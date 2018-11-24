package design.alex.starwars.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

import design.alex.starwars.App;
import design.alex.starwars.data.AppDatabase;
import design.alex.starwars.data.rest.RestApiPeoples;
import design.alex.starwars.di.module.ApplicationModule;
import design.alex.starwars.di.module.DataBaseModule;
import design.alex.starwars.di.module.NetModule;

/**
 * Компонент приложения Dagger
 */
@Singleton // Scope компонента
@Component(
        modules = { // подключенные модули компонента
                ApplicationModule.class,
                DataBaseModule.class,
                NetModule.class
        }
)
public interface ApplicationComponent {

    /**
     * Получение контекста приложения
     * @return Context
     */
    Context getContext();

    /**
     * Получение экземпляра БД
     * @return AppDatabase
     */
    AppDatabase getDataBase();

    /**
     * Полечение интерфеса для работы с Api персонажей
     * @return RestApiPeoples
     */
    RestApiPeoples getRestApiPeoples();

    /**
     * Сборка компонента приложения
     * @return ApplicationComponent
     */
    public static ApplicationComponent buildComponent(App app) {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(app))
                .dataBaseModule(new DataBaseModule(app))
                .netModule(new NetModule())
                .build();
    }

    /**
     * Инъекция зависимостей в application
     * @param app - application
     */
    void inject(App app);
}
