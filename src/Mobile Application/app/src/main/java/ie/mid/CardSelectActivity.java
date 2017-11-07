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
import ie.mid.model.AvailableCard;

public class CardSelectActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                startActivity(intent);
            }
        });

        TextView description = (TextView) findViewById(R.id.desciption_text);
        description.setText(getResources().getString(R.string.select_description));
    }

    private List<AvailableCard> getListData(){
        AvailableCard data1 = new AvailableCard("Passport", "http://irishpost.co.uk/wp-content/uploads/2016/10/Ireland_Passport_featured.jpg");
        AvailableCard data2 = new AvailableCard("Drivers License", "http://www.laoissc.com/wp-content/uploads/sites/4/2014/12/National-Driver-Licence-Service-Logo.png");
        AvailableCard data3 = new AvailableCard("Public Services Card", "https://pbs.twimg.com/profile_images/905375014270242817/yHHuC5Hz_400x400.jpg");
        AvailableCard data11 = new AvailableCard("Passport", "http://irishpost.co.uk/wp-content/uploads/2016/10/Ireland_Passport_featured.jpg");
        AvailableCard data22 = new AvailableCard("Drivers License", "http://www.laoissc.com/wp-content/uploads/sites/4/2014/12/National-Driver-Licence-Service-Logo.png");
        AvailableCard data33 = new AvailableCard("Public Services Card", "https://pbs.twimg.com/profile_images/905375014270242817/yHHuC5Hz_400x400.jpg");
        AvailableCard data111 = new AvailableCard("Passport", "http://irishpost.co.uk/wp-content/uploads/2016/10/Ireland_Passport_featured.jpg");
        AvailableCard data222 = new AvailableCard("Drivers License", "http://www.laoissc.com/wp-content/uploads/sites/4/2014/12/National-Driver-Licence-Service-Logo.png");
        AvailableCard data333 = new AvailableCard("Public Services Card", "https://pbs.twimg.com/profile_images/905375014270242817/yHHuC5Hz_400x400.jpg");
        ArrayList<AvailableCard> listOfData = new ArrayList<>();
        listOfData.add(data1);
        listOfData.add(data2);
        listOfData.add(data3);
        listOfData.add(data11);
        listOfData.add(data22);
        listOfData.add(data33);
        listOfData.add(data111);
        listOfData.add(data222);
        listOfData.add(data333);
        return listOfData;
    }
}
