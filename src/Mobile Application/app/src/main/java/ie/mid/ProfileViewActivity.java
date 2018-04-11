package ie.mid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

import ie.mid.async.ProfileDeleter;
import ie.mid.async.ProfileUpdater;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.ProfileTaskCompleted;
import ie.mid.model.Profile;
import ie.mid.view.RoundedImageView;

public class ProfileViewActivity extends AppCompatActivity implements ProfileTaskCompleted{

    private RoundedImageView profileImage;
    private Profile profile;
    private static final int RESULT_LOAD_IMAGE = 100;
    private EditText username;
    private boolean isDeletion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        final DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        profile = handler.getProfile(getIntent().getStringExtra("userId"));
        handler.close();
        profileImage = findViewById(R.id.profile_image);
        String imagePath = profile.getImageUrl();
        if (!imagePath.equals("PUG")) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            profileImage.setImageBitmap(bitmap);
        } else {
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.pug));
        }
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > 22) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.MANAGE_DOCUMENTS"}, 1);
                }
            }
        });
        username = findViewById(R.id.username);
        username.setText(profile.getName());
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(profile.getName())) {
                    findViewById(R.id.update_profile).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.update_profile).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final Context context = getApplicationContext();
        Button update = findViewById(R.id.update_profile);
        update.setVisibility(View.GONE);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = username.getText().toString();
                if(!newName.isEmpty()){
                    updateUsername(newName);
                } else{
                    Toast.makeText(context, "Cannot set username to null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button delete = findViewById(R.id.delete_profile);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
    }

    private void updateUsername(String newName) {
        isDeletion = false;
        profile.setName(newName);
        showLoading();
        new ProfileUpdater(getApplicationContext(),this).execute(profile);
    }

    private void confirmDelete(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteUser();

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);
        builder.setMessage("Are you sure you want to delete your profile? This is process is irreversible").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void deleteUser(){
        isDeletion = true;
        showLoading();
        new ProfileDeleter(getApplicationContext(),this).execute(profile);
    }

    void showLoading() {
        findViewById(R.id.button_container).setVisibility(View.GONE);
        findViewById(R.id.loading_container).setVisibility(View.VISIBLE);
    }

    void hideLoading() {
        findViewById(R.id.button_container).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_container).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("userId",profile.getId());
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied to access your location.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            String copiedImage = copyImage(picturePath);
            if (copiedImage != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(copiedImage);
                String selectedImagePath = copiedImage;
                profileImage.setImageBitmap(bitmap);
                DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
                handler.open();
                handler.updateProfileImg(profile.getId(), selectedImagePath);
                handler.close();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String copyImage(String filePath) {
        String finalPath;
        String extension = filePath.substring(filePath.lastIndexOf("."));
        File sourceImage = new File(filePath);
        if (!sourceImage.exists()) {
            return null;
        }
        PackageManager packageManager = getPackageManager();
        String packageName = getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        packageName = packageInfo.applicationInfo.dataDir;
        String newPath = packageName + "/profile_img/";
        File newImagePath = new File(newPath);
        if (!newImagePath.exists()) {
            newImagePath.mkdirs();
        }
        finalPath = newPath + UUID.randomUUID().toString() + extension;
        File newImage = new File(finalPath);
        boolean newFile;
        try {
            newFile = newImage.createNewFile();
        } catch (IOException e) {
            return null;
        }
        try (FileChannel source = new FileInputStream(sourceImage).getChannel(); FileChannel destination = new FileOutputStream(newImage).getChannel()) {
            if (source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            if (source != null) {
                source.close();
            }
            destination.close();

        } catch (Exception e) {
            return null;
        }
        if(newFile)
            return finalPath;
        else
            return null;
    }

    @Override
    public void onTaskComplete(Profile updatedProfile) {
        hideLoading();
        if(!isDeletion && updatedProfile != null){
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
            handler.open();
            handler.updateProfileName(profile.getId(),updatedProfile.getName());
            handler.close();
            username.setText(updatedProfile.getName());
            profile.setName(updatedProfile.getName());
            Toast.makeText(this, "Update Complete", Toast.LENGTH_SHORT).show();
        } else if(isDeletion){
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
            handler.open();
            handler.removeProfile(profile.getId());
            handler.close();
            Intent intent = new Intent(this,ProfileSelectionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, "Error updating server", Toast.LENGTH_SHORT).show();
        }
    }
}
