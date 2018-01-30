package ie.mid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ie.mid.fragments.CardFragment;
import ie.mid.fragments.MoreFragment;
import ie.mid.fragments.RequestsFragment;
import ie.mid.handler.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    public String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = getIntent().getStringExtra("userId");

        DatabaseHandler handler = new DatabaseHandler(this);
        handler.open();

        if (!handler.hasCards(userId)) {
            handler.close();
            Intent intent = new Intent(getApplicationContext(), CardSelectActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
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

            //Manually displaying the first fragment - one time only
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = CardFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
            transaction.commitNow();
        }
    }
}
