package design.alex.starwars.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import design.alex.starwars.ui.card.CardActivity;
import design.alex.starwars.ui.home.adapter.HeroRecyclerAdapter;
import design.alex.starwars.R;
import design.alex.starwars.data.model.entity.People;
import design.alex.starwars.ui.home.helpers.RecyclerScrollListener;


public class HomeActivity
        extends
        AppCompatActivity
        implements
        HeroRecyclerAdapter.Listener,
        HomeActivityView, RecyclerScrollListener.Listener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.progress) FrameLayout mProgressLayout;
    @BindView(R.id.content) FrameLayout mContentLayout;
    @BindView(R.id.error) FrameLayout mErrorLayout;
    @BindView(R.id.coordinator) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private HeroRecyclerAdapter mAdapter;
    private RecyclerScrollListener mScrollListener;

    private HomeActivityPresenter mPresenter;

    @Override
    public void onClickPeople(People people) {
        mPresenter.onClickPeople(people);
    }

    @Override
    public void showContent() {
        mContentLayout.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        mContentLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mErrorLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoader() {
        mAdapter.showProgress();
    }

    @Override
    public void hideLoader() {
        mAdapter.hideProgress();
    }

    @Override
    public void fetchData(int page) {
        mPresenter.fetchData(page);
    }

    @Override
    public void setFullLoaded(boolean isFullLoaded) {
        mScrollListener.setFullLoaded(isFullLoaded);
    }

    @Override
    public void addPeoples(List<People> peopleList) {
        mAdapter.addAll(peopleList);
    }

    @Override
    public void openCardActivity(long id) {
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra(CardActivity.PARAM_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void setLoading(boolean loading) {
        mScrollListener.setLoading(loading);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        setupList();
        setupListener();

        mPresenter = new HomeActivityPresenterImpl();
        mPresenter.setView(this);
        mPresenter.onCreate();
    }

    private void setupList() {
        mAdapter = new HeroRecyclerAdapter();
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupListener() {
        mScrollListener = new RecyclerScrollListener(mRecyclerView.getLayoutManager());
        mScrollListener.setListener(this);
        mRecyclerView.addOnScrollListener(mScrollListener);
    }
}


