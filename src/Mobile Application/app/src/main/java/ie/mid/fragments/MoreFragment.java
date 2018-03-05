package ie.mid.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

import ie.mid.CardSelectActivity;
import ie.mid.RequestCreateActivity;
import ie.mid.ProfileViewActivity;
import ie.mid.R;
import ie.mid.SettingsActivity;
import ie.mid.adapter.SettingsListAdapter;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.Profile;
import ie.mid.model.SettingItem;
import ie.mid.view.RoundedImageView;

public class MoreFragment extends Fragment {

    ListView settingsList;
    ArrayList<SettingItem> settingItems = new ArrayList<>();
    String userId;
    Profile profile;
    private Bitmap qrCode;

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userId = getArguments().getString("userId");
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        DatabaseHandler handler = new DatabaseHandler(getActivity().getApplicationContext());
        handler.open();
        profile = handler.getProfile(getActivity().getIntent().getStringExtra("userId"));
        handler.close();

        RoundedImageView profileImage = view.findViewById(R.id.profile_image);
        TextView textView = view.findViewById(R.id.settings_title);
        textView.setText(profile.getName());
        String imagePath = profile.getImageUrl();
        if (!imagePath.equals("PUG")) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            profileImage.setImageBitmap(bitmap);
        } else {
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.pug));
        }

        Button button = (Button) view.findViewById(R.id.qr_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQR();
            }
        });

        settingsList = view.findViewById(R.id.settings_list);
        getSettingsList();
        SettingsListAdapter adapter = new SettingsListAdapter(getActivity().getApplicationContext(), settingItems);
        settingsList.setAdapter(adapter);

        // Drawer Item click listeners
        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), ProfileViewActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), CardSelectActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), RequestCreateActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), SettingsActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    public void getSettingsList() {
        settingItems.add(new SettingItem("Profile", "Manage Profile Settings", R.drawable.ic_face_black_24dp));
        settingItems.add(new SettingItem("Add Card", "Add a new card", R.drawable.ic_credit_card_black_24dp));
        settingItems.add(new SettingItem("Request Information", "Request information from a user", R.drawable.ic_person_black_24dp));
        settingItems.add(new SettingItem("Settings", "Manage Application Settings", R.drawable.ic_settings_black_24dp));
    }


    private void showQR(){
        getQR();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.dialog_qr, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView qrImageView = dialogLayout.findViewById(R.id.qr_code);
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
                float imageWidthInPX = (float)qrImageView.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)qrCode.getHeight() / (float)qrCode.getWidth()));
                qrImageView.setLayoutParams(layoutParams);


            }
        });

        dialog.show();
    }

    private void getQR(){
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

        } catch (WriterException e) {
        }
    }
}
