package com.sublime.sublimecash.sublime.Recharge;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.MoneyTransferAddActivity;
import com.sublime.sublimecash.sublime.PaymentHistoryActivity;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;

public class MoneyTransferActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    EditText txtMobileNumber;
    TextView btnAdd,txtName,acNumber,txtIFSC,txtLimit,btnTransfer;
    Profile myProfile;
    LinearLayout listBenific;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Sublime Cash ");
        actionBar.show();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myProfile = Session.GetProfile(getApplicationContext());

        listBenific = findViewById(R.id.listBenific);
        txtName = findViewById(R.id.txtName);
        acNumber = findViewById(R.id.acNumber);
        txtLimit = findViewById(R.id.txtLimit);
        txtIFSC = findViewById(R.id.txtIFSC);
        btnTransfer = findViewById(R.id.btnTransfer);

        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMobile();
            }
        });
    }
    public void AddMobile(){
        String Recharge_url= Constants.Application_URL+"/users/index.php/Money/Transfer_money";
        progressDialog = progressDialog.show(MoneyTransferActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Status = jObj.getString("status");

                    if (Status.equalsIgnoreCase("Invalid Mobile Number"))
                    {
                        Toast.makeText(MoneyTransferActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                    else if (Status.equalsIgnoreCase("Remitter Not Found")){
                        Intent intent = new Intent(MoneyTransferActivity.this, MoneyTransferAddActivity.class);
                        startActivity(intent);
                        MoneyTransferActivity.this.finish();
                    }
                    JSONObject data = jObj.getJSONObject("data");
                    JSONObject Remitter = data.getJSONObject("remitter");
                    String RemitterName = Remitter.getString("name");
                    String TotalLimit = Remitter.getString("remaininglimit");
                    JSONObject beneficiary = data.getJSONObject("beneficiary");
                    if (beneficiary.length()==0){
                        Intent intent = new Intent(MoneyTransferActivity.this,AddBenefisiaryActivity.class);
                        intent.putExtra("remitterName",RemitterName);
                        startActivity(intent);
                    }else {
                    JSONObject item = beneficiary.getJSONObject("item");
                    final String beneficId = item.getString("id");
                    final String Name = item.getString("name");
                    final String Account = item.getString("account");
                    final String ifsc = item.getString("ifsc");
                    Toast.makeText(MoneyTransferActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                    if(Status.equalsIgnoreCase("Transaction Successful")) {
                        listBenific.setVisibility(View.VISIBLE);
                        txtName.setText(Name);
                        acNumber.setText(Account);
                        txtIFSC.setText(ifsc);
                        txtLimit.setText(TotalLimit);
                        btnTransfer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MoneyTransferActivity.this, MoneyTransferlistActivity.class);
                                intent.putExtra("name", Name);
                                intent.putExtra("AcNumber", Account);
                                intent.putExtra("ifsc", ifsc);
                                intent.putExtra("Benefic", beneficId);
                                startActivity(intent);
                            }
                        });
                    }
                    } if(Status.equalsIgnoreCase("OTP sent successfully")){
                        OtpDialog();
                    }else {
                        Toast.makeText(MoneyTransferActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                        MoneyTransferActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MoneyTransferActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Customernumber", txtMobileNumber.getText().toString());
                params.put("user_id",myProfile.UserID);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void OtpDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_transfer, null);
        dialogBuilder.setView(dialogView);
        final EditText editAmount = dialogView.findViewById(R.id.editAmount);
        dialogBuilder.setMessage("Enter OTP");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String Transfer_url= Constants.Application_URL+"/users/index.php/Money/remitterValidation";
                progressDialog = progressDialog.show(MoneyTransferActivity.this, "", "Validating...", false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Transfer_url, new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String Status = jObj.getString("status");
                            String MSG = jObj.getString("msg");
                            Toast.makeText(MoneyTransferActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MoneyTransferActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", myProfile.UserLogin);
                        params.put("amount",editAmount.getText().toString());
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
