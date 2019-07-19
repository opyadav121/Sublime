package com.sublime.sublimecash.sublime.Recharge;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
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
import Model.Gas;
import Model.Oparater;
import Model.Profile;

public class GasBillActivity extends AppCompatActivity {

    GridView gridViewGas;
    List<Gas> OperatorList=new ArrayList<>();
    AdapterOperator adapterOperator;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_bill);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Select Provider");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());
        gridViewGas = findViewById(R.id.gridViewGas);
        adapterOperator=new AdapterOperator(GasBillActivity.this, R.layout.gridview_gas, OperatorList);
        gridViewGas.setAdapter(adapterOperator);
        Operators();
    }

    public void Operators() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = Constants.Application_URL+"/users/index.php/Recharge/moblie_recharge";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Gas opt = new Gas();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        opt.OperatorName = jObj.getString("operator_name");
                        opt.optImageName = jObj.getString("image");
                        opt.OptID = jObj.getString("operator_code");
                        opt.opType = jObj.getString("OPType");
                        if (opt.opType.equalsIgnoreCase("Gas")) {
                            OperatorList.add(opt);
                        }else {

                        }
                    }
                    adapterOperator.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GasBillActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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

        gridViewGas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gas optname  = (Gas) adapterOperator.getItem(position);
                String optName = optname.OperatorName;
                String optId = optname.OptID;
                String optType = optname.opType;
                Intent intent = new Intent(GasBillActivity.this, GasBillPayActivity.class);
                intent.putExtra("optName", optName);
                intent.putExtra("OptId", optId);
                intent.putExtra("optType", optType);
                startActivity(intent);
            }
        });

    }

    class AdapterOperator extends ArrayAdapter {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterOperator(Context context, int resource, List<Gas> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return OperatorList.size();
        }

        @Nullable
        @Override
        public Gas getItem(int position) {
            return OperatorList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.gridview_gas, null);
                    holder = new ViewHolder();
                    holder.txtOperatorName = convertView.findViewById(R.id.optName);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Gas opt = getItem(position);
                holder.txtOperatorName.setText(opt.OperatorName);
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
        TextView txtOperatorName;
    }
}
