package com.example.omprakash.sublime.Recharge;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.omprakash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Common.Constants;

public class MobilePostpaidActivity extends AppCompatActivity {

    RadioGroup RadioBtnGroup;
    RadioButton rbPrepaid,rbPostpaid,rbDatacard;
    EditText txtMobileNumber,txtOperator,txtAmount;
    ListView listViewOperator;
    Button btnPayment;
    List<String> OperatorList= new ArrayList<>();
    HashMap<String,Integer> OperatorHashMap = new HashMap<>();
    ArrayAdapter<String> adapterOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_postpaid);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Mobile Recharge");
        actionBar.show();

        RadioBtnGroup = findViewById(R.id.RadioBtnGroup);
        RadioBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.rbPostpaid){

                }
               /* switch (checkedId) {
                    case R.id.rbPrepaid:
                        RadioButton radioButton= rbPrepaid.;
                        break;
                    case R.id.rbPostpaid:
                        RadioButton value = Integer.parseInt(((RadioButton) findViewById(R.id.radio2).getText()) * 3);
                        break;
                    case R.id.rbDatacard:
                        RadioButton value = Integer.parseInt(((RadioButton) findViewById(R.id.radio3).getText()) * 3);
                        break;
                }  */
            }
        });
       // rbPrepaid = findViewById(R.id.rbPrepaid);
       // rbPostpaid = findViewById(R.id.rbPostpaid);
       // rbDatacard = findViewById(R.id.rbDatacard);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtOperator = findViewById(R.id.txtOperator);
        txtAmount = findViewById(R.id.txtAmount);
        listViewOperator = findViewById(R.id.listViewOperator);
        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recharge();
            }
        });

        setOperatorSpinner();

    }

    private void Recharge()
    {
        String MobileNo = txtMobileNumber.getText().toString();
        String Operator = listViewOperator.getSelectedItem().toString();
        String Amount = txtAmount.getText().toString();
        String TokenId = "4dDLah75ISOtCToDDE2Z7w==";
        String UserId = "gufran.28@gmail.com";
        String child = "123456";

        try {

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            String  url =  "http://recharge4ushop.online/Recharge/Recharge";

            String reqBody = "{\"Customernumber\":\""+ MobileNo +"\",\"Tokenid\":\""+ TokenId +"\",\"Userid\":\""+ UserId +
                    "\",\"Amount\":\"" +Amount + "\",\"Optcode\":\""+ Operator +"\",\"Yourrchid\":\""+ child +
                    "\",\"optional1\":\"\",,\"optional2\":\"\"}";

            JSONObject jsRequest = new JSONObject(reqBody);
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, url, jsRequest,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jObj) {
                    try {

                    }

                    catch (Exception jEx) {
                        int a=1;
                        a++;

                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int a=1;
                   // progressBar.setVisibility(View.GONE);
                   // txtMessage.setText("! Volley Exception");
                }
            });
            RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
            jsArrayRequest.setRetryPolicy(rPolicy);
            queue.add(jsArrayRequest);
            //*******************************************************************************************************
        } catch (Exception js) {
            int a=1;
            a++;
          //  progressBar.setVisibility(View.GONE);
          //  txtMessage.setText("! Network Exception");


        } finally {

        }
    }
    public void setOperatorSpinner()
    {
        String url = Constants.Application_URL+ "/api/University/All";
        try{
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                   // progressBar.setVisibility(View.GONE);
                    try {
                        int x = response.length();
                        for (int i = 0; i <x; i++) {

                            JSONObject jObj = response.getJSONObject(i);

                            String OperatorName = jObj.getString("OperatorName");
                            int OperatorID = jObj.getInt("OperatorID");
                            OperatorHashMap.put(OperatorName, OperatorID);
                            OperatorList.add(OperatorName);
                        }
                        adapterOperator.notifyDataSetChanged();

                    } catch (JSONException e) {
                        int a=1;
                    }
                    catch (Exception ex)
                    {
                        int a=1;
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // progressBar.setVisibility(View.GONE);

                }
            });
            RetryPolicy rPolicy = new DefaultRetryPolicy(0, -1, 0);
            jsArrayRequest.setRetryPolicy(rPolicy);
            queue.add(jsArrayRequest);
        }catch (Exception ex){
            int a=1;
           // progressBar.setVisibility(View.GONE);
        }



        adapterOperator = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, OperatorList);
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
                String Operator = (String) listViewOperator.getItemAtPosition(position);
                //selectedUniversity = universityHashMap.get(University);
                txtOperator.setText(Operator);
                listViewOperator.setVisibility(View.GONE);
            }
        });
    }

}
