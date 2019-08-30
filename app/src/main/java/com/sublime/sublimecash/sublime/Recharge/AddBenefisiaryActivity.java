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
import android.widget.TextView;
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
import Model.RemitterDetails;

public class AddBenefisiaryActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    EditText txtMobile,name,txtAccountNumber,txtIFSC;
    TextView txtName;
    Button btnAdd;
    Profile myProfile;
    String Name,remmitId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_benefisiary);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Sublime Cash ");
        actionBar.show();
        Intent intent = getIntent();
        Name = intent.getStringExtra("remitterName");
        remmitId = intent.getStringExtra("remitterId");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myProfile = Session.GetProfile(getApplicationContext());
        txtMobile = findViewById(R.id.txtMobile);
        btnAdd = findViewById(R.id.btnAdd);
        txtName = findViewById(R.id.txtName);
        txtAccountNumber = findViewById(R.id.txtAccountNumber);
        txtIFSC = findViewById(R.id.txtIFSC);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBeneficiary();
            }
        });
    }

    public void AddBeneficiary(){
        String Recharge_url= Constants.Application_URL+"/users/index.php/Money/Add_Beneficiary";
        progressDialog = progressDialog.show(AddBenefisiaryActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);

                    String Status = jObj.getString("status");
                    Toast.makeText(AddBenefisiaryActivity.this, ""+Status, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AddBenefisiaryActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", txtMobile.getText().toString());
                params.put("name",name.getText().toString());
                params.put("remitterid", remmitId);
                params.put("ifsc",txtIFSC.getText().toString());
                params.put("account", txtAccountNumber.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
