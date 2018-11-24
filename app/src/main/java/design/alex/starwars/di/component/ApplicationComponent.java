package design.alex.starwars.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import design.alex.starwars.App;
import design.alex.starwars.di.module.ApplicationModule;

/**
 * Компонент приложения Dagger
 */
@Singleton // Scope компонента
@Component(
        modules = { // подключенные модули компонента
                ApplicationModule.class
        }
)
public interface ApplicationComponent {

    /**
     * Получение контекста приложения
     * @return Context
     */
    Context getContext();

    /**
     * Сборка компонента приложения
     * @return ApplicationComponent
     */
    public static ApplicationComponent buildComponent(App app) {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(app))
                .build();
    }

    /**
     * Инъекция зависимостей в application
     * @param app - application
     */
    void inject(App app);
}
