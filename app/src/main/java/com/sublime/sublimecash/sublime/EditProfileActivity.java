package com.sublime.sublimecash.sublime;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Common.Session;
import Common.VolleyMultipartRequest;
import Model.Profile;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    EditText txtName,txtEmail,txtMobile,txtDistrict,txtDob,txtOccupation,txtMotherName,
            txtFatherName,txtNomineeName,txtNomineeRelation,txtPan,txtAadhar,txtAddress,
            txtPincode,txtState;
    Button btnSubmit,btnUpload;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    CircleImageView imageProfile;
    Bitmap bitmap;
    Profile myProfile;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Profile");
        actionBar.show();
        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtMobile);
        txtDob = findViewById(R.id.txtDob);
        progressBar = findViewById(R.id.progressBar);
        txtOccupation = findViewById(R.id.txtOccupation);
        txtMotherName = findViewById(R.id.txtMotherName);
        txtFatherName = findViewById(R.id.txtFatherName);
        txtNomineeName = findViewById(R.id.txtNomineeName);
        txtNomineeRelation = findViewById(R.id.txtNomineeRelation);
        txtPan = findViewById(R.id.txtPan);
        txtAddress = findViewById(R.id.txtAddress);
        txtPincode = findViewById(R.id.txtPincode);
        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        btnSubmit = findViewById(R.id.btnSubmit);
        imageProfile = findViewById(R.id.imageProfile);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        if (myProfile.profileImg.equalsIgnoreCase("null")|| myProfile.profileImg.equalsIgnoreCase("")) {

        }else {
            String url1 = myProfile.profileImg;
            Picasso.with(getApplicationContext()).load(url1).into(imageProfile);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateProfile();
                }
            });
            btnUpload = findViewById(R.id.btnUpload);
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadBitmap();
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageProfile.setImageBitmap(bitmap);
                //calling the method uploadBitmap to upload image
                btnUpload.setVisibility(View.VISIBLE);
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
    private void uploadBitmap() {
        progressBar.setVisibility(View.VISIBLE);
        String url_profile = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/kyc/upload_pic";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,url_profile , new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(EditProfileActivity.this, "file Uploaded Successfully", Toast.LENGTH_SHORT).show();
                try {
                    progressBar.setVisibility(View.GONE);
                    btnUpload.setVisibility(View.GONE);
                    JSONObject obj = new JSONObject(new String(response.data));
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }) {
            String name;
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                name= Long.toString(imagename);
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email",myProfile.UserLogin);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
    public void UpdateProfile(){
        String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/updateprofile";
        progressDialog = progressDialog.show(EditProfileActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    if (response!=null) {
                        JSONObject jObj = new JSONObject(response);
                        String msg = jObj.getString("msg");

                    }
                    else {
                        Toast.makeText(EditProfileActivity.this, "Login Failed......", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", txtEmail.getText().toString());
                params.put("name", txtName.getText().toString());
                params.put("mobile", txtMobile.getText().toString());
                params.put("father_name", txtFatherName.getText().toString());
                params.put("mother_name", txtMotherName.getText().toString());
                params.put("occupation", txtOccupation.getText().toString());
                params.put("dob", txtDob.getText().toString());
                params.put("state", txtState.getText().toString());
                params.put("pan_no", txtPan.getText().toString());
                params.put("district", txtDistrict.getText().toString());
                params.put("pincode", txtPincode.getText().toString());
                params.put("address", txtAddress.getText().toString());
                params.put("nominee", txtNomineeName.getText().toString());
                params.put("nominee_relation", txtNomineeRelation.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
