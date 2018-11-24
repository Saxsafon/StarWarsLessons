package design.alex.starwars.ui.card;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import design.alex.starwars.R;
import design.alex.starwars.databinding.CardActivityBinding;


public class CardActivity extends AppCompatActivity {

    public static final String PARAM_ID = "star.wars.card.param.id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardActivityBinding binding = DataBindingUtil.setContentView(
                this, R.layout.card_activity
        );
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CardActivityViewModel viewModel = new CardActivityViewModel();
        binding.setViewModel(viewModel);
        viewModel.loadData(getPeopleId());
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

    private Long getPeopleId() {
        return getIntent().getLongExtra(PARAM_ID, 0);
    }
}