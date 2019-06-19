package com.example.omprakash.sublime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omprakash.sublime.Recharge.CouponActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Model.Coupons;

public class RegisterPaidActivity extends AppCompatActivity {

    EditText txtPin,txtEmail,txtMobile,txtPassword,txtName;
    RadioGroup radioGroup;
    Button btnJoin;
    String side;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String reg_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/memberRegister";
    String pinNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_paid);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Register ");
        actionBar.show();
        requestQueue = Volley.newRequestQueue(this);
        txtPin = findViewById(R.id.txtPin);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtMobile = findViewById(R.id.txtMobile);
        txtPassword= findViewById(R.id.txtPassword);
        radioGroup = findViewById(R.id.radioGroup);
        btnJoin = findViewById(R.id.btnJoin);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton =  findViewById(checkedId);
                side = checkedRadioButton.getText().toString();
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaidRegister();
            }
        });

        Intent intent = getIntent();
        pinNo = intent.getStringExtra("Pin");
        txtPin.setText(pinNo);
    }

    public void PaidRegister(){
        progressDialog = progressDialog.show(RegisterPaidActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    Toast.makeText(RegisterPaidActivity.this, ""+jObj.getString("msg"), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterPaidActivity.this, LoginActivity.class);
                    startActivity(i);
                    RegisterPaidActivity.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterPaidActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", txtEmail.getText().toString());
                params.put("name",txtName.getText().toString());
                params.put("join_user", "on");
                params.put("pin", txtPin.getText().toString());
                params.put("mobile", txtMobile.getText().toString());
                params.put("password", txtPassword.getText().toString());
                params.put("account", "null");
                params.put("side", side);
                params.put("login_user", "user@tutorialvilla.com");
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
