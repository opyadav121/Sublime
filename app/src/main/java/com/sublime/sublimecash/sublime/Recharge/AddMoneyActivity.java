package com.sublime.sublimecash.sublime.Recharge;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.sublime.sublimecash.sublime.PayMentGateWay;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Session;
import Model.Profile;

public class AddMoneyActivity extends AppCompatActivity {
    EditText txtAmount;
    Button btnAdd;
    ProgressDialog progressDialog;
    String url = "https://secure.payu.in";
    Profile myProfile;
    RequestQueue requestQueue;
    String id= "1234";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Sublime Cash ");
        actionBar.show();
        requestQueue = Volley.newRequestQueue(this);
        myProfile = Session.GetProfile(this);
        btnAdd = findViewById(R.id.btnAdd);
        txtAmount = findViewById(R.id.txtAmount);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMoneyActivity.this, PayMentGateWay.class);
                intent.putExtra("FIRST_NAME",myProfile.UserName);
                intent.putExtra("EMAIL_ADDRESS",myProfile.original_email);
                intent.putExtra("PHONE_NUMBER",myProfile.MobileNumber);
                intent.putExtra("RECHARGE_AMT",txtAmount.getText().toString());
                intent.putExtra("EMAIL",myProfile.UserLogin);
                startActivity(intent);
                //AddMoney();
            }
        });

    }

    public void AddMoney(){
        try {
            progressDialog = progressDialog.show(AddMoneyActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        //Toast.makeText(RegisterActivity.this, ""+jObj.getString("msg"), Toast.LENGTH_SHORT).show();
                        String UserId = jObj.getString("user_id");
                        String password = jObj.getString("Password");
                        String Status = jObj.getString("msg");

                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    Toast.makeText(AddMoneyActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("amount",txtAmount.getText().toString());
                    params.put("firstname", myProfile.UserName);
                    params.put("email", myProfile.UserLogin);
                    params.put("phone", myProfile.MobileNumber);
                    params.put("key", "");
                    params.put("hash", "");
                    params.put("txnid", "");
                    params.put("join_user", "on");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }catch (Exception e){
            int a = 1;
        }
    }

}
