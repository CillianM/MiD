package ie.mid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;

import ie.mid.fragments.CardFragment;
import ie.mid.fragments.MoreFragment;
import ie.mid.fragments.RequestsFragment;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.CardType;

public class MainActivity extends AppCompatActivity {

    private String userId;
    private int tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = getIntent().getStringExtra("userId");
        tab = getIntent().getIntExtra("tab",-1);

        DatabaseHandler handler = new DatabaseHandler(this);
        handler.open();
        List<CardType> userCards = handler.getUserCards(userId);

        if (!handler.hasCards(userId)) {
            handler.close();
            Intent intent = new Intent(getApplicationContext(), CardSelectActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            finish();
        } else {

            BottomNavigationView bottomNavigationView = (BottomNavigationView)
                    findViewById(R.id.navigation);

            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", userId);
                            switch (item.getItemId()) {
                                case R.id.action_cards:
                                    selectedFragment = CardFragment.newInstance();
                                    selectedFragment.setArguments(bundle);
                                    break;
                                case R.id.action_requests:
                                    selectedFragment = RequestsFragment.newInstance();
                                    selectedFragment.setArguments(bundle);
                                    break;
                                case R.id.action_more:
                                    selectedFragment = MoreFragment.newInstance();
                                    selectedFragment.setArguments(bundle);
                                    break;
                            }
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, selectedFragment);
                            transaction.commitNow();

                            return true;
                        }
                    });

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = new Fragment();
            Bundle bundle = new Bundle();
            if(tab == -1) {
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                fragment = CardFragment.newInstance();
                bundle.putString("userId", userId);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commitNow();
            }
            else {
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                switch (tab){
                    case 0:
                        fragment = CardFragment.newInstance();
                    case 1:
                        fragment = RequestsFragment.newInstance();
                    case 2:
                        fragment = CardFragment.newInstance();
                }
                bundle = new Bundle();
                bundle.putString("userId", userId);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commitNow();
            }
        }
    }
}
