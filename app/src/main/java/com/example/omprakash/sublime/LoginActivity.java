package com.example.omprakash.sublime;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import Common.Session;
import Model.Profile;

public class LoginActivity extends AppCompatActivity {

     Button btnLogin;
     TextView btnRegister;
     EditText txtUserName,txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister = findViewById(R.id.btnRegister);
        txtUserName = findViewById(R.id.txtUseName);
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
                UserLogin();

               //Intent i = new Intent(LoginActivity.this,HomeActivity.class);
              // startActivity(i);

              /*  if(txtUserName.getText().toString().matches("") || txtPassword.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(),"Login or Password is empty", Toast.LENGTH_LONG);
                }
                else
                {
                    UserLogin();
                }  */
            }
        });
    }

    public void UserLogin(){
        String Email= txtUserName.getText().toString();
        String Password= txtPassword.getText().toString();
        try {
            String url = "http://sublimecash.com/ciwebservices/index.php/form_api/Usertest/register?email=" +Email+ "&password="+Password;
           // String reqBody = "{\"email\":\"" +Email  + "\", \"password\":\"" + Password + "\"}";

           // JSONObject jsRequest = new JSONObject(reqBody);
            //-------------------------------------------------------------------------------------------------
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.GET, url,  new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response!=null) {
                            Profile myProfile = new Profile();
                           // myProfile.UserName = response.getString("email");
                            myProfile.UserPassword = response.getString("password");
                            myProfile.UserLogin = response.getString("email");
                            myProfile.MobileNumber = response.getString("mobile");
                           // myProfile.ProfileImage = "";
                            myProfile.UserID = response.getString("under_userid");
                            myProfile.Address = response.getString("address");

                            Session.AddProfile(getApplicationContext(), myProfile);

                        }

                    } catch (Exception e) {
                        int a = 1;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = error.toString();

                    // prgBar.setVisibility(View.GONE);
                }
            });
            RetryPolicy rPolicy = new DefaultRetryPolicy(0, -1, 0);

            jsArrayRequest.setRetryPolicy(rPolicy);
            queue.add(jsArrayRequest);

            //*******************************************************************************************************
        } catch (Exception js) {
            Toast.makeText(getApplicationContext(), "Login Failed ,Contact Admin", Toast.LENGTH_LONG).show();
        }
    }
}
