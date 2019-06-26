package com.rapliot.omprakash.sublime.Recharge;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import Model.Oparater;
import Model.Profile;

public class BroadbandActivity extends AppCompatActivity {
    ListView listViewProvider;
    HashMap<String,String> OperatorHashMap = new HashMap<>();
    List<String> OperatorList1= new ArrayList<>();
    ArrayAdapter<String> adapterOperatorHash;
    Profile myProfile;
    EditText txtProvider,txtService;
    Button btnPay;
    String OperatorCode;

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
        actionBar.setTitle("Landlines Bill Payment");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());

        txtProvider= findViewById(R.id.txtProvider);
        txtService = findViewById(R.id.txtService);
        btnPay = findViewById(R.id.btnPay);
        listViewProvider = findViewById(R.id.listViewProvider);
        SetOperators();
    }
    public void SetOperators()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String RechargeHitory_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/moblie_recharge";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RechargeHitory_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Oparater opt = new Oparater();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        String OperatorName = jObj.getString("operator_name");
                        String OptID = jObj.getString("operator_code");
                        opt.opType = jObj.getString("OPType");
                        OperatorHashMap.put(OperatorName, OptID);
                        OperatorList1.add(OperatorName);
                    }
                    adapterOperatorHash.notifyDataSetChanged();
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
        adapterOperatorHash = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,OperatorList1);
        adapterOperatorHash.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listViewProvider.setAdapter(adapterOperatorHash);
        txtProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewProvider.setVisibility(View.VISIBLE);

            }
        });
        listViewProvider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String opt = (String) listViewProvider.getItemAtPosition(position);
                txtProvider.setText(opt);
                OperatorCode = OperatorHashMap.get(opt);
                listViewProvider.setVisibility(View.GONE);
            }
        });
    }
}
