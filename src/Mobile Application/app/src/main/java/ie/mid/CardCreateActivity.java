package ie.mid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import ie.mid.enums.CardStatus;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Field;
import ie.mid.pojo.IdentityType;

public class CardCreateActivity extends AppCompatActivity {

    private List<String> cardFields;
    private List<String> fieldData;
    private IdentityType identityType;
    private List<TextInputEditText> fields;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_card_create);
        getSupportActionBar().setTitle("Card Creation");

        Intent intent = getIntent();
        boolean isUpdate = intent.getBooleanExtra("ISUPDATE", false);
        userId = intent.getStringExtra("userId");
        if (isUpdate) {
            getSupportActionBar().setTitle("Card Update");
            String cardId = intent.getStringExtra("CARD");
            getCardData(cardId);
        } else {
            identityType = intent.getParcelableExtra("card");
            getSupportActionBar().setTitle(identityType.getName());
            getCardFields();
        }
        Uri imageUri = Uri.parse(identityType.getCoverImg());
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.coverImg);
        draweeView.setImageURI(imageUri);

        fields = new ArrayList<>();
        float d = getResources().getDisplayMetrics().density;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.form_layout);
        for (int i = 0; i < cardFields.size(); i++) {
            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setId(i + 1);
            RelativeLayout.LayoutParams textInputLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            textInputLayoutParams.setMarginStart((int) (d * 15));
            textInputLayoutParams.setMarginEnd((int) (d * 15));
            if (i > 0)
                textInputLayoutParams.addRule(RelativeLayout.BELOW, i);
            textInputLayout.setLayoutParams(textInputLayoutParams);
            LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            TextInputEditText editText = new TextInputEditText(this);
            editText.setLayoutParams(editTextLayoutParams);
            editText.setHint(cardFields.get(i));
            editText.setLines(1);
            if (isUpdate) {
                editText.setText(fieldData.get(i));
            }
            textInputLayout.addView(editText);
            relativeLayout.addView(textInputLayout);
            fields.add(editText);
        }
        Button button = (Button) findViewById(R.id.create_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCard();
            }
        });
    }

    public void verifyCard() {
        List<String> entryList = new ArrayList<>();
        for (TextInputEditText editText : fields) {
            if (editText.getText().length() > 0) {
                entryList.add(editText.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Fields Left Empty", Toast.LENGTH_LONG).show();
                return;
            }
        }
        submitCard(entryList);
    }

    public void submitCard(List<String> entryList) {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        handler.createCard(identityType.getId(),
                identityType.getName(),
                userId, getFieldTitleString(identityType.getFields()),
                getFieldValueString(entryList),
                CardStatus.NOT_VERIFIED.toString(),
                identityType.getCoverImg()
        );
        handler.close();
        Intent intent = new Intent(CardCreateActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    private String getFieldValueString(List<String> entryList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String entry : entryList) {
            String string = entry + ",";
            stringBuilder.append(string);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String getFieldTitleString(List<Field> fieldList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fieldList) {
            String fieldString = field.getName() + ":" + field.getType() + ",";
            stringBuilder.append(fieldString);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public void getCardFields() {
        cardFields = new ArrayList<>();
        for (Field field : identityType.getFields()) {
            cardFields.add(field.getName());
        }
    }

    public void getCardData(String cardId) {
        //TODO look at user card data and get the fields and field data
    }
}
