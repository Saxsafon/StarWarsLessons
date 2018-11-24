package design.alex.starwars.ui.home;

import android.util.Log;

import java.util.List;

import design.alex.starwars.App;
import design.alex.starwars.data.cache.PeopleCacheTransformer;
import design.alex.starwars.data.model.entity.People;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivityPresenterImpl implements HomeActivityPresenter {

    private static final int LIMIT = 10;

    private HomeActivityView mView;
    private int mPage = 0;

    private Observer<List<People>> mRestObserver = new Observer<List<People>>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d("TAG", "onSubscribe");
        }

        @Override
        public void onNext(List<People> peopleList) {
            mView.hideLoader();
            mView.setFullLoaded(peopleList.size() < LIMIT);
            mView.addPeoples(peopleList);
            mView.showContent();
        }

        @Override
        public void onError(Throwable e) {
            mView.setLoading(false);
            mView.showError();
        }

        @Override
        public void onComplete() {
            mView.setLoading(false);
        }
    };

    @Override
    public void setView(HomeActivityView view) {
        mView = view;
    }

    @Override
    public void fetchData(int page) {
        mPage = page;
        mView.showLoader();
        loadData(mPage);
        mView.setLoading(true);
    }

    @Override
    public void onCreate() {
        mView.showProgress();
        mPage = 1;
        loadData(mPage);
    }

    @Override
    public void onClickPeople(People people) {
        mView.openCardActivity(people.getId());
    }

    /**
     * Загрузка данных из репозитория
     * @param page - страница
     */
    private void loadData(int page) {
        App.getPeopleRestService()
                .getAllPeoples(page)
                .onErrorResumeNext(throwable -> {
                    mView.setFullLoaded(true);
                    return Observable.error(throwable);
                })
                .compose(new PeopleCacheTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRestObserver);
    }
}
