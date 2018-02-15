package ie.mid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ie.mid.adapter.CardSelectAdapter;
import ie.mid.async.IdentityTypeGetter;
import ie.mid.backend.IdentityTypeService;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.IdentityTaskCompleted;
import ie.mid.model.AvailableCard;
import ie.mid.model.CardType;
import ie.mid.pojo.IdentityType;
import ie.mid.util.InternetUtil;

public class CardSelectActivity extends AppCompatActivity implements IdentityTaskCompleted {

    GridView gridView;
    List<IdentityType> identityTypeList;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        setContentView(R.layout.activity_card_select);
        getSupportActionBar().setTitle("Identity Selection");

        CardView cardView = (CardView) findViewById(R.id.card_view);
        CardView.LayoutParams layoutParams = (CardView.LayoutParams)
                cardView.getLayoutParams();
        layoutParams.height = 400;
        cardView.setLayoutParams(layoutParams);

        TextView description = (TextView) findViewById(R.id.desciption_text);
        description.setText(getResources().getString(R.string.select_description));
        if(InternetUtil.isNetworkAvailable(getApplicationContext())) {
            new IdentityTypeGetter(getApplicationContext(),this).execute();
        }
        else{
            TextView cardText = (TextView) findViewById(R.id.card_info);
            findViewById(R.id.card_progress).setVisibility(View.GONE);
            cardText.setText("No Internet Connection");
        }
    }

    @Override
    public void onTaskComplete(List<IdentityType> identityTypes) {
        TextView cardText = (TextView) findViewById(R.id.card_info);
        findViewById(R.id.card_progress).setVisibility(View.GONE);
        if(identityTypes != null && !identityTypes.isEmpty()) {
            ArrayList<AvailableCard> listOfData = new ArrayList<>();
            for (IdentityType identityType : identityTypes) {
                AvailableCard availableCard = new AvailableCard(identityType.getName(), identityType.getIconImg());
                listOfData.add(availableCard);
            }
            identityTypeList = identityTypes;
            gridView = (GridView) findViewById(R.id.card_select);
            gridView.setAdapter(new CardSelectAdapter(this, listOfData));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(notAlreadyPresent(identityTypeList.get(i))) {
                        Intent intent = new Intent(getApplicationContext(), CardCreateActivity.class);
                        intent.putExtra("card", identityTypeList.get(i));
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();
                    }else{
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CardSelectActivity.this,R.style.AlertDialog);
                        builder.setMessage("Current identity identity already exists on this profile").setPositiveButton("Ok", dialogClickListener).show();
                    }
                }
            });
            findViewById(R.id.card_select).setVisibility(View.VISIBLE);
        }
        else if(identityTypes != null && identityTypes.isEmpty()){
            cardText.setText("List empty");
            cardText.setVisibility(View.VISIBLE);
        }
        else{
            cardText.setText("Unable To Retrieve Listing");
            cardText.setVisibility(View.VISIBLE);
        }
    }

    private boolean notAlreadyPresent(IdentityType identityType) {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        return handler.getUserCardByIdentityType(userId, identityType.getId()) == null;
    }
}
