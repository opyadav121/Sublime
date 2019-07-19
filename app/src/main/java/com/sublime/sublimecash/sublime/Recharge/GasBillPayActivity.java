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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.sublime.sublimecash.sublime.PaymentHistoryActivity;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Profile;
import de.hdodenhof.circleimageview.CircleImageView;

public class GasBillPayActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String optImage,optName,OptId,optType;
    TextView txtBWallet,txtEWallet,txtSWallet,btnTransfer,txtOperator,btnPay;
    EditText txtMobileNumber,txtAmount;
    String SWallet_Balance,Ewalet_Balance,Pending_Balance;
    ProgressDialog progressDialog;
    Profile myProfile;
    String RandomChildCode="";
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_bill_pay);

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
        btnPay = findViewById(R.id.btnPay);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
        txtOperator.setText(optName);
        WalletBalance();
    }

    public void WalletBalance(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String Wallet_url= Constants.Application_URL+"/users/index.php/Recharge/wallet";
        progressDialog = progressDialog.show(GasBillPayActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Wallet_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jObj = new JSONObject(response);
                    Ewalet_Balance = jObj.getString("E-Wallet");
                    SWallet_Balance = jObj.getString("S-Wallet");
                    Pending_Balance = jObj.getString("Pending_balance");
                    txtBWallet.setText(" \u20B9"+Pending_Balance);
                    txtEWallet.setText(" \u20B9"+Ewalet_Balance);
                    txtSWallet.setText(" \u20B9"+SWallet_Balance);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(GasBillPayActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
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
        dialog.setContentView(R.layout.custom_dialog);

        // set the custom dialog components - text, image and button
        TextView txtAmt = dialog.findViewById(R.id.txtAmt);
        TextView txtopt = dialog.findViewById(R.id.txtopt);
        TextView txtMobile = dialog.findViewById(R.id.txtMobile);
        String amt = txtAmount.getText().toString();
        String mobile= txtMobileNumber.getText().toString();
        txtAmt.setText("\u20B9 "+amt);
        txtopt.setText(optName);
        txtMobile.setText(mobile);
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

                Recharge();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void Recharge(){
        RandomChildCode = ChildCode() + "A";
        final Date currentTime = Calendar.getInstance().getTime();
        final int Remain = Integer.parseInt(SWallet_Balance) - Integer.parseInt(txtAmount.getText().toString());
        String Recharge_url= Constants.Application_URL+"/users/index.php/Recharge/API_recharge";
        progressDialog = progressDialog.show(GasBillPayActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Status = jObj.getString("Status");
                    Toast.makeText(GasBillPayActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                    String RandomChildCode= jObj.getString("Yourrchid");
                    String Error = jObj.getString("Errormsg");
                    String Remaining = jObj.getString("Remain");
                    String RechargeID = jObj.getString("RechargeID");
                    Intent confirmation = new Intent(GasBillPayActivity.this, PaymentHistoryActivity.class);
                    confirmation.putExtra("Yourrchid", RandomChildCode);
                    confirmation.putExtra("Errormsg",Error);
                    confirmation.putExtra("Remain",Remaining);
                    confirmation.putExtra("Status",Status);
                    confirmation.putExtra("RechargeID",RechargeID);
                    startActivity(confirmation);
                    GasBillPayActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(GasBillPayActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                params.put("Customernumber", txtMobileNumber.getText().toString());
                params.put("Yourrchid",RandomChildCode);
                params.put("Optname",txtOperator.getText().toString());
                params.put("Optcode",OptId);
                params.put("operatorname",txtOperator.getText().toString());
                params.put("wallet_bal",SWallet_Balance);
                params.put("remaining_bal",Integer.toString(Remain));
                params.put("Amount",txtAmount.getText().toString());
                params.put("date", String.valueOf(currentTime));
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
                progressDialog = progressDialog.show(GasBillPayActivity.this, "", "Please wait...", false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Transfer_url, new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String Status = jObj.getString("status");
                            String MSG = jObj.getString("msg");
                            Toast.makeText(GasBillPayActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                            WalletBalance();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(GasBillPayActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
