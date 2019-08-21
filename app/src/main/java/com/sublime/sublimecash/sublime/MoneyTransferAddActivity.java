package com.sublime.sublimecash.sublime;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.sublime.sublimecash.sublime.Recharge.MobilePostpaidActivity;
import com.sublime.sublimecash.sublime.Recharge.MoneyTransferActivity;
import com.sublime.sublimecash.sublime.Recharge.OtpForRemitterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;

public class MoneyTransferAddActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    EditText txtFirstName,txtLastName,txtMobile,txtPincode;
    Button btnAdd;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer_add);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Remitter");
        actionBar.show();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myProfile = Session.GetProfile(getApplicationContext());

        btnAdd = findViewById(R.id.btnAdd);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtMobile = findViewById(R.id.txtMobile);
        txtPincode = findViewById(R.id.txtPincode);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRemitter();
            }
        });
    }
    public void AddRemitter(){
       final String mobile = txtMobile.getText().toString();
       final   String Name = txtFirstName.getText().toString()+ txtLastName.getText().toString();
        String Recharge_url= Constants.Application_URL+"/users/index.php/Money/AddRemitter";
        progressDialog = progressDialog.show(MoneyTransferAddActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject Result = jObj.getJSONObject("result");
                    String Status = Result.getString("status");
                    if (Status.equalsIgnoreCase("Invalid or Empty pincode")){
                        Toast.makeText(MoneyTransferAddActivity.this, "Invalid or Empty pincode", Toast.LENGTH_SHORT).show();
                    }else {
                        JSONObject data = Result.getJSONObject("data");
                        JSONObject Remitter = data.getJSONObject("remitter");
                        String RemitterId = Remitter.getString("id");
                        if (Status.equalsIgnoreCase("OTP sent successfully")) {
                            Intent confirmation = new Intent(MoneyTransferAddActivity.this, OtpForRemitterActivity.class);
                            confirmation.putExtra("Mobile", mobile);
                            confirmation.putExtra("name", Name);
                            confirmation.putExtra("remitterId", RemitterId);
                            startActivity(confirmation);
                        }
                    }
                    MoneyTransferAddActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MoneyTransferAddActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", txtMobile.getText().toString());
                params.put("name", txtFirstName.getText().toString());
                params.put("surname", txtLastName.getText().toString());
                params.put("pincode", txtPincode.getText().toString());
                params.put("user_id",myProfile.UserID);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
