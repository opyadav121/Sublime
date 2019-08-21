package com.sublime.sublimecash.sublime.Recharge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.PaymentHistoryActivity;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;

public class WaterBillActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String optImage,optName,OptId,optType;
    TextView txtBWallet,txtEWallet,txtSWallet,btnTransfer,txtOperator,txtsbWallet;
    EditText txtMobileNumber,txtAmount;
    Button btnPay;
    ProgressDialog progressDialog;
    Profile myProfile;
    String RandomChildCode="";
    final Context context = this;
    Double Remain,bal,restAmt,amt;
    String dueAmount,Name,Bill,customerNo,dueDate,refId,billunit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_bill);
        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        optName = intent.getStringExtra("optName");
        OptId = intent.getStringExtra("OptId");
        optType = intent.getStringExtra("optType");

        txtBWallet = findViewById(R.id.txtBWallet);
        txtEWallet = findViewById(R.id.txtEWallet);
        txtSWallet = findViewById(R.id.txtSWallet);
        txtOperator = findViewById(R.id.txtOperator);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtAmount = findViewById(R.id.txtAmount);
        txtOperator.setText(optName);
        txtBWallet.setText(" \u20B9"+myProfile.PendingWallet);
        txtEWallet.setText(" \u20B9"+myProfile.EWallet);
        txtSWallet.setText(" \u20B9"+myProfile.SWallet);
        bal = Double.parseDouble(myProfile.SWallet);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });
        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetBill();
            }
        });
    }
    public String ChildCode(){
        int range = 9;
        int length = 4;
        String Child="TRID";
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) {
                i = -1;
                continue;
            }
            Child = Child + number;
        }
        return Child;
    }
    public void customDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cuatom_dialog1);
        // set the custom dialog components - text, image and button
        TextView txtAmt = dialog.findViewById(R.id.txtAmt);
        TextView txtName = dialog.findViewById(R.id.txtName);
        TextView txtBill = dialog.findViewById(R.id.txtBill);
        TextView txtopt = dialog.findViewById(R.id.txtopt);
        TextView txtdate = dialog.findViewById(R.id.txtdate);

        txtAmt.setText("\u20B9 "+dueAmount);
        txtopt.setText(optName);
        txtName.setText(Name);
        txtBill.setText("Bill no: "+Bill);
        txtdate.setText("DueDate: "+dueDate);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.btnCancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView Button = (TextView) dialog.findViewById(R.id.btnContinue);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillPay();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void GetBill() {
        customerNo = txtMobileNumber.getText().toString();
        billunit = txtAmount.getText().toString();
        RandomChildCode = ChildCode() + "A";
        String Recharge_url = Constants.Application_URL + "/users/index.php/Bill/bill_fetch";
        progressDialog = progressDialog.show(WaterBillActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Data = jObj.getString("data");
                    JSONObject jOb = new JSONObject(Data);
                    dueAmount = jOb.getString("dueamount");
                    Name = jOb.getString("customername");
                    Bill = jOb.getString("billnumber");
                    dueDate = jOb.getString("duedate");
                    refId = jOb.getString("reference_id");
                    customDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(WaterBillActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                params.put("Customernumber", customerNo);
                params.put("Optcode", OptId);
                params.put("bill_unit", billunit);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void BillPay() {
        amt = Double.parseDouble(dueAmount);
        if(bal > amt){
            restAmt = amt - bal;
            String addAmt = Double.toString(restAmt);
            Intent intent = new Intent(WaterBillActivity.this,AddMoneyActivity.class);
            intent.putExtra("addAmt",addAmt);
            startActivity(intent);
        }else {
            Remain = Double.parseDouble(myProfile.SWallet) - Double.parseDouble(dueAmount);
            String rem = Double.toString(Remain);
            String Recharge_url = Constants.Application_URL + "/users/index.php/Bill/ElectricityPayment";
            progressDialog = progressDialog.show(WaterBillActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String Status = jObj.getString("status");
                        if (Status.equalsIgnoreCase("FAIL")) {
                            Toast.makeText(WaterBillActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WaterBillActivity.this, "" + Status, Toast.LENGTH_SHORT).show();
                            String TransId = jObj.getString("transaction_id");
                            String Mobile = jObj.getString("mob_no");
                            String TransAmount = jObj.getString("amount");
                            String Datetime = jObj.getString("date");
                            Intent confirmation = new Intent(WaterBillActivity.this, PaymentHistoryActivity.class);
                            confirmation.putExtra("Yourrchid", RandomChildCode);
                            confirmation.putExtra("Trans_Id", TransId);
                            confirmation.putExtra("Mobile", Mobile);
                            confirmation.putExtra("Status", Status);
                            confirmation.putExtra("Date", Datetime);
                            confirmation.putExtra("TansAmount", TransAmount);
                            startActivity(confirmation);
                            WaterBillActivity.this.finish();
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
                    Toast.makeText(WaterBillActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", myProfile.UserLogin);
                    params.put("Customernumber", customerNo);
                    params.put("Yourrchid", RandomChildCode);
                    params.put("Amount", dueAmount);
                    params.put("Optcode", OptId);
                    params.put("reference_id", refId);
                    params.put("bill_unit",billunit);
                    params.put("wallet_bal",myProfile.SWallet);
                    params.put("remaining_bal",rem);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_transfer, null);
        dialogBuilder.setView(dialogView);
        final EditText editAmount = dialogView.findViewById(R.id.editAmount);
        dialogBuilder.setMessage("Enter Amount");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String Transfer_url= Constants.Application_URL+"/users/index.php/Recharge/add_money_s_wallet";
                progressDialog = progressDialog.show(WaterBillActivity.this, "", "Please wait...", false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Transfer_url, new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String Status = jObj.getString("status");
                            String MSG = jObj.getString("msg");
                            Toast.makeText(WaterBillActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(WaterBillActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
