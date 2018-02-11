package ie.mid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ie.mid.adapter.CardSelectAdapter;
import ie.mid.backend.IdentityTypeService;
import ie.mid.interfaces.IdentityTaskCompleted;
import ie.mid.model.AvailableCard;
import ie.mid.pojo.IdentityType;
import ie.mid.util.InternetUtil;

public class CardSelectActivity extends AppCompatActivity implements IdentityTaskCompleted {

    GridView gridView;
    IdentityTypeService identityTypeService;
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
            new IdentityTypeGetter(this, new IdentityTypeService(getApplicationContext())).execute();
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
                    Intent intent = new Intent(getApplicationContext(), CardCreateActivity.class);
                    intent.putExtra("card", identityTypeList.get(i));
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
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

    private static class IdentityTypeGetter extends AsyncTask<Void, Void, List<IdentityType>> {

        private IdentityTaskCompleted callBack;
        private IdentityTypeService identityTypeService;

        IdentityTypeGetter(IdentityTaskCompleted callBack, IdentityTypeService identityTypeService){
            this.callBack = callBack;
            this.identityTypeService = identityTypeService;
        }

        @Override
        protected List<IdentityType> doInBackground(Void... voids) {
            return identityTypeService.getIdentityTypes();
        }

        @Override
        protected void onPostExecute(List<IdentityType> result) {
            callBack.onTaskComplete(result);
        }

        @Override
        protected void onPreExecute() {
        }

    }
}
