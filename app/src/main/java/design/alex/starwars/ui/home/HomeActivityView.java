package design.alex.starwars.ui.home;

import java.util.List;

import design.alex.starwars.data.model.entity.People;

public interface HomeActivityView {

    /**
     * Показать лоадер
     */
    void showProgress();

    /**
     * Показать контент
     */
    void showContent();

    /**
     * Показать ошибку
     */
    void showError();

    /**
     * Показать лоадер в списке
     */
    void showLoader();

    /**
     * Спрятать лоадер в списке
     */
    void hideLoader();

    /**
     * Установки полной загрузки списка
     * @param isFullLoaded - список загружен?
     */
    void setFullLoaded(boolean isFullLoaded);

    /**
     * Добавление всех персонажей
     * @param peopleList - персонажи
     */
    void addPeoples(List<People> peopleList);

    /**
     * Открыть карточку персонажа
     * @param id - Id персонажа
     */
    void openCardActivity(long id);

    /**
     * Загрузка окончена
     * @param loading - статус
     */
    void setLoading(boolean loading);
}
