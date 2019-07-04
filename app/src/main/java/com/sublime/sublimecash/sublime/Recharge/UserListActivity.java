package com.sublime.sublimecash.sublime.Recharge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;
import Model.Users;

public class UserListActivity extends AppCompatActivity {

    ArrayList<Users> UserHistory = new ArrayList<Users>();
    AdapterUsers adapterUsers;
    ListView listViewUsers;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("UserList ");
        actionBar.show();
        myProfile = Session.GetProfile(this);

        listViewUsers = findViewById(R.id.listViewUsers);
        adapterUsers =new AdapterUsers(UserListActivity.this,0,UserHistory);
        listViewUsers.setAdapter(adapterUsers);
        Users();
    }

    public void Users(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String UserList_url = Constants.Application_URL+"/users/index.php/Home/UserList";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserList_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Users recharge = new Users();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        recharge.Email = jObj.getString("email");
                        recharge.Mobile = jObj.getString("mobile");
                        recharge.Address = jObj.getString("address");
                        recharge.Date = jObj.getString("activation_date");
                        recharge.CouponName = jObj.getString("coupon_name");
                        recharge.Amount = jObj.getString("account");
                        recharge.ReferId = jObj.getString("refer_id");
                        recharge.UnderUser = jObj.getString("under_userid");
                        recharge.Status = jObj.getString("status");
                        recharge.Side = jObj.getString("side");
                        UserHistory.add(recharge);
                    }
                    adapterUsers.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserListActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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

    class AdapterUsers extends ArrayAdapter<Users> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterUsers(Context context, int resource, List<Users> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return UserHistory.size();
        }

        @Nullable
        @Override
        public Users getItem(int position) {
            return UserHistory.get(position);
        }

        @Override
        public int getPosition(Users item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_users, null);
                    holder = new ViewHolder();
                    holder.txtEmail = convertView.findViewById(R.id.txtEmail);
                    holder.txtMobile = convertView.findViewById(R.id.txtMobile);
                    holder.txtAddress = convertView.findViewById(R.id.txtAddress);
                    holder.txtPlan = convertView.findViewById(R.id.txtPlan);
                    holder.txtDate = convertView.findViewById(R.id.txtDate);
                    holder.txtSide = convertView.findViewById(R.id.txtSide);
                    holder.txtReferId = convertView.findViewById(R.id.txtReferId);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Users history= getItem(position);
                holder.txtEmail.setText(history.Email);
                holder.txtMobile.setText(history.Mobile);
                holder.txtAddress.setText(history.Address );
                holder.txtPlan.setText(history.CouponName+", "+" \u20B9"+history.Amount);
                holder.txtDate.setText(history.Date);
                holder.txtSide.setText(history.Side);
                holder.txtReferId.setText(history.ReferId);
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
        TextView txtEmail,txtMobile,txtAddress,txtPlan,txtDate,txtSide,txtReferId;
    }
}
