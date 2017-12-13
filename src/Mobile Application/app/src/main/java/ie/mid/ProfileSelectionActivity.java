package ie.mid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Profile;
import ie.mid.view.RoundedImageView;

public class ProfileSelectionActivity extends AppCompatActivity {

    Spinner profileSpinner;
    RoundedImageView profileImage;
    List<Profile> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selection);

        DatabaseHandler handler = new DatabaseHandler(this);
        handler.open();

        if(handler.returnAmountOfProfiles() == 0){
            handler.close();
            Intent intent = new Intent(getApplicationContext(),ProfileCreationActivity.class);
            startActivity(intent);
        }
        else {
            profiles = handler.returnProfiles();
            List<String> names = getProfileNames(profiles);
            handler.close();
            Button loginButton = (Button) findViewById(R.id.login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            ImageButton addButton = (ImageButton) findViewById(R.id.add_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ProfileCreationActivity.class);
                    startActivity(intent);
                }
            });

            profileSpinner = (Spinner) findViewById(R.id.profile_spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                    R.layout.list_item_spinner, names);
            profileSpinner.setAdapter(adapter);

            profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String imagePath = profiles.get(i).getImageUrl();
                    if(!imagePath.equals("PUG")) {
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        profileImage.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    List<String> getProfileNames(List<Profile> profiles){
        List<String> names = new ArrayList<>();
        for(Profile profile:profiles){
            names.add(profile.getName());
        }
        return names;
    }
}
