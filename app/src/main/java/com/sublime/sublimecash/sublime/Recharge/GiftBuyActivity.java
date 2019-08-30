package com.sublime.sublimecash.sublime.Recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.text.HtmlCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.sublime.sublimecash.sublime.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import Common.Session;
import Model.Profile;

public class GiftBuyActivity extends AppCompatActivity {
    TextView txtTC,txtDescription;
    Button btnBuy,btnClose;
    ImageView imagebuy;
    EditText txtAmount,txtSenderName,txtSenderEmail,txtSenderPincode,txtReceiverName,txtReceiverEmail,txtMsg,txtReceiverMob,
            txtReceiverAddress,txtReceiverPinCode,txtReceiverCity,txtReceiverState;
    String imgName,CardNane,cardId,tnc_mobile;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Profile myProfile;
    ToggleButton toggle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_buy);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Gift Card");
        actionBar.show();
        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Intent intent = getIntent();
        imgName = intent.getStringExtra("Image");
        CardNane = intent.getStringExtra("optName");
        cardId = intent.getStringExtra("OptId");

        imagebuy = findViewById(R.id.imagebuy);
        txtDescription = findViewById(R.id.txtDescription);
        txtAmount = findViewById(R.id.txtAmount);
        txtSenderName = findViewById(R.id.txtSenderName);
        txtSenderEmail = findViewById(R.id.txtSenderEmail);
        txtReceiverName = findViewById(R.id.txtReceiverName);
        txtReceiverEmail = findViewById(R.id.txtReceiverEmail);
        txtMsg = findViewById(R.id.txtMsg);
        txtSenderPincode = findViewById(R.id.txtSenderPincode);
        txtReceiverMob = findViewById(R.id.txtReceiverMob);
        txtReceiverAddress = findViewById(R.id.txtReceiverAddress);
        txtReceiverPinCode = findViewById(R.id.txtReceiverPinCode);
        txtReceiverCity = findViewById(R.id.txtReceiverCity);
        txtReceiverState = findViewById(R.id.txtReceiverState);
        btnClose = findViewById(R.id.btnClose);
        txtTC = findViewById(R.id.txtTC);
        btnBuy = findViewById(R.id.btnBuy);
        toggle1 = findViewById(R.id.toggle1);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Purchage();
            }
        });

        String url1 = "http://www.sublimecash.com/users/opt/gift/" + imgName;
        Picasso.with(getApplicationContext()).load(url1).into(imagebuy);
        GetDetails();
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                txtTC.setText(HtmlCompat.fromHtml(tnc_mobile, 0));
                if (isChecked) {
                    txtTC.setVisibility(View.VISIBLE);
                } else {
                    txtTC.setVisibility(View.GONE);
                }
            }
        });

    }
    public void GetDetails() {
        String Recharge_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Voucher/Voucherdetail";
        progressDialog = progressDialog.show(GiftBuyActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Data = jObj.getString("data");
                    JSONObject jOb = new JSONObject(Data);
                    String desc = jOb.getString("description");
                    tnc_mobile = jOb.getString("tnc_mobile");
                    String sku = jOb.getString("sku");
                    txtDescription.setText(desc);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(GiftBuyActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                params.put("id", cardId);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void Purchage() {
        String Recharge_url = "http://www.digitalepay.co.in/Webservice/RT/index.php/Report/Vouchertransaction";
        progressDialog = progressDialog.show(GiftBuyActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Data = jObj.getString("data");
                    JSONObject jOb = new JSONObject(Data);
                    String desc = jOb.getString("description");
                    String tnc_mobile = jOb.getString("tnc_mobile");
                    String sku = jOb.getString("sku");
                    txtDescription.setText(desc);


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(GiftBuyActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                params.put("spkey",cardId);
                params.put("amount", txtAmount.getText().toString());
                params.put("sender_name",txtSenderName.getText().toString());
                params.put("sender_email", txtSenderEmail.getText().toString());
                params.put("sender_pincode",txtSenderPincode.getText().toString());
                params.put("receiver_name", txtReceiverName.getText().toString());
                params.put("receiver_email",txtReceiverEmail.getText().toString());
                params.put("gift_message", txtMsg.getText().toString());
                params.put("receiver_mobile",txtReceiverMob.getText().toString());
                params.put("receiver_address", txtReceiverAddress.getText().toString());
                params.put("receiver_pincode",txtSenderPincode.getText().toString());
                params.put("receiver_city", txtReceiverCity.getText().toString());
                params.put("receiver_state",txtReceiverState.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
