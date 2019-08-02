package com.sublime.sublimecash.sublime.Recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class CouponRequestbySWalletActivity extends AppCompatActivity {
    Spinner spPlans;
    EditText txtQuantity;
    Button btnSWallet,btnOnline;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Profile myProfile;
    String splan,CoupanName,pay_amount;
    int plan,qty,PayAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_requestby_swallet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Coupon Request");
        actionBar.show();

        myProfile = Session.GetProfile(this);
        requestQueue = Volley.newRequestQueue(this);
        spPlans = findViewById(R.id.spPlans);
        txtQuantity = findViewById(R.id.txtQuantity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.CouponRequest, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlans.setAdapter(adapter);
        spPlans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                splan = parent.getItemAtPosition(position).toString();//this is your selected item

                if (splan.equalsIgnoreCase("2240")){
                    CoupanName="Plan1";
                }else if (splan.equalsIgnoreCase("6720")){
                    CoupanName="Plan2";
                }else if (splan.equalsIgnoreCase("11200")){
                    CoupanName="Plan3";
                }else {
                    CoupanName="Plan4";
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSWallet = findViewById(R.id.btnSWallet);
        btnSWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SWalletPayment();
            }
        });
        btnOnline = findViewById(R.id.btnOnline);
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayURequest();
            }
        });

    }
    public void PayURequest(){
        String quantity = txtQuantity.getText().toString();
        if (quantity.equals("")) {
            txtQuantity.setError("Enter Quantity");
        }else{
            qty = Integer.parseInt(quantity);
            plan = Integer.parseInt(splan);
            PayAmount = plan * qty;
            pay_amount = String.valueOf(PayAmount);
            Intent intent = new Intent(CouponRequestbySWalletActivity.this, CouponPaymentGatway.class);
            intent.putExtra("FIRST_NAME", myProfile.UserName);
            intent.putExtra("EMAIL_ADDRESS", myProfile.original_email);
            intent.putExtra("PHONE_NUMBER", myProfile.MobileNumber);
            intent.putExtra("RECHARGE_AMT", pay_amount);
            intent.putExtra("EMAIL", myProfile.UserLogin);
            intent.putExtra("quantity", txtQuantity.getText().toString());
            intent.putExtra("coupon_name", CoupanName);
            startActivity(intent);
        }
    }

    public void SWalletPayment() {
        String quantity = txtQuantity.getText().toString();
        if (quantity.equals("")) {
            txtQuantity.setError("Enter Quantity");
        } else {
            qty = Integer.parseInt(txtQuantity.getText().toString());
            plan = Integer.parseInt(splan);
            PayAmount = plan * qty;
            pay_amount = String.valueOf(PayAmount);
            String Recharge_url = Constants.Application_URL + "/users/index.php/payment/transfer_coupon_quantity_swallet";
            progressDialog = progressDialog.show(CouponRequestbySWalletActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String Status = jObj.getString("Status");
                        CouponRequestbySWalletActivity.this.finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(CouponRequestbySWalletActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", myProfile.UserLogin);
                    params.put("coupon_name", CoupanName);
                    params.put("amount", splan);
                    params.put("quantity", txtQuantity.getText().toString());
                    params.put("pay_amount", pay_amount);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
