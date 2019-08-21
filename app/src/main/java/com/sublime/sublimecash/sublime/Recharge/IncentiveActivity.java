package com.sublime.sublimecash.sublime.Recharge;

import android.app.ProgressDialog;
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
import com.sublime.sublimecash.sublime.SupportStatusActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Session;
import Model.Complaint;
import Model.Incentive;
import Model.Profile;

public class IncentiveActivity extends AppCompatActivity {
    ArrayList<Incentive> IncList = new ArrayList<>();
    AdapterIncentive adapterIncentive;
    ListView listViewIncentive;
    Profile myProfile;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incentive);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Incentive");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        listViewIncentive = findViewById(R.id.listViewIncentive);
        adapterIncentive =new AdapterIncentive(IncentiveActivity.this,0,IncList);
        listViewIncentive.setAdapter(adapterIncentive);
        GetStatus();
    }
    public void GetStatus(){
        try {
            String  Register_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/home/incentive";
            progressDialog = progressDialog.show(IncentiveActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>()  {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Incentive complaint = new Incentive();
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            complaint.Type = jObj.getString("amount_type");
                            complaint.Date = jObj.getString("date");
                            complaint.Amount = jObj.getString("amount");
                            String refer_user = jObj.getString("refer_user");
                            String userid = jObj.getString("userid");
                            if (refer_user.equalsIgnoreCase(userid)){
                                complaint.RefBy = "Self";
                            }else {
                                complaint.RefBy = refer_user;
                            }
                            IncList.add(complaint);
                        }
                        adapterIncentive.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    Toast.makeText(IncentiveActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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

    class AdapterIncentive extends ArrayAdapter<Incentive> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterIncentive(Context context, int resource, List<Incentive> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return IncList.size();
        }

        @Nullable
        @Override
        public Incentive getItem(int position) {
            return IncList.get(position);
        }

        @Override
        public int getPosition(Incentive item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_incentive, null);
                    holder = new ViewHolder();
                    holder.txtName = convertView.findViewById(R.id.txtName);
                    holder.txtDate = convertView.findViewById(R.id.txtDate);
                    holder.txtAmount = convertView.findViewById(R.id.txtAmount);
                    holder.txtType = convertView.findViewById(R.id.txtType);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Incentive history= getItem(position);
                holder.txtType.setText(history.Type);
                holder.txtName.setText(history.RefBy);
                holder.txtAmount.setText(history.Amount);
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
        TextView txtType,txtName,txtAmount,txtDate;
    }
}
