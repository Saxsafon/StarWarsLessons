package design.alex.starwars;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import design.alex.starwars.model.entity.People;
import design.alex.starwars.ui.widgets.TemplateTextView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class CardActivity extends AppCompatActivity {

    public static final String PARAM_ID = "star.wars.card.param.id";

    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.collapsing_image_view) ImageView mCollapsingImageView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.person_birth_text_view) TemplateTextView mHeroBirthTextView;
    @BindView(R.id.person_height_text_view) TemplateTextView mHeroHeightTextView;
    @BindView(R.id.person_mass_text_view) TemplateTextView mHeroMassTextView;
    @BindView(R.id.person_gender_text_view) TemplateTextView mHeroGenderTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setTitleEnabled(false);

        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Consumer<People> mObsever = new Consumer<People>() {

        @Override
        public void accept(People people) throws Exception {
            Glide.with(getApplicationContext()).load(people.getImageUrl())
                    .into(mCollapsingImageView);

            mToolbar.setTitle(people.getName());
            mHeroBirthTextView.setTemplatedText(people.getBirthYear());
            mHeroHeightTextView.setTemplatedText(String.valueOf(people.getHeight()));
            mHeroMassTextView.setTemplatedText(String.valueOf(people.getMass()));
            mHeroGenderTextView.setTemplatedText(people.getGender());
        }
    };

    private void loadData() {
        App.getAppDatabase()
                .getPeopleDao()
                .getPeople(getPeopleId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObsever);
    }

    private Long getPeopleId() {
        return getIntent().getLongExtra(PARAM_ID, 0);
    }
}
