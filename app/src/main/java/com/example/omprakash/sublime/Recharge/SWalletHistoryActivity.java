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

import Model.EWallet;
import Model.SWallet;

public class SWalletHistoryActivity extends AppCompatActivity {
    ArrayList<SWallet> SWalletHistory = new ArrayList<>();
    AdapterSWallet adapterSWallet;
    ListView listViewS_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swallet_history);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("S Wallet ");
        actionBar.show();

        listViewS_wallet = findViewById(R.id.listViewS_wallet);
        adapterSWallet =new AdapterSWallet(SWalletHistoryActivity.this,0,SWalletHistory);
        listViewS_wallet.setAdapter(adapterSWallet);

        SwalletHistory();
    }

    public void SwalletHistory(){
        RequestQueue queue = Volley.newRequestQueue(SWalletHistoryActivity.this);
        String SWalletHistory_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Payment/S_WalletHistory";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SWalletHistory_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        SWallet recharge = new SWallet();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        recharge.Credit = jObj.getString("credit");
                        recharge.Type = jObj.getString("type");
                        recharge.Amount = jObj.getString("amount");
                        recharge.Date = jObj.getString("date");
                        recharge.Debit = jObj.getString("debit");
                        SWalletHistory.add(recharge);
                    }
                    adapterSWallet.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SWalletHistoryActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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

    class AdapterSWallet extends ArrayAdapter<SWallet> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterSWallet(Context context, int resource, List<SWallet> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return SWalletHistory.size();
        }

        @Nullable
        @Override
        public SWallet getItem(int position) {
            return SWalletHistory.get(position);
        }

        @Override
        public int getPosition(SWallet item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_e_wallet, null);
                    holder = new ViewHolder();
                    holder.EwAmount = convertView.findViewById(R.id.EwAmount);
                    holder.EwDate = convertView.findViewById(R.id.EwDate);
                    holder.EwType = convertView.findViewById(R.id.EwType);
                    holder.EwCredit = convertView.findViewById(R.id.EwCredit);
                    holder.EwDebit = convertView.findViewById(R.id.EwDebit);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                SWallet history= getItem(position);
                holder.EwAmount.setText(history.Amount);
                holder.EwDate.setText(history.Date);
                holder.EwType.setText(history.Type );
                holder.EwCredit.setText(history.Credit);
                holder.EwDebit.setText(history.Debit);
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
        TextView EwAmount,EwDate,EwType,EwCredit,EwDebit;
    }
}