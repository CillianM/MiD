package ie.mid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class CardCreateActivity extends AppCompatActivity {

    private List<String> cardFields;
    private List<String> fieldData;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_create);
        getSupportActionBar().setTitle("Card Creation");

        Intent intent = getIntent();
        isUpdate = intent.getBooleanExtra("ISUPDATE", false);
        if (isUpdate) {
            getSupportActionBar().setTitle("Card Update");
            String cardId = intent.getStringExtra("CARD");
            getCardData(cardId);
        } else {
            getCardFields();
        }
        float d = getResources().getDisplayMetrics().density;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.form_layout);
        for (int i = 0; i < cardFields.size(); i++) {
            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setId(i + 1);
            RelativeLayout.LayoutParams textInputLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            textInputLayoutParams.setMarginStart((int) (d * 15));
            textInputLayoutParams.setMarginEnd((int) (d * 15));
            if (i == 0)
                textInputLayoutParams.addRule(RelativeLayout.BELOW, R.id.description);
            else
                textInputLayoutParams.addRule(RelativeLayout.BELOW, i);
            textInputLayout.setLayoutParams(textInputLayoutParams);
            LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            EditText editText = new EditText(this);
            editText.setLayoutParams(editTextLayoutParams);
            editText.setHint(cardFields.get(i));
            if (isUpdate) {
                editText.setText(fieldData.get(i));
            }
            textInputLayout.addView(editText);
            relativeLayout.addView(textInputLayout);
        }
        Button button = (Button) findViewById(R.id.create_button);
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, (int) (d * 60), 0, 0);
        buttonLayoutParams.addRule(RelativeLayout.BELOW, cardFields.size());
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        button.setLayoutParams(buttonLayoutParams);
    }

    public void getCardFields() {
        //TODO get the standard card fields for this type of card and instantiate the field data as empty
        cardFields = new ArrayList<>();
        cardFields.add("Field 1");
        cardFields.add("Field 2");
        cardFields.add("Field 3");
    }

    public void getCardData(String cardId) {
        //TODO look at user card data and get the fields and field data
    }
}
