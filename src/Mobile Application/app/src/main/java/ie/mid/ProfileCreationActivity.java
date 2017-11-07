package ie.mid;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

import ie.mid.view.RoundedImageView;

public class ProfileCreationActivity extends AppCompatActivity implements Validator.ValidationListener {

    Validator validator;
    private static final int RESULT_LOAD_IMAGE = 100;


    @NotEmpty
    EditText nickname;
    @Password(min = 4, scheme = Password.Scheme.NUMERIC)
    EditText password;
    @ConfirmPassword
    private EditText confirmPassword;

    Button createButton;
    RoundedImageView profileImage;
    String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        profileImage = (RoundedImageView) findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>22){
                    requestPermissions(new String[] {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE","android.permission.MANAGE_DOCUMENTS"}, 1);
                }
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);

        nickname = (EditText) findViewById(R.id.input_nickname);
        password = (EditText) findViewById(R.id.input_password);
        confirmPassword = (EditText) findViewById(R.id.input_password_confirm);

        createButton = (Button) findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied to access your location.", Toast.LENGTH_SHORT).show();
                }
                else
                {
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
            if(!copiedImage.equals(null)){
                Bitmap bitmap = BitmapFactory.decodeFile(copiedImage);
                profileImage.setImageBitmap(bitmap);
            }
        }
    }

    private String copyImage(String filePath){
        String finalPath = null;
        String extension = filePath.substring(filePath.lastIndexOf("."));
        File sourceImage = new File(filePath);
        if (!sourceImage.exists()) {
            return finalPath;
        }
        try{
            PackageManager packageManager = getPackageManager();
            String packageName = getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            packageName = packageInfo.applicationInfo.dataDir;
            String newPath = packageName +  "/profile_img/";
            File newImagePath = new File(newPath);
            if (!newImagePath.exists()) {
                newImagePath.mkdirs();
            }
            finalPath = newPath + UUID.randomUUID().toString() + extension;
            File newImage = new File(finalPath);
            newImage.createNewFile();

            FileChannel source = null;
            FileChannel destination = null;
            source = new FileInputStream(sourceImage).getChannel();
            destination = new FileOutputStream(newImage).getChannel();
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }

        }
        catch (Exception e){
            return null;
        }
        return finalPath;
    }
}
