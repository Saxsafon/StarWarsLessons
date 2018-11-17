package design.alex.starwars.ui.splash;

public interface SplashScreenActivityPresenter {

    /**
     * Сеттер view
     * @param view - view страницы
     */
    void setView(SplashScreenActivityView view);

    /**
     * Обработка запуска активити
     */
    void onCreate();

    /**
     * Обработка остановки анимации
     */
    void onStopAnimation();
}
