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
import Model.DTHopt;
import Model.Oparater;
import Model.Profile;

public class DTHActivity extends AppCompatActivity {

    GridView gridViewDTH;
    List<DTHopt> DTHList=new ArrayList<>();
    AdapterDTH adapterOperator;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dth);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Select DTH");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());
        gridViewDTH = findViewById(R.id.gridViewDTH);
        adapterOperator=new AdapterDTH(DTHActivity.this, R.layout.gridview_dth, DTHList);
        gridViewDTH.setAdapter(adapterOperator);
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
                        DTHopt opt = new DTHopt();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        opt.OperatorName = jObj.getString("operator_name");
                        opt.optImageName = jObj.getString("image");
                        opt.OptID = jObj.getString("operator_code");
                        String Status = jObj.getString("status");
                        opt.opType = jObj.getString("OPType");
                        if (opt.opType.equalsIgnoreCase("DTH")&& Status.equalsIgnoreCase("1")) {
                            DTHList.add(opt);
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
                Toast.makeText(DTHActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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

        gridViewDTH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DTHopt optname  = (DTHopt) adapterOperator.getItem(position);
                String optImageName = optname.optImageName;
                String optName = optname.OperatorName;
                String optId = optname.OptID;
                String optType = optname.opType;
                Intent intent = new Intent(DTHActivity.this, DTHRechargeActivity.class);
                intent.putExtra("Image", optImageName);
                intent.putExtra("optName", optName);
                intent.putExtra("OptId", optId);
                intent.putExtra("optType", optType);
                startActivity(intent);
            }
        });
    }

    class AdapterDTH extends ArrayAdapter {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterDTH(Context context, int resource, List<DTHopt> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return DTHList.size();
        }

        @Nullable
        @Override
        public DTHopt getItem(int position) {
            return DTHList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.gridview_dth, null);
                    holder = new ViewHolder();
                    holder.image = convertView.findViewById(R.id.image);
                    holder.txtOperatorName = convertView.findViewById(R.id.optName);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                DTHopt opt = getItem(position);
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
