package design.alex.starwars.ui.home;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import design.alex.starwars.App;
import design.alex.starwars.data.model.entity.People;
import design.alex.starwars.data.model.rest.RawPeople;
import design.alex.starwars.data.model.rest.RawResult;
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
        loadData(mPage);
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

    private void loadData(int page) {
        App.getPeopleRestService()
                .getAllPeoples(page)
                .map(result -> saveDb(result))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRestObserver);
    }

    private List<People> saveDb(RawResult result) {
        List<People> peopleList = new ArrayList<>();
        if (result != null && result.getResults() != null && !result.getResults().isEmpty()) {
            for (RawPeople rawPeople : result.getResults()) {
                People people = new People();
                String id = rawPeople.getUrl().replaceAll("[\\D+]","");
                people.setId(Long.parseLong(id));
                people.setImageUrl(
                        String.format("https://starwars-visualguide.com/assets/img/characters/%s.jpg", people.getId())
                );
                people.setName(rawPeople.getName());
                try {
                    people.setHeight(Integer.parseInt(rawPeople.getHeight()));
                } catch (NumberFormatException ignored) {}
                try {
                    people.setMass(Integer.parseInt(rawPeople.getMass()));
                } catch (NumberFormatException ignored) {}

                people.setHairColor(rawPeople.getHairColor());
                people.setEyeColor(rawPeople.getEyeColor());
                people.setBirthYear(rawPeople.getBirthYear());
                people.setGender(rawPeople.getGender());
                people.setHomeWorld(rawPeople.getHoumeWorld());
                App.getAppDatabase().getPeopleDao().insert(people);
                peopleList.add(people);
            }
        }
        return peopleList;
    }
}
