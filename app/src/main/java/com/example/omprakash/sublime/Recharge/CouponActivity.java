package com.example.omprakash.sublime.Recharge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omprakash.sublime.HomeActivity;
import com.example.omprakash.sublime.LoginActivity;
import com.example.omprakash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Session;
import Model.BinaryInc;
import Model.Coupons;
import Model.Profile;

public class CouponActivity extends AppCompatActivity {
    ArrayList<Coupons> couponList = new ArrayList<Coupons>();
    AdapterCoupon adapterCoupon;
    ListView listView_Coupon;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/CouponpinList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Coupons ");
        actionBar.show();
        requestQueue = Volley.newRequestQueue(this);

        listView_Coupon = findViewById(R.id.listView_Coupon);
        adapterCoupon =new AdapterCoupon(getApplicationContext(),0,couponList);
        listView_Coupon.setAdapter(adapterCoupon);
        Coupon();
    }

    public void Coupon()
    {
        progressDialog = progressDialog.show(CouponActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Coupons recharge = new Coupons();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        recharge.couponAmount = jObj.getString("joining_Amt");
                        recharge.PinNo = jObj.getString("pin_no");
                        recharge.Date = jObj.getString("date");
                        recharge.CouponName = jObj.getString("coupon_name");
                        couponList.add(recharge);
                    }
                    adapterCoupon.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(CouponActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", "user@tutorialvilla.com");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    class AdapterCoupon extends ArrayAdapter<Coupons> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterCoupon(Context context, int resource, List<Coupons> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return couponList.size();
        }
        @Nullable
        @Override
        public Coupons getItem(int position) {
            return couponList.get(position);
        }

        @Override
        public int getPosition(Coupons item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_coupon, null);
                    holder = new ViewHolder();
                    holder.txtPin = convertView.findViewById(R.id.txtPin);
                    holder.txtCouponAmount = convertView.findViewById(R.id.txtCouponAmount);
                    holder.txtCouponName = convertView.findViewById(R.id.txtCouponName);
                    holder.txtTransferDate = convertView.findViewById(R.id.txtTransferDate);
                    holder.btn_register = convertView.findViewById(R.id.btn_register);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Coupons history= getItem(position);
                holder.txtPin.setText(history.PinNo);
                holder.txtCouponAmount.setText(history.couponAmount);
                holder.txtCouponName.setText(history.CouponName );
                holder.txtTransferDate.setText(history.Date);
                holder.btn_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                Toast.makeText(getApplicationContext(),"Could not Load RentData", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }

    private class ViewHolder
    {
        TextView txtPin,txtCouponAmount,txtCouponName,txtTransferDate;
        Button btn_register;
    }
}
