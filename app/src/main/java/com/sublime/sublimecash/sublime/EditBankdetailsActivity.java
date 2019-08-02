package com.sublime.sublimecash.sublime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Common.Session;
import Common.VolleyMultipartRequest;
import Model.Profile;

public class EditBankdetailsActivity extends AppCompatActivity {
    EditText txtAcHolderName,txtAccountNumber,txtIfsc,txtBank,txtBranch;
    ImageView addPhoto;
    Button btnSubmit;
    Profile myProfile;
    RequestQueue requestQueue;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bankdetails);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Bank Details");
        actionBar.show();

        myProfile = Session.GetProfile(this);
        requestQueue = Volley.newRequestQueue(this);
        txtAcHolderName = findViewById(R.id.txtAcHolderName);
        txtAccountNumber = findViewById(R.id.txtAccountNumber);
        txtIfsc = findViewById(R.id.txtIfsc);
        txtBank = findViewById(R.id.txtBank);
        txtBranch = findViewById(R.id.txtBranch);
        addPhoto = findViewById(R.id.addPhoto);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadBankDetails();
            }
        });

        findViewById(R.id.addPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                addPhoto.setImageBitmap(bitmap);
                //calling the method uploadBitmap to upload image
                //  txtUpload.setVisibility(View.VISIBLE);
                // uploadBitmap();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] getFileDataFromDrawable(Bitmap bit) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public void UploadBankDetails(){
        String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/kyc/upload_bank_detail";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,url , new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(EditBankdetailsActivity.this, "file Uploaded Successfully", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email",myProfile.UserLogin);
                params.put("bank_holder_name", txtAcHolderName.getText().toString());
                params.put("bank_account_no", txtAccountNumber.getText().toString());
                params.put("bank_name", txtBank.getText().toString());
                params.put("bank_branch_name", txtBranch.getText().toString());
                params.put("bank_ifsc", txtIfsc.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}