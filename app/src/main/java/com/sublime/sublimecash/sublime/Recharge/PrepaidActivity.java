package com.sublime.sublimecash.sublime.Recharge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Constants;
import Model.Oparater;
import de.hdodenhof.circleimageview.CircleImageView;

public class PrepaidActivity extends AppCompatActivity {

    GridView gridViewPrepaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepaid);
    }


    public void Operators()
    {
     /*   RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String RechargeHitory_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/moblie_recharge";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RechargeHitory_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Oparater opt = new Oparater();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        opt.OperatorName = jObj.getString("operator_name");
                        opt.optImageName = jObj.getString("image");
                        opt.OptID = jObj.getString("operator_code");
                        opt.opType = jObj.getString("OPType");
                        // OperatorHashMap.put(opt.OperatorName, opt.OptID);
                        OperatorList.add(opt);
                    }
                    adapterOperator.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrepaidActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
        adapterOperator = new AdapterOperator(this, android.R.layout.simple_spinner_dropdown_item,OperatorList);
        adapterOperator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listViewOperator.setAdapter(adapterOperator);
        txtOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewOperator.setVisibility(View.VISIBLE);

            }
        });

        listViewOperator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String opt = (String) listViewOperator.getItemAtPosition(position);
                txtOperator.setText(opt);
                listViewOperator.setVisibility(View.GONE);
            }
        });
    }

    class AdapterOperator extends ArrayAdapter {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterOperator(Context context, int resource, List<Oparater> objects) {

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
        public Oparater getItem(int position) {
            return OperatorList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_operator, null);
                    holder = new ViewHolder();
                    //  holder.iconOperator = convertView.findViewById(R.id.iconOperator);
                    holder.txtOperatorName = convertView.findViewById(R.id.txtOperatorName);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Oparater history= getItem(position);
                holder.txtOperatorName.setText(history.OperatorName);
                //  String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" + history.optImageName;
                //  Picasso.with(getApplicationContext()).load(url1).into(holder.iconOperator);
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                Toast.makeText(getApplicationContext(),"Could not Load RentData", Toast.LENGTH_LONG).show();
                return null;
            }
        }*/
    }

}
