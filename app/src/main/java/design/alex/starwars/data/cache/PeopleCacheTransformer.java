package design.alex.starwars.data.cache;

import java.util.ArrayList;
import java.util.List;

import design.alex.starwars.App;
import design.alex.starwars.data.mappres.PeopleMapper;
import design.alex.starwars.data.model.entity.People;
import design.alex.starwars.data.model.rest.RawPeople;
import design.alex.starwars.data.model.rest.RawResult;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Трансформер потока, позволяет кешировать данные в БД из Rest.
 */
public class PeopleCacheTransformer implements ObservableTransformer<RawResult, List<People>> {

    /** Маппер персонажа из объекта rest во внутренний объект **/
    private PeopleMapper mPeopleMapper;

    public PeopleCacheTransformer() {
        mPeopleMapper = new PeopleMapper();
    }

    @Override
    public ObservableSource<List<People>> apply(io.reactivex.Observable<RawResult> observable) {
        return observable
                .map(result -> mPeopleMapper.transform(result.getResults()))
                .map(this::save)
                .onErrorResumeNext(this::cacheHandler);
    }

    /**
     * Обработка ошибки от сети. Если полявилась ошибка,
     * просто забираем все данные из базы и отображаем их
     * @param throwable - ошибка
     * @return Observable
     */
    private Observable<List<People>> cacheHandler(Throwable throwable) {
        return Observable.just(App.getAppDatabase().getPeopleDao().getAllPeoples());
    }

    /**
     * Сохранение полученных данных из сета в БД
     * @param peoples -  список полученных персонажей
     * @return List
     */
    private List<People> save(List<People> peoples) {
        if (!peoples.isEmpty()) {
            for (People people : peoples) {
                App.getAppDatabase().getPeopleDao().insert(people);
            }
        }
        return peoples;
    }
}