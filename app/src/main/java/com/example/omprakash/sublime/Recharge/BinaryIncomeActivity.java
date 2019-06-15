package com.example.omprakash.sublime.Recharge;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omprakash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.BinaryInc;
import Model.Coupons;
import Model.EWallet;

public class BinaryIncomeActivity extends AppCompatActivity {
    ArrayList<BinaryInc> binaryIncs = new ArrayList<BinaryInc>();
    AdapterBinary adapterBinary;
    ListView listViewBinary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_income);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Binary Income ");
        actionBar.show();
        listViewBinary = findViewById(R.id.listViewBinary);
        adapterBinary =new AdapterBinary(BinaryIncomeActivity.this,0,binaryIncs);
        listViewBinary.setAdapter(adapterBinary);
        BinaryIncome();
    }
    public void BinaryIncome(){
        RequestQueue queue = Volley.newRequestQueue(BinaryIncomeActivity.this);
        String Binary_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Payment/daily_promoter";
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
                        binaryIncs.add(recharge);
                    }
                    adapterBinary.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BinaryIncomeActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
        queue.add(stringRequest);
    }

    class AdapterBinary extends ArrayAdapter<BinaryInc> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterBinary(Context context, int resource, List<BinaryInc> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return binaryIncs.size();
        }
        @Nullable
        @Override
        public BinaryInc getItem(int position) {
            return binaryIncs.get(position);
        }

        @Override
        public int getPosition(BinaryInc item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_binaryincome, null);
                    holder = new ViewHolder();
                    holder.txtBinaryAmount = convertView.findViewById(R.id.txtBinaryAmount);
                    holder.txtAmountType = convertView.findViewById(R.id.txtAmountType);
                    holder.txtReferUser = convertView.findViewById(R.id.txtReferUser);
                    holder.txtReferAmount = convertView.findViewById(R.id.txtReferAmount);
                    holder.txtDate = convertView.findViewById(R.id.txtDate);
                    holder.txtStatus = convertView.findViewById(R.id.txtStatus);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                BinaryInc history= getItem(position);
                holder.txtBinaryAmount.setText(history.BinaryAmount);
                holder.txtAmountType.setText(history.AmountType);
                holder.txtReferUser.setText(history.referUser );
                holder.txtReferAmount.setText(history.referUserAmount);
                holder.txtDate.setText(history.Date );
                holder.txtStatus.setText(history.Status);
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

    public class ViewHolder
    {
        TextView txtBinaryAmount,txtAmountType,txtReferUser,txtReferAmount,txtDate,txtStatus;
    }
}
