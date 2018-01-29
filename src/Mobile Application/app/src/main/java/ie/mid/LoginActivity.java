package ie.mid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Arrays;

import ie.mid.handler.DatabaseHandler;
import ie.mid.layout.PinInterface;
import ie.mid.layout.PinLayout;
import ie.mid.model.Profile;
import ie.mid.util.HashUtil;

public class LoginActivity extends AppCompatActivity {

    static String userId;
    private Profile userProfile;
    PinLayout pinLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        userId = intent.getStringExtra("user");

        DatabaseHandler handler = new DatabaseHandler(this);
        handler.open();
        userProfile = handler.getProfile(userId);
        handler.close();

        pinLayout = (PinLayout) findViewById(R.id.pin_layout);
        pinLayout.setTypeYourPinInterface(new PinInterface() {
            @Override
            public void onPinTyped(String typedPin) {
                validatePin(typedPin);
            }
        });
        pinLayout.callFocus();

    }

    private void validatePin(String typedPin) {
        byte[] salt = HashUtil.hexToByte(userProfile.getSalt());
        byte[] correctHash = HashUtil.hexToByte(userProfile.getHash());
        byte[] hashedPassword = HashUtil.hashPassword(typedPin, salt);
        if (Arrays.equals(hashedPassword, correctHash)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            pinLayout.clearPassword();
            Toast.makeText(getApplicationContext(), "Pin Not Correct", Toast.LENGTH_LONG).show();
        }
    }
}
