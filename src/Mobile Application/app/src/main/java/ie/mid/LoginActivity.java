package ie.mid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
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
    private FrameLayout mFrameNumber1;
    private FrameLayout mFrameNumber2;
    private FrameLayout mFrameNumber3;
    private FrameLayout mFrameNumber4;
    private FrameLayout mFrameNumber5;
    private FrameLayout mFrameNumber6;
    private FrameLayout mFrameNumber7;
    private FrameLayout mFrameNumber8;
    private FrameLayout mFrameNumber9;
    private FrameLayout mFrameNumber0;
    private FrameLayout mFrameNumberDeleteSpace;
    private FrameLayout mFrameNumberNext;


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

        pinLayout = findViewById(R.id.pin_layout);
        pinLayout.setTypeYourPinInterface(new PinInterface() {
            @Override
            public void onPinTyped(String typedPin) {
                validatePin(typedPin);
            }
        });

        mFrameNumber1 = findViewById(R.id.frameLayout_number1);
        mFrameNumber2 = findViewById(R.id.frameLayout_number2);
        mFrameNumber3 = findViewById(R.id.frameLayout_number3);
        mFrameNumber4 = findViewById(R.id.frameLayout_number4);
        mFrameNumber5 = findViewById(R.id.frameLayout_number5);
        mFrameNumber6 = findViewById(R.id.frameLayout_number6);
        mFrameNumber7 = findViewById(R.id.frameLayout_number7);
        mFrameNumber8 = findViewById(R.id.frameLayout_number8);
        mFrameNumber9 = findViewById(R.id.frameLayout_number9);
        mFrameNumber0 = findViewById(R.id.frameLayout_number0);
        mFrameNumberDeleteSpace = findViewById(R.id.frameLayout_deletePin);
        setPinLayout();
    }

    private void validatePin(String typedPin) {
        byte[] salt = HashUtil.hexToByte(userProfile.getSalt());
        byte[] correctHash = HashUtil.hexToByte(userProfile.getHash());
        byte[] hashedPassword = HashUtil.hashPassword(typedPin, salt);
        if (Arrays.equals(hashedPassword, correctHash)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        } else {
            pinLayout.clearPassword();
            Toast.makeText(getApplicationContext(), "Pin Not Correct", Toast.LENGTH_LONG).show();
        }
    }

    private void setPinLayout(){

        mFrameNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("1");
            }
        });

        mFrameNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("2");
            }
        });

        mFrameNumber3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("3");
            }
        });

        mFrameNumber4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("4");
            }
        });

        mFrameNumber5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("5");
            }
        });

        mFrameNumber6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("6");
            }
        });

        mFrameNumber7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("7");
            }
        });

        mFrameNumber8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("8");
            }
        });

        mFrameNumber9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("9");
            }
        });

        mFrameNumber0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.addChar("0");
            }
        });

        /**
         * Delete pin one by one, after that, change the background of indicator
         */
        mFrameNumberDeleteSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLayout.removeChar();
            }
        });
    }
}
