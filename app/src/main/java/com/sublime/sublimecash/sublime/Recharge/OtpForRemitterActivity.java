package com.sublime.sublimecash.sublime.Recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.sublime.sublimecash.sublime.MoneyTransferAddActivity;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;

public class OtpForRemitterActivity extends AppCompatActivity {
    EditText editOTP;
    Button btnAdd;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Profile myProfile;
    String Name,Mobile,RemitterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_for_remitter);
        Intent intent = getIntent();
        Name = intent.getStringExtra("name");
        Mobile =  intent.getStringExtra("Mobile");
        RemitterId = intent.getStringExtra("remitterId");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myProfile = Session.GetProfile(getApplicationContext());
        btnAdd = findViewById(R.id.btnAdd);
        editOTP = findViewById(R.id.editOTP);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTPValidate();
            }
        });
    }
    public void OTPValidate(){
        String url= Constants.Application_URL+"/users/index.php/Money/remitterValidation";
        progressDialog = progressDialog.show(OtpForRemitterActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    Intent intent= new Intent(OtpForRemitterActivity.this,MoneyTransferActivity.class);
                    startActivity(intent);
                    OtpForRemitterActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(OtpForRemitterActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("otp", editOTP.getText().toString());
                params.put("user_id",myProfile.UserID );
                params.put("name", Name);
                params.put("mobile", Mobile);
                params.put("remitterid",RemitterId);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
