package ie.mid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Profile;
import ie.mid.view.RoundedImageView;

public class ProfileViewActivity extends AppCompatActivity {

    private Profile profile;
    private Bitmap qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        profile = handler.getProfile(getIntent().getStringExtra("userId"));
        handler.close();
        RoundedImageView profileImage = findViewById(R.id.profile_image);
        TextView textView = findViewById(R.id.mid_text);
        textView.setText(profile.getName());
        String imagePath = profile.getImageUrl();
        if (!imagePath.equals("PUG")) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            profileImage.setImageBitmap(bitmap);
        } else {
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.pug));
        }
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(profile.getServerId(), BarcodeFormat.QR_CODE, 150, 150);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            qrCode = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrCode.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ImageView qrImageView = findViewById(R.id.qr_code);
            qrImageView.setImageBitmap(qrCode);
            qrImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "MiD is: " + profile.getServerId());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    return true;
                }
            });
        } catch (WriterException e) {
        }
    }
}
