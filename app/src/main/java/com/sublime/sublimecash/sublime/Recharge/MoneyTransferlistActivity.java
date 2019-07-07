package com.sublime.sublimecash.sublime.Recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;

public class MoneyTransferlistActivity extends AppCompatActivity {
    TextView txtName;
    EditText name,txtAccountNumber,txtBeneficId,txtAmount;
    Spinner spType;
    Button btnCancel,btnPay;
    ProgressDialog progressDialog;
    Profile myProfile;
    RequestQueue requestQueue;
    String BeneficId,Name,AcNumber,mode,RemitterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transferlist);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Money Transfer");
        actionBar.show();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myProfile = Session.GetProfile(getApplicationContext());

        Intent intent = getIntent();
        Name = intent.getStringExtra("name");
        AcNumber = intent.getStringExtra("AcNumber");
        BeneficId = intent.getStringExtra("Benefic");
        String ifsc = intent.getStringExtra("ifsc");
        RemitterName = intent.getStringExtra("remitterName");


        name = findViewById(R.id.name);
        txtName = findViewById(R.id.txtName);
        txtAccountNumber = findViewById(R.id.txtAccountNumber);
        txtBeneficId = findViewById(R.id.txtBeneficId);
        txtAmount = findViewById(R.id.txtAmount);
        spType = findViewById(R.id.spType);
        btnCancel = findViewById(R.id.btnCancel);
        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoneyTransfer();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TransferType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                mode = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        name.setText(Name);
        txtAccountNumber.setText(AcNumber);
        txtBeneficId.setText(BeneficId);
        txtName.setText("Hi  "+RemitterName);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyTransferlistActivity.this,MoneyTransferActivity.class);
                startActivity(intent);
            }
        });

    }

    public void MoneyTransfer(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url= Constants.Application_URL+"/users/index.php/Money/TransferMoney";
        progressDialog = progressDialog.show(MoneyTransferlistActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    Toast.makeText(MoneyTransferlistActivity.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MoneyTransferlistActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", myProfile.UserID);
                params.put("amount", txtAmount.getText().toString());
                params.put("beneficiaryid", BeneficId);
                params.put("mode", myProfile.UserID);
                params.put("beneficiary_name",Name);
                params.put("account_no", AcNumber);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
