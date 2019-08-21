package com.sublime.sublimecash.sublime.Recharge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.HomeActivity;
import com.sublime.sublimecash.sublime.LoginActivity;
import com.sublime.sublimecash.sublime.R;
import com.sublime.sublimecash.sublime.RegisterActivity;
import com.sublime.sublimecash.sublime.SupportStatusActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;

public class SupportActivity extends AppCompatActivity {
    EditText txtMobile,txtMsg;
    Button btnStatus,btnSubmit;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Support");
        actionBar.show();
        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        btnSubmit = findViewById(R.id.btnSubmit);
        btnStatus = findViewById(R.id.btnStatus);
        txtMobile = findViewById(R.id.txtMobile);
        txtMsg = findViewById(R.id.txtMsg);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtMobile.getText().toString().length() == 0 )
                    txtMobile.setError("Mobile no. is required!");
                else if (txtMsg.getText().toString().length() == 0){
                    txtMsg.setError("Enter Some text!");
                }else {
                    HelpSend();
                }
            }
        });
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SupportActivity.this, SupportStatusActivity.class));
            }
        });

    }
    public void HelpSend(){
        try {
            String  Register_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/login/Query";
            progressDialog = progressDialog.show(SupportActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>()  {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String Status = jObj.getString("msg");
                        Toast.makeText(SupportActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SupportActivity.this, HomeActivity.class));
                        SupportActivity.this.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    Toast.makeText(SupportActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("userid", myProfile.UserLogin);
                    params.put("mobile_no",txtMobile.getText().toString());
                    params.put("msg",txtMsg.getText().toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }catch (Exception e){
            int a = 1;
        }
    }
}
