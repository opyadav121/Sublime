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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import Model.Broadband;
import Model.LandlineOperater;
import Model.Oparater;
import Model.Profile;

public class BroadbandActivity extends AppCompatActivity {
    GridView gridViewBroadband;
    List<Broadband> OperatorList=new ArrayList<>();
    AdapterOperator adapterOperator;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadband);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Select Operator");
        actionBar.show();

        gridViewBroadband = findViewById(R.id.gridViewBroadband);
        myProfile = Session.GetProfile(getApplicationContext());
        adapterOperator=new AdapterOperator(BroadbandActivity.this, R.layout.gridview_prepaid, OperatorList);
        gridViewBroadband.setAdapter(adapterOperator);
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
                        Broadband opt = new Broadband();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        opt.OperatorName = jObj.getString("operator_name");
                        opt.optImageName = jObj.getString("image");
                        opt.OptID = jObj.getString("operator_code");
                        opt.opType = jObj.getString("OPType");
                        String Status = jObj.getString("status");
                        if (opt.opType.equalsIgnoreCase("BROADBAND")&& Status.equalsIgnoreCase("1")) {
                            OperatorList.add(opt);
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
                Toast.makeText(BroadbandActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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

        gridViewBroadband.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Broadband optname  = (Broadband) adapterOperator.getItem(position);
                String optImageName = optname.optImageName;
                String optName = optname.OperatorName;
                String optId = optname.OptID;
                String optType = optname.opType;
                Intent intent = new Intent(BroadbandActivity.this, BroadbandBillActivity.class);
                intent.putExtra("Image", optImageName);
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
        public AdapterOperator(Context context, int resource, List<Broadband> objects) {

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
        public Broadband getItem(int position) {
            return OperatorList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.gridview_prepaid, null);
                    holder = new ViewHolder();
                    holder.image = convertView.findViewById(R.id.image);
                    holder.txtOperatorName = convertView.findViewById(R.id.optName);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Broadband opt = getItem(position);
                holder.txtOperatorName.setText(opt.OperatorName);
                String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" + opt.optImageName;
                Picasso.with(getApplicationContext()).load(url1).into(holder.image);
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
        ImageView image;
    }
}
