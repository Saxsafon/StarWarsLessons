package design.alex.starwars;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import design.alex.starwars.model.entity.People;
import design.alex.starwars.model.rest.RawPeople;
import design.alex.starwars.model.rest.RawResult;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeActivity
        extends
        AppCompatActivity implements HeroRecyclerAdapter.Listener {

    private static final int LIMIT = 10;

    FrameLayout mProgressLayout;
    FrameLayout mContentLayout;
    FrameLayout mErrorLayout;

    RecyclerView mRecyclerView;

    private HeroRecyclerAdapter mAdapter;
    private RecyclerScrollListener mScrollListener;

    private Observer<List<People>> mRestObserver = new Observer<List<People>>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d("TAG", "onSubscribe");
        }

        @Override
        public void onNext(List<People> peopleList) {
            mAdapter.hideProgress();
            mScrollListener.setFullLoaded(peopleList.size() < LIMIT);
            mAdapter.addAll(peopleList);
            showContent();
            Log.d("TAG", "onNext");
        }

        @Override
        public void onError(Throwable e) {
            mScrollListener.setLoading(false);
            showError();
            Log.d("TAG", "onError");
        }

        @Override
        public void onComplete() {
            mScrollListener.setLoading(false);
            Log.d("TAG", "onComplete");
        }
    };

    @Override
    public void onClickPeople(People people) {
        startCardActivity(people.getId());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        bindViews();
        setupList();
        setupListener();
        showProgress();
        loadData(1);
    }

    private void startCardActivity(Long id) {
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra(CardActivity.PARAM_ID, id);
        startActivity(intent);
    }

    private void bindViews() {
        mProgressLayout = findViewById(R.id.progress);
        mContentLayout = findViewById(R.id.content);
        mErrorLayout = findViewById(R.id.error);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    private void setupList() {
        mAdapter = new HeroRecyclerAdapter();
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupListener() {
        mScrollListener = new RecyclerScrollListener();
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    private void showContent() {
        mContentLayout.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
    }

    private void showProgress() {
        mContentLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    private void showError() {
        mErrorLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.GONE);
    }

    private void loadData(int page) {
        ((App)getApplication())
                .getPeopleRestService()
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
                    people.setMass(Integer.getInteger(rawPeople.getMass()));
                } catch (NumberFormatException ignored) {}

                people.setHairColor(rawPeople.getHairColor());
                people.setEyeColor(rawPeople.getEyeColor());
                people.setBirthYear(rawPeople.getBirthYear());
                people.setGender(rawPeople.getGender());
                people.setHomeWorld(rawPeople.getHoumeWorld());
                ((App)getApplication()).getAppDatabase().getPeopleDao().insert(people);
                peopleList.add(people);
            }
        }
        return peopleList;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private class RecyclerScrollListener extends RecyclerView.OnScrollListener {

        private Integer mTotalCount;
        private Integer mLastItem;
        private Integer mThreshold = 5;
        private Boolean mIsLoading = false;
        private Boolean mIsFullLoaded = false;

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mRecyclerView.getLayoutManager() == null) {
                return;
            }
            mTotalCount = mRecyclerView.getLayoutManager().getItemCount();
            mLastItem = ((LinearLayoutManager)mRecyclerView.getLayoutManager())
                    .findLastVisibleItemPosition();
            if (!mIsLoading && mTotalCount < (mLastItem + mThreshold) && !mIsFullLoaded) {
                mAdapter.showProgress();
                loadData((mTotalCount / 10) + 1);
                setLoading(true);
            }
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        public void setLoading(Boolean loading) {
            mIsLoading = loading;
        }

        public void setFullLoaded(Boolean fullLoaded) {
            mIsFullLoaded = fullLoaded;
        }
    }
}


