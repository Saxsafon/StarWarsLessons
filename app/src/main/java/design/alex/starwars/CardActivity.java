package design.alex.starwars;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class CardActivity extends AppCompatActivity {

    public static final String PARAM_ID = "star.wars.card.param.id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);
        //mPersonNameTextView = findViewById(R.id.person_name_text_view);
    }
}
