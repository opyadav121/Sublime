package com.sublime.sublimecash.sublime;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Session;
import Model.Profile;

public class CorrespondanceAddressActivity extends AppCompatActivity {
    EditText txtName,txtMobile,txtPincode,txtAddress1,txtAddress2,txtArea,txtCity,txtState;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Profile myProfile;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correspondance_address);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();
        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        txtName = findViewById(R.id.txtName);
        txtMobile = findViewById(R.id.txtMobile);
        txtPincode = findViewById(R.id.txtPincode);
        txtAddress1 = findViewById(R.id.txtAddress1);
        txtAddress2 = findViewById(R.id.txtAddress2);
        txtArea = findViewById(R.id.txtArea);
        txtCity = findViewById(R.id.txtCity);
        txtState = findViewById(R.id.txtState);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAddress();
            }
        });

    }
    public void AddNewAddress(){
        String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/updateprofile";
        progressDialog = progressDialog.show(CorrespondanceAddressActivity.this, "", "Please wait...", false, false);
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
                        Toast.makeText(CorrespondanceAddressActivity.this, "Failed......", Toast.LENGTH_LONG).show();
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
                Toast.makeText(CorrespondanceAddressActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
               // params.put("email", txtEmail.getText().toString());
                params.put("name", txtName.getText().toString());
                params.put("mobile", txtMobile.getText().toString());
                params.put("father_name", txtPincode.getText().toString());
                params.put("mother_name", txtAddress1.getText().toString());
                params.put("occupation", txtAddress2.getText().toString());
                params.put("dob", txtArea.getText().toString());
                params.put("state", txtState.getText().toString());
                params.put("pan_no", txtCity.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
