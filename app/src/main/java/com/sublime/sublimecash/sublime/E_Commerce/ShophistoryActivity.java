package com.sublime.sublimecash.sublime.E_Commerce;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.sublime.sublimecash.sublime.HomeActivity;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Session;
import Model.EcomShopHistory;
import Model.Profile;

public class ShophistoryActivity extends AppCompatActivity {
    TextView txtDate,txtOrderId,txtTotalAmount,txtStatus,txtStatusComment,billPayment,billDetail,
            TotalAmount;
    ImageView imgItem;
    LinearLayout btnAnotherPay,btnShowReceipt;
    Profile myProfile;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shophistory);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();
        txtDate = findViewById(R.id.txtDate);
        txtOrderId = findViewById(R.id.txtOrderId);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        txtStatus = findViewById(R.id.txtStatus);
        txtStatusComment = findViewById(R.id.txtStatusComment);
        billPayment = findViewById(R.id.billPayment);
        billDetail = findViewById(R.id.billDetail);
        TotalAmount = findViewById(R.id.TotalAmount);
        imgItem = findViewById(R.id.imgItem);
        btnAnotherPay = findViewById(R.id.btnAnotherPay);
        btnShowReceipt= findViewById(R.id.btnShowReceipt);
        btnAnotherPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShophistoryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btnShowReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShophistoryActivity.this, ReceiptActivity.class);
                startActivity(intent);
            }
        });
        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        getDetails();
    }
    public void getDetails(){
        String url= "http://www.sublimecash.com/ws2/index.php/front_controller/get_order_histroy";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jOb = new JSONObject(response);
                    String Address = jOb.getString("add");
                    String Item = jOb.getString("item");
                    JSONArray jsonArray = new JSONArray(Address);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        EcomShopHistory shop = new EcomShopHistory();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        shop.name = jObj.getString("name");
                        shop.email = jObj.getString("email");
                        shop.address = jObj.getString("address");
                        shop.created = jObj.getString("created");
                        shop.amount = jObj.getString("amount");
                        shop.phone = jObj.getString("phone");
                    }
                    JSONArray jsArray = new JSONArray(Item);
                    for (int j = 0; j < jsArray.length(); j++) {
                        EcomShopHistory shop1 = new EcomShopHistory();
                        JSONObject jObj1 = jsArray.getJSONObject(j);
                        shop1.order_id = jObj1.getString("order_id");
                        shop1.product_id = jObj1.getString("product_id");
                        shop1.quantity = jObj1.getString("quantity");
                        shop1.prod_name = jObj1.getString("prod_name");
                        shop1.sku_number = jObj1.getString("sku_number");
                        shop1.image = jObj1.getString("image");
                        shop1.Vendor_name = jObj1.getString("Vendor_name");
                        shop1.status = jObj1.getString("status");
                        shop1.sub_total = jObj1.getString("sub_total");
                        shop1.user_email = jObj1.getString("user_email");

                        txtDate.setText(shop1.created);
                        txtOrderId.setText(shop1.order_id);
                        txtTotalAmount.setText("\u20B9"+shop1.sub_total);
                        txtStatus.setText(shop1.status);
                        txtStatus.setTextColor(Color.rgb(12,123,55));
                        txtStatusComment.setText("Thank you for your Payment. Your payment will be updated at biller's end within 24 hrs.");
                        billPayment.setText(shop1.prod_name);
                        billDetail.setText("Product :  "+shop1.sku_number+"\nVender : "+shop1.Vendor_name);
                        TotalAmount.setText("\u20B9"+shop1.sub_total);
                        String url1 = "http://sublimecash.com/upload/product/" +shop1.image;
                        Picasso.with(getApplicationContext()).load(url1).into(imgItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
