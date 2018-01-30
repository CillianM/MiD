package ie.mid;

import android.content.Intent;
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
import ie.mid.model.AvailableCard;
import ie.mid.pojo.IdentityType;

public class CardSelectActivity extends AppCompatActivity {

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

        gridView = (GridView) findViewById(R.id.card_select);

        gridView.setAdapter(new CardSelectAdapter(this,getListData()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CardCreateActivity.class);
                intent.putExtra("card", identityTypeList.get(i));
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        TextView description = (TextView) findViewById(R.id.desciption_text);
        description.setText(getResources().getString(R.string.select_description));
    }

    private List<AvailableCard> getListData(){
        ArrayList<AvailableCard> listOfData = new ArrayList<>();
        identityTypeService = new IdentityTypeService(getApplicationContext());
        identityTypeList = identityTypeService.getIdentityTypes();
        for (IdentityType identityType : identityTypeList) {
            AvailableCard availableCard = new AvailableCard(identityType.getName(), identityType.getIconImg());
            listOfData.add(availableCard);
        }
        return listOfData;
    }
}
