package com.sublime.sublimecash.sublime.E_Commerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.PayMentGateWay;
import com.sublime.sublimecash.sublime.R;
import com.sublime.sublimecash.sublime.Recharge.AddMoneyActivity;
import com.sublime.sublimecash.sublime.Recharge.CouponRequestbySWalletActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;

public class PaymentOptionActivity extends AppCompatActivity {
    TextView SWalletBalance,payment,amounttoPay;
    Button payNow,btnPayU;
    Profile myProfile;
    CheckBox checkBox;
    int amount,SBalance;
    String toPay,Pay;

    ProgressDialog progressDialog;
    String url = "https://secure.payu.in";
    RequestQueue requestQueue;
    String id= "1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        toPay = intent.getStringExtra("Price");
        myProfile = Session.GetProfile(getApplicationContext());

        SWalletBalance = findViewById(R.id.SWalletBalance);
        payment = findViewById(R.id.payment);
        checkBox = findViewById(R.id.checkBox);
        payNow = findViewById(R.id.payNow);
        btnPayU = findViewById(R.id.btnPayU);
        amounttoPay = findViewById(R.id.amounttoPay);
        SWalletBalance.setText(" \u20B9"+myProfile.SWallet);
        payment.setText(" \u20B9"+toPay);
        SBalance = Integer.parseInt(myProfile.SWallet);
        amount = Integer.parseInt(toPay);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();
            }
        });
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnPayU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayU();
            }
        });
    }

    public void payment(){
        if (checkBox.isChecked()){
        if (amount > SBalance){
          int Payable = amount - SBalance;
          Pay = Integer.toString(Payable);
            amounttoPay.setText(" \u20B9"+Pay);
        }else if (amount <= SBalance){
           // int Payable1 = SBalance - amount;
           // Pay = Integer.toString(Payable1);
            amounttoPay.setText(" \u20B9"+toPay);
        }
    }else {
            amounttoPay.setText(" \u20B9"+toPay);
        }
    }
    public void PayU(){
        Intent intent = new Intent(PaymentOptionActivity.this, PayUforShopingActivity.class);
        intent.putExtra("FIRST_NAME",myProfile.UserName);
        intent.putExtra("EMAIL_ADDRESS",myProfile.original_email);
        intent.putExtra("PHONE_NUMBER",myProfile.MobileNumber);
        intent.putExtra("RECHARGE_AMT",toPay);
        intent.putExtra("EMAIL",myProfile.UserLogin);
        startActivity(intent);
    }

    public void PayOffline(){
            String Recharge_url = Constants.Application_URL + "/users/index.php/payment/transfer_coupon_quantity_swallet";
            progressDialog = progressDialog.show(PaymentOptionActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String Status = jObj.getString("Status");
                        PaymentOptionActivity.this.finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(PaymentOptionActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", myProfile.UserLogin);
                    params.put("pay_amount", toPay);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

}