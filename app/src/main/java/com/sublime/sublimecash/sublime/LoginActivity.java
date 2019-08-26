package com.sublime.sublimecash.sublime;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import Common.Constants;
import Common.Session;
import Model.PrefManager;
import Model.Profile;

public class LoginActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String  Login_url = Constants.Application_URL+"/users/index.php/Login/login";

     Button btnLogin,btnSkip;
     TextView btnRegister;
     EditText txtEmail,txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(" Login ");
        actionBar.show();

        requestQueue = Volley.newRequestQueue(this);
        btnRegister = findViewById(R.id.btnRegister);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });
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
    public void Login(){
        progressDialog = progressDialog.show(LoginActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Login_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    if (response!=null) {
                        JSONObject jObj = new JSONObject(response);
                         String msg = jObj.getString("msg");
                        if (msg.equalsIgnoreCase("Fail")) {
                            Toast.makeText(LoginActivity.this, "userId or password Incorrect", Toast.LENGTH_SHORT).show();
                        }else {
                            Profile myProfile = new Profile();
                            myProfile.UserName = jObj.getString("name");
                            myProfile.UserID = jObj.getString("user_id");
                            myProfile.UserLogin = jObj.getString("email");
                            myProfile.MobileNumber = jObj.getString("mobile");
                            myProfile.Address = jObj.getString("address");
                            myProfile.original_email = jObj.getString("original_email");
                            myProfile.joiningAmount = jObj.getString("joining_amount");
                            myProfile.ActivationDate = jObj.getString("activation_date");
                            myProfile.PanNumber = jObj.getString("pan_no");
                            myProfile.SponserId = jObj.getString("sponsor_id");
                            myProfile.UnderUserId = jObj.getString("under_userid");
                            myProfile.Dob = jObj.getString("dob");
                            myProfile.FatherName = jObj.getString("father_name");
                            myProfile.MotherName = jObj.getString("mother_name");
                            myProfile.gender = jObj.getString("gender");
                            myProfile.district = jObj.getString("district");
                            myProfile.State = jObj.getString("state");
                            myProfile.PinCode = jObj.getString("pincode");
                            myProfile.Passport = jObj.getString("passport");
                            myProfile.nominee = jObj.getString("nominee");
                            myProfile.nomineeRelation = jObj.getString("relation");
                            myProfile.marital = jObj.getString("marital");
                            myProfile.commission_paid = jObj.getString("commission_paid");
                            myProfile.total_left_user = jObj.getString("total_left_user");
                            myProfile.total_right_user = jObj.getString("total_right_user");
                            myProfile.left_carry = jObj.getString("left_carry");
                            myProfile.right_carry = jObj.getString("right_carry");
                            myProfile.left_business = jObj.getString("left_business");
                            myProfile.right_business = jObj.getString("right_business");
                            myProfile.occupation = jObj.getString("occupation");
                            myProfile.JoiningAmount = jObj.getString("joining_amount");
                            myProfile.side = jObj.getString("side");
                            myProfile.profileImg = jObj.getString("profile");
                            Session.AddProfile(getApplicationContext(), myProfile);
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                            LoginActivity.this.finish();
                        }
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
