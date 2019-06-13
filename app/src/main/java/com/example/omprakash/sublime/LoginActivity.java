package com.example.omprakash.sublime;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    //String MobilePattern = "[0-9]{10}";
    ProgressDialog progressDialog;
    String  Login_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Login/login";

     Button btnLogin;
     TextView btnRegister;
     EditText txtEmail,txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        btnRegister = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }
    private void Login(){
        progressDialog = progressDialog.show(LoginActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Login_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
              // int x = response.length();
                try {
                    if (response!=null) {
                        JSONObject jObj = new JSONObject(response);
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Profile myProfile = new Profile();
                        myProfile.UserName = jObj.getString("email");
                       // myProfile.UserPassword = jObj.getString("Password");
                        myProfile.UserLogin = jObj.getString("email");
                        myProfile.MobileNumber = jObj.getString("mobile");
                        myProfile.Address = jObj.getString("address");
                        myProfile.joiningAmount = jObj.getString("joining_amount");
                        myProfile.ActivationDate = jObj.getString("activation_date");
                        myProfile.Sponsor = jObj.getString("sponsor");
                        myProfile.PanNumber= jObj.getString("pan_no");
                        Session.AddProfile(getApplicationContext(), myProfile);
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        LoginActivity.this.finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Login Failed......", Toast.LENGTH_LONG).show();
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
                Toast.makeText(LoginActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", txtEmail.getText().toString());
                params.put("pass", txtPassword.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
