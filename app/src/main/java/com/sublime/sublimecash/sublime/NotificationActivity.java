package com.sublime.sublimecash.sublime;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.Recharge.BinaryIncomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.BinaryInc;
import Model.Profile;

public class NotificationActivity extends AppCompatActivity {
    TextView nodata;
    ListView listViewNotification;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notifications");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());

        listViewNotification = findViewById(R.id.listViewNotification);
        nodata = findViewById(R.id.nodata);

    }
    public void getNotifications(){
        RequestQueue queue = Volley.newRequestQueue(NotificationActivity.this);
        String Binary_url = Constants.Application_URL+"/users/index.php/Payment/daily_promoter";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Binary_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BinaryInc recharge = new BinaryInc();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        recharge.BinaryAmount = jObj.getString("amount");
                        recharge.AmountType = jObj.getString("amount_type");
                        recharge.Date = jObj.getString("date");
                        recharge.referUser = jObj.getString("refer_user");
                        recharge.referUserAmount = jObj.getString("refer_user_amount");
                        recharge.Status = jObj.getString("status");
                      //  binaryIncs.add(recharge);
                    }
                  //  adapterBinary.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NotificationActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                return params;
            }
        };
        queue.add(stringRequest);

    }
}
