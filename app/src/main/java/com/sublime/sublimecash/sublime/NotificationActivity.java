package com.sublime.sublimecash.sublime;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import com.sublime.sublimecash.sublime.Recharge.BinaryIncomeActivity;
import com.sublime.sublimecash.sublime.Recharge.DTHActivity;
import com.sublime.sublimecash.sublime.Recharge.DTHRechargeActivity;
import com.sublime.sublimecash.sublime.Recharge.RechargeHistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.BinaryInc;
import Model.DTHopt;
import Model.Notification;
import Model.Profile;
import Model.Recharge_History;

public class NotificationActivity extends AppCompatActivity {
    ListView listViewNotification;
    Profile myProfile;
    ArrayList<Notification> NotificationList = new ArrayList<>();
    AdapterNotification adapterNotification;
    ProgressDialog progressDialog;

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

        adapterNotification =new AdapterNotification(NotificationActivity.this,0,NotificationList);
        listViewNotification.setAdapter(adapterNotification);
        getNotifications();
    }
    public void getNotifications() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/message";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Notification notification = new Notification();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        notification.title = jObj.getString("title");
                        notification.Msg = jObj.getString("message");
                        notification.date = jObj.getString("date");
                        NotificationList.add(notification);

                    }
                    adapterNotification.notifyDataSetChanged();
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

    class AdapterNotification extends ArrayAdapter<Notification> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterNotification(Context context, int resource, List<Notification> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return NotificationList.size();
        }

        @Nullable
        @Override
        public Notification getItem(int position) {
            return NotificationList.get(position);
        }

        @Override
        public int getPosition(Notification item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_notification, null);
                    holder = new ViewHolder();
                    holder.date = convertView.findViewById(R.id.date);
                    holder.title = convertView.findViewById(R.id.title);
                    holder.msg = convertView.findViewById(R.id.msg);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Notification notifi = getItem(position);
                holder.date.setText(notifi.date);
                holder.title.setText(notifi.title);
                holder.msg.setText(notifi.Msg);
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                Toast.makeText(getApplicationContext(),"Could not Load Data", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }
    private class ViewHolder
    {
        TextView date,title,msg;
    }
}
