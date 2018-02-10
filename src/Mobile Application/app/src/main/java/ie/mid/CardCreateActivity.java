package ie.mid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ie.mid.enums.CardStatus;
import ie.mid.enums.DataType;
import ie.mid.fragments.DatePickerFragment;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.CardType;
import ie.mid.model.Field;
import ie.mid.pojo.IdentityType;

public class CardCreateActivity extends AppCompatActivity {

    private List<String> cardFields;
    private IdentityType identityType;
    private List<TextInputEditText> fields;
    private List<DatePickerDialog> datePickerDialogs;
    private String userId;
    private CardType cardType;
    private boolean isUpdate;
    int exp_year, exp_month, exp_day,birth_year, birth_month, birth_day;
    boolean expSet,birthSet,birthUsed,expUsed;
    DatePickerDialog.OnDateSetListener exp_dateListener,birth_dateListener;
    Button expButton,birthButton;
    int expIndex,birthIndex;
    private final int DATE_PICKER_EXP = 0;
    private final int DATE_PICKER_BIRTH = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_card_create);
        getSupportActionBar().setTitle("Card Creation");

        Intent intent = getIntent();
        isUpdate = intent.getBooleanExtra("isUpdate", false);
        userId = intent.getStringExtra("userId");
        if (isUpdate) {
            String cardId = intent.getStringExtra("cardId");
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
            handler.open();
            cardType = handler.getUserCard(cardId);
            handler.close();
            getSupportActionBar().setTitle(cardType.getTitle());
            constructUpdateForm();
        } else {
            identityType = intent.getParcelableExtra("card");
            getSupportActionBar().setTitle(identityType.getName());
            getCardFields();
            constructCreateForm();
        }
        Button button = (Button) findViewById(R.id.create_button);
        if(isUpdate)
            button.setText("Update");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCard();
            }
        });
        birth_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                birthSet = true;
                birth_year = i;
                birth_month = i1 + 1;
                birth_day = i2;
                updateDisplays();
            }
        };
        exp_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                expSet = true;
                exp_year = i;
                exp_month = i1 + 1;
                exp_day = i2;
                updateDisplays();
            }
        };
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,CardSelectActivity.class));
        finish();
    }

    private void updateDisplays() {
        if(birthSet) {
            String date = birth_day + "/" + birth_month + "/" + birth_year;
            birthButton.setText(date);
        }
        if(expSet){
            String date = exp_day + "/" + exp_month + "/" + exp_year;
            expButton.setText(date);
        }
    }

    private void setBirthDate(String date){
        String[] array = date.split("/");
        birth_day = Integer.parseInt(array[0]);
        birth_month = Integer.parseInt(array[1]);
        birth_year =Integer.parseInt(array[2]);
    }

    private void setExpiryDate(String date){
        String[] array = date.split("/");
        exp_day = Integer.parseInt(array[0]);
        exp_month = Integer.parseInt(array[1]);
        exp_year =Integer.parseInt(array[2]);
    }

    public void constructUpdateForm() {
        Uri imageUri = Uri.parse(cardType.getImageUrl());
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.coverImg);
        draweeView.setImageURI(imageUri);

        fields = new ArrayList<>();
        float d = getResources().getDisplayMetrics().density;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.form_layout);
        for (int i = 0; i < cardType.getDataList().size(); i++) {
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

            boolean isBirthField = DataType.BIRTHDAY.toString().equals(cardType.getDataList().get(i).getFieldType());
            boolean isExpField = DataType.EXPIRY.toString().equals(cardType.getDataList().get(i).getFieldType());
            if(!isBirthField && !isExpField) {

                TextInputEditText editText = new TextInputEditText(this);
                editText.setLayoutParams(editTextLayoutParams);
                editText.setHint(cardType.getDataList().get(i).getFieldTitle());
                editText.setText(cardType.getDataList().get(i).getFieldEntry());
                editText.setLines(1);
                textInputLayout.addView(editText);
                relativeLayout.addView(textInputLayout);
                fields.add(editText);
            }
            else{
                if(DataType.BIRTHDAY.toString().equals(cardType.getDataList().get(i).getFieldType())){
                    birthIndex = i;
                    birthUsed = true;
                    birthSet = true;
                    setBirthDate(cardType.getDataList().get(i).getFieldEntry());
                    birthButton = new Button(this);
                    birthButton.setText(cardType.getDataList().get(i).getFieldEntry());
                    birthButton.setLayoutParams(editTextLayoutParams);
                    birthButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(DATE_PICKER_BIRTH);
                        }
                    });
                    textInputLayout.addView(birthButton);
                }
                else {
                    expIndex = i;
                    expUsed = true;
                    expSet = true;
                    setExpiryDate(cardType.getDataList().get(i).getFieldEntry());
                    expButton = new Button(this);
                    expButton.setText(cardType.getDataList().get(i).getFieldEntry());
                    expButton.setLayoutParams(editTextLayoutParams);
                    expButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(DATE_PICKER_EXP);
                        }
                    });
                    textInputLayout.addView(expButton);
                }
                relativeLayout.addView(textInputLayout);
            }
        }
    }

    public void constructCreateForm(){
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

            boolean isBirthField = DataType.BIRTHDAY.toString().equals(identityType.getFields().get(i).getType());
            boolean isExpField = DataType.EXPIRY.toString().equals(identityType.getFields().get(i).getType());
            if(!isBirthField && !isExpField) {

                TextInputEditText editText = new TextInputEditText(this);
                editText.setLayoutParams(editTextLayoutParams);
                editText.setHint(cardFields.get(i));
                editText.setLines(1);
                textInputLayout.addView(editText);
                relativeLayout.addView(textInputLayout);
                fields.add(editText);
            }
            else{
                if(DataType.BIRTHDAY.toString().equals(identityType.getFields().get(i).getType())){
                    birthIndex = i;
                    birthUsed = true;
                    birthButton = new Button(this);
                    birthButton.setText("Set Birth Date");
                    birthButton.setLayoutParams(editTextLayoutParams);
                    birthButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(DATE_PICKER_BIRTH);
                        }
                    });
                    textInputLayout.addView(birthButton);
                }
                else {
                    expIndex = i;
                    expButton = new Button(this);
                    expButton.setText("Set Expiry Date");
                    expButton.setLayoutParams(editTextLayoutParams);
                    expButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDialog(DATE_PICKER_EXP);
                        }
                    });
                    textInputLayout.addView(expButton);
                    expUsed = true;
                }
                relativeLayout.addView(textInputLayout);
            }
        }
    }

    public void verifyCard() {
        List<String> entryList = new ArrayList<>();
        for (int i = 0; i < fields.size() + getDateOffset(); i++) {
            if(i == birthIndex && birthSet){
                String date = birth_day + "/" + birth_month + "/" + birth_year;
                entryList.add(date);
            }
            else if(i == expIndex && expSet){
                String date = exp_day + "/" + exp_month + "/" + exp_year;
                entryList.add(date);
            }
            else{
                if (fields.get(i).getText().length() > 0 ) {
                    entryList.add(fields.get(i).getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Fields Left Empty", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        if(isUpdate)
            updateCard(entryList);
        else
            submitCard(entryList);
    }

    public void updateCard(List<String> entryList) {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        handler.updateCard(cardType.getId(),getFieldValueString(entryList));
        handler.close();
        Intent intent = new Intent(CardCreateActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void submitCard(List<String> entryList) {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        handler.createCard(identityType.getPartyId(),identityType.getId(),
                identityType.getName(),
                userId, getFieldTitleString(identityType.getFields()),
                getFieldValueString(entryList),
                identityType.getCoverImg(),
                CardStatus.NOT_VERIFIED.toString(),
                identityType.getVersionNumber()
        );
        handler.close();
        Intent intent = new Intent(CardCreateActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
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

    @Override
    protected Dialog onCreateDialog(int id) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        switch(id){
            case DATE_PICKER_BIRTH:
                return new DatePickerDialog(this, birth_dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            case DATE_PICKER_EXP:
                return new DatePickerDialog(this, exp_dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    public int getDateOffset() {
        int offset = 0;
        if(birthSet) offset +=1;
        if(expSet) offset += 1;
        return offset;
    }
}
