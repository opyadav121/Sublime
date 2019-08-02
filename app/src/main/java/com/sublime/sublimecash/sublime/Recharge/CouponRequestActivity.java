package com.sublime.sublimecash.sublime.Recharge;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Common.VolleyMultipartRequest;
import Model.Profile;

public class CouponRequestActivity extends AppCompatActivity {

    Spinner spPlans;
    EditText txtQuantity, txtHolderName, txtBankName, txtBranch, txtState, txtDistrict, editTextTags;
    TextView txtUpload;
    Button btnImageUpdate;
    ImageView fileImage;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/Assets/uploads/abc";
    String RequestCoupon_url = Constants.Application_URL+ "/users/index.php/Home/request_activation";
    Profile myProfile;
    Bitmap bitmap;
    String splan,CoupanName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Coupon Request ");
        actionBar.show();
        myProfile = Session.GetProfile(this);
        requestQueue = Volley.newRequestQueue(this);
        spPlans = findViewById(R.id.spPlans);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtHolderName = findViewById(R.id.txtHolderName);
        txtBankName = findViewById(R.id.txtBankName);
        txtBranch = findViewById(R.id.txtBranch);
        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        txtUpload = findViewById(R.id.txtUpload);
        fileImage = findViewById(R.id.fileImage);
        btnImageUpdate = findViewById(R.id.btnImageUpdate);
        txtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBitmap();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.CouponRequest, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlans.setAdapter(adapter);
        spPlans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                splan = parent.getItemAtPosition(position).toString();//this is your selected item

                if (splan.equalsIgnoreCase("2240")){
                    CoupanName="Plan1";
                }else if (splan.equalsIgnoreCase("6720")){
                    CoupanName="Plan2";
                }else if (splan.equalsIgnoreCase("11200")){
                    CoupanName="Plan3";
                }else {
                    CoupanName="Plan4";
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //adding click listener to button
        findViewById(R.id.btnImageUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if everything is ok we will open image chooser
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

                fileImage.setImageBitmap(bitmap);
                txtUpload.setVisibility(View.VISIBLE);
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
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,RequestCoupon_url , new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(CouponRequestActivity.this, "file Uploaded Successfully", Toast.LENGTH_SHORT).show();
                CouponRequestActivity.this.finish();
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("coupon_name", CoupanName);
                params.put("district", "null");
                params.put("amount", splan);
                params.put("bank_holder_name", txtHolderName.getText().toString());
                params.put("bank_name", txtBankName.getText().toString());
                params.put("state", "null");
                params.put("quantity", txtQuantity.getText().toString());
                params.put("date", "");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}