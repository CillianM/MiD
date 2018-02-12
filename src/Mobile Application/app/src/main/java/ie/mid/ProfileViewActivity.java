package ie.mid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Profile;
import ie.mid.view.RoundedImageView;

public class ProfileViewActivity extends AppCompatActivity {

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        profile = handler.getProfile(getIntent().getStringExtra("userId"));
        handler.close();
        RoundedImageView profileImage = findViewById(R.id.profile_image);
        String mid = "MiD: " + profile.getMid();
        TextView textView = findViewById(R.id.mid_text);
        textView.setText(mid);
        String imagePath = profile.getImageUrl();
        if(!imagePath.equals("PUG")) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            profileImage.setImageBitmap(bitmap);
        } else {
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.pug));
        }
    }
}
