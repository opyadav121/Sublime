package com.rapliot.omprakash.sublime.Recharge;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rapliot.omprakash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Session;
import Model.Profile;
import Model.Recharge_History;

public class RechargeHistoryActivity extends AppCompatActivity {

    ArrayList<Recharge_History> rechargeList = new ArrayList<>();
    AdapterRecharge adapterRecharge;
    ListView ListRecharges;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Profile myProfile;
    String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/CouponpinList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_history);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Recharge History ");
        actionBar.show();

        requestQueue = Volley.newRequestQueue(this);
        myProfile = Session.GetProfile(this);
        ListRecharges = findViewById(R.id.ListRecharges);
        adapterRecharge =new AdapterRecharge(RechargeHistoryActivity.this,0,rechargeList);
        ListRecharges.setAdapter(adapterRecharge);
        RechargeHistory();
    }

    public void RechargeHistory()
    {
        progressDialog = progressDialog.show(RechargeHistoryActivity.this, "", "Please wait...", false, false);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String RechargeHitory_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/history";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RechargeHitory_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Recharge_History recharge = new Recharge_History();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        recharge.MobileNO = jObj.getString("mob_no");
                        recharge.Operator = jObj.getString("operator");
                        recharge.Amount = jObj.getString("amount");
                        recharge.Date = jObj.getString("date");
                        recharge.Status = jObj.getString("status");
                        recharge.TransId = jObj.getString("transaction_id");
                        recharge.RemainingBal = jObj.getString("remaining_bal");
                        recharge.WalletBal = jObj.getString("wallet_bal");
                       // recharge.OperatorIcon = jObj.getString("");
                        rechargeList.add(recharge);
                    }
                    adapterRecharge.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RechargeHistoryActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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

    class AdapterRecharge extends ArrayAdapter<Recharge_History>{
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterRecharge(Context context, int resource, List<Recharge_History> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return rechargeList.size();
        }

        @Nullable
        @Override
        public Recharge_History getItem(int position) {
            return rechargeList.get(position);
        }

        @Override
        public int getPosition(Recharge_History item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_rechargehistory, null);
                    holder = new ViewHolder();
                    holder.histMobile = convertView.findViewById(R.id.histMobile);
                    holder.histOperator = convertView.findViewById(R.id.histOperator);
                    holder.histAmount = convertView.findViewById(R.id.histAmount);
                    holder.histOrderId = convertView.findViewById(R.id.histOrderId);
                    holder.histDate = convertView.findViewById(R.id.histDate);
                    holder.histStatus = convertView.findViewById(R.id.histStatus);
                    holder.hisTransId = convertView.findViewById(R.id.hisTransId);
                   // holder.OperatorIcon = convertView.findViewById(R.id.OperatorIcon);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Recharge_History history= getItem(position);
                holder.histMobile.setText(history.MobileNO);
                holder.histOperator.setText(history.Operator);
                holder.histAmount.setText(" \u20B9"+history.Amount );
                holder.histOrderId.setText(history.OrderId);
                holder.histDate.setText(history.Date);
                holder.histStatus.setText(history.Status );
                holder.hisTransId.setText(history.TransId);
               // holder.OperatorIcon.setVisibility(View.VISIBLE);
               // String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/idea.jpg";
               // Picasso.with(getApplicationContext()).load(url1).into(holder.OperatorIcon);
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
        TextView histMobile,histOperator,histAmount,histOrderId,histDate,histStatus,hisTransId;
        ImageView OperatorIcon;
    }
}
