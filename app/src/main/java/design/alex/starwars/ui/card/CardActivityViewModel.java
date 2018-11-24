package design.alex.starwars.ui.card;

import android.annotation.SuppressLint;
import android.databinding.BaseObservable;

import design.alex.starwars.App;
import design.alex.starwars.data.model.entity.People;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardActivityViewModel extends BaseObservable {

    private People mPeople;

    CardActivityViewModel() {
        mPeople = new People.Empty();
    }

    public People getPeople() {
        return mPeople;
    }

    public void setPeople(People people) {
        mPeople = people;
        notifyChange();
    }

    @SuppressLint("CheckResult")
    public void loadData(long peopleId) {
        App.getAppDatabase()
                .getPeopleDao()
                .getPeople(peopleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setPeople);
    }
}
