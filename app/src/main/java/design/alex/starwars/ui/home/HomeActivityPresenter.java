package design.alex.starwars.ui.home;

import design.alex.starwars.data.model.entity.People;

public interface HomeActivityPresenter {

    /**
     * Сеттер view
     * @param view - страница
     */
    void setView(HomeActivityView view);

    /**
     * Подгрузка данных
     */
    void fetchData(int page);

    /**
     * Запуск активити
     */
    void onCreate();

    /**
     * Обработка клика на персонажа
     * @param people - персонаж
     */
    void onClickPeople(People people);
}
