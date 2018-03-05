package ie.mid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.internal.Sets;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ie.mid.adapter.IdentityRequestAdapter;
import ie.mid.adapter.SettingsListAdapter;
import ie.mid.async.IdentityTypeGetter;
import ie.mid.async.RequestCreator;
import ie.mid.backend.IdentityTypeService;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.IdentityTaskCompleted;
import ie.mid.interfaces.RequestCreateTaskCompleted;
import ie.mid.model.CardField;
import ie.mid.model.Field;
import ie.mid.model.Profile;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.InformationRequest;
import ie.mid.pojo.Request;
import ie.mid.util.InternetUtil;

public class RequestCreateActivity extends AppCompatActivity implements IdentityTaskCompleted,RequestCreateTaskCompleted {

    private Profile profile;
    private String recipientId;
    private ImageView qrCodeView;
    List<IdentityType> identityTypeList;
    Spinner identityTypeSpinner;
    IdentityRequestAdapter identityRequestAdapter;
    ListView identityValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        handler.open();
        profile = handler.getProfile(getIntent().getStringExtra("userId"));
        handler.close();

        Button button = findViewById(R.id.create_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRequest();
            }
        });
        qrCodeView = findViewById(R.id.qr_code);
        qrCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intiateScan();
            }
        });
        identityValues = findViewById(R.id.identity_type_values);
        identityTypeSpinner = (Spinner) findViewById(R.id.identity_types);
        showLoading();
        if(InternetUtil.isNetworkAvailable(getApplicationContext())){
            new IdentityTypeGetter(getApplicationContext(),this).execute();
        } else{
            networkError();
        }

    }

    private void showLoading(){
        findViewById(R.id.request_layout).setVisibility(View.GONE);
        findViewById(R.id.request_progress).setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        findViewById(R.id.request_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.request_progress).setVisibility(View.GONE);

    }

    private void submitRequest() {
        if(recipientId == null){
            Toast.makeText(getApplicationContext(),"No user QR code scanned",Toast.LENGTH_SHORT).show();
            return;
        }
        List<Boolean> isChecked = identityRequestAdapter.getBooleanList();
        IdentityType identityType = identityTypeList.get(identityTypeSpinner.getSelectedItemPosition());
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < identityType.getFields().size(); i++){
            if(isChecked.get(i)){
                builder.append(identityType.getFields().get(i).getType()).append(",");
            }
        }
        boolean containsTrue = isChecked.contains(true);
        if(!containsTrue){
            Toast.makeText(getApplicationContext(),"No item selected",Toast.LENGTH_SHORT).show();
            return;
        }
        builder.deleteCharAt(builder.length()-1);
        InformationRequest request = new InformationRequest();
        request.setIndentityTypeId(identityType.getId());
        request.setIdentityTypeFields(builder.toString());
        request.setSenderId(profile.getServerId());
        request.setRecipientId(recipientId);
        showLoading();
        if(InternetUtil.isNetworkAvailable(getApplicationContext())){
            new RequestCreator(getApplicationContext(),this,profile).execute(request);
        } else{
            networkError();
        }
    }

    private void networkError() {
        findViewById(R.id.request_progress).setVisibility(View.GONE);
        findViewById(R.id.request_info).setVisibility(View.VISIBLE);
        TextView info = findViewById(R.id.request_info);
        info.setText("No network connection");
    }

    private void intiateScan(){
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setPrompt("Scan User QR Code")
                .initiateScan();
    }

    private void createQrCode(String contents){
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 150, 150);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap qrCode = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrCode.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            qrCodeView.setImageBitmap(qrCode);
            findViewById(R.id.qr_prompt).setVisibility(View.GONE);
        }
        catch (WriterException e){
            Toast.makeText(getApplicationContext(),"Error writing QR to image",Toast.LENGTH_SHORT).show();
        }
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                createQrCode(result.getContents());
                recipientId = result.getContents();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private List<String> getIdentityNames(List<IdentityType> identityTypes){
        List<String> names = new ArrayList<>();
        for(IdentityType identityType:identityTypes){
            names.add(identityType.getName());
        }
        return names;
    }

    @Override
    public void onTaskComplete(Request request) {
        if(request != null){
            finish();
        }
        else{
            hideLoading();
            Toast.makeText(getApplicationContext(),"Error submitting request",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskComplete(List<IdentityType> identityTypes) {
        if(identityTypes != null){
            hideLoading();
            identityTypeList = identityTypes;
            List<String> names = getIdentityNames(identityTypes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.spinner_item, names);
            identityTypeSpinner.setAdapter(adapter);

            identityTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    setList(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if(identityTypeList.size() > 0){
                setList(0);
            }
        }
        else{
            findViewById(R.id.request_progress).setVisibility(View.GONE);
            findViewById(R.id.request_info).setVisibility(View.VISIBLE);
            TextView info = findViewById(R.id.request_info);
            info.setText("Error Retrieving Data");
        }

    }

    private void setList(int position) {
        identityRequestAdapter = new IdentityRequestAdapter(getApplicationContext(), identityTypeList.get(position).getFields());
        identityValues.setAdapter(identityRequestAdapter);
    }


}
