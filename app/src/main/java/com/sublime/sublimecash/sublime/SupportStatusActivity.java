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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.Recharge.SWalletHistoryActivity;
import com.sublime.sublimecash.sublime.Recharge.SupportActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Session;
import Model.Complaint;
import Model.Profile;
import Model.SWallet;

public class SupportStatusActivity extends AppCompatActivity {
    ArrayList<Complaint> compList = new ArrayList<>();
    AdapterSupport adapterSupport;
    ListView listViewStatus;
    Profile myProfile;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_status);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Complaints ");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        listViewStatus = findViewById(R.id.listViewStatus);
        adapterSupport =new AdapterSupport(SupportStatusActivity.this,0,compList);
        listViewStatus.setAdapter(adapterSupport);
        GetStatus();
    }
    public void GetStatus(){
        try {
            String  Register_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/login/complain_status";
            progressDialog = progressDialog.show(SupportStatusActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>()  {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Complaint complaint = new Complaint();
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            complaint.Mobile = jObj.getString("mobile_no");
                            complaint.Status = jObj.getString("status");
                            complaint.Comp = jObj.getString("msg");
                            complaint.Date = jObj.getString("date");
                            compList.add(complaint);
                        }
                        adapterSupport.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    Toast.makeText(SupportStatusActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
            requestQueue.add(stringRequest);
        }catch (Exception e){
            int a = 1;
        }
    }
    class AdapterSupport extends ArrayAdapter<Complaint> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterSupport(Context context, int resource, List<Complaint> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return compList.size();
        }

        @Nullable
        @Override
        public Complaint getItem(int position) {
            return compList.get(position);
        }

        @Override
        public int getPosition(Complaint item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_support, null);
                    holder = new ViewHolder();
                    holder.txtMobile = convertView.findViewById(R.id.txtMobile);
                    holder.txtDate = convertView.findViewById(R.id.txtDate);
                    holder.txtMsg = convertView.findViewById(R.id.txtMsg);
                    holder.txtStatus = convertView.findViewById(R.id.txtStatus);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Complaint history= getItem(position);
                holder.txtMobile.setText(history.Mobile);
                holder.txtStatus.setText(history.Status);
                holder.txtMsg.setText(history.Comp);
                holder.txtDate.setText(history.Date);
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
        TextView txtMobile,txtStatus,txtMsg,txtDate;
    }
}
