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
import android.widget.RadioButton;
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
import com.sublime.sublimecash.sublime.AddRemainingActivity;
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

public class DTHRechargeActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String optImage,optName,OptId,optType;
    CircleImageView imageOperator;
    RadioGroup radioGroup;
    TextView txtBWallet,txtEWallet,txtSWallet,btnTransfer,txtOperator,btnPay,txtsbWallet;
    EditText txtMobileNumber,txtAmount;
    Button browsePlan,Offer;
    ProgressDialog progressDialog;
    Profile myProfile;
    String RandomChildCode="";
    final Context context = this;
    Double Remain,bal,amt,restAmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthrecharge);

        myProfile = Session.GetProfile(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        optImage = intent.getStringExtra("Image");
        optName = intent.getStringExtra("optName");
        OptId = intent.getStringExtra("OptId");
        optType = intent.getStringExtra("optType");

        imageOperator = findViewById(R.id.imageOperator);
        radioGroup = findViewById(R.id.radioGroup);
        txtBWallet = findViewById(R.id.txtBWallet);
        txtEWallet = findViewById(R.id.txtEWallet);
        txtSWallet = findViewById(R.id.txtSWallet);
        btnTransfer = findViewById(R.id.btnTransfer);
        txtOperator = findViewById(R.id.txtOperator);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtAmount = findViewById(R.id.txtAmount);
        browsePlan = findViewById(R.id.browsePlan);
        Offer = findViewById(R.id.Offer);
        txtBWallet.setText(" \u20B9"+myProfile.PendingWallet);
        txtEWallet.setText(" \u20B9"+myProfile.EWallet);
        txtSWallet.setText(" \u20B9"+myProfile.SWallet);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });
        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myProfile = Session.GetProfile(getApplicationContext());
                bal = Double.parseDouble(myProfile.SWallet);
                amt = Double.parseDouble(txtAmount.getText().toString());
                if(bal < amt){
                    restAmt = amt - bal;
                    String addAmt = Double.toString(restAmt);
                    Intent intent = new Intent(DTHRechargeActivity.this, AddRemainingActivity.class);
                    intent.putExtra("addAmt",addAmt);
                    startActivity(intent);

                }else {
                    customDialog();
                }
            }
        });

        String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" +optImage;
        Picasso.with(getApplicationContext()).load(url1).into(imageOperator);
        txtOperator.setText(optName);
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
        dialog.setContentView(R.layout.confirm_dialog);

        // set the custom dialog components - text, image and button
        TextView txtAmt = dialog.findViewById(R.id.txtAmt);
        TextView txtopt = dialog.findViewById(R.id.txtopt);
        TextView txtMobile = dialog.findViewById(R.id.txtMobile);
        String amt = txtAmount.getText().toString();
        String mobile= txtMobileNumber.getText().toString();
        txtAmt.setText("\u20B9 "+amt);
        txtopt.setText(optName);
        txtMobile.setText(mobile);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" +optImage;
        Picasso.with(getApplicationContext()).load(url1).into(image);
        //  image.setImageResource(R.drawable.airtelicon);
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
    public void Recharge() {
        myProfile = Session.GetProfile(getApplicationContext());
        String Mobile = txtMobileNumber.getText().toString();
        String Amount = txtAmount.getText().toString();
        if (Mobile.equals("")) {
            txtMobileNumber.setError("Enter Customer no.");
        } else if (Amount.equals("")) {
            txtAmount.setError("Enter Amount.");
        } else {
            RandomChildCode = ChildCode() + "A";
            final Date currentTime = Calendar.getInstance().getTime();
            Remain = Double.parseDouble(myProfile.SWallet) - Double.parseDouble(txtAmount.getText().toString());
            String restAmount = Double.toString(Remain);
            String Recharge_url = Constants.Application_URL + "/users/index.php/Recharge/API_recharge";
            progressDialog = progressDialog.show(DTHRechargeActivity.this, "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        if (response.length()==1) {
                            Toast.makeText(DTHRechargeActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                        } else{
                            String Status = jObj.getString("status");
                            Toast.makeText(DTHRechargeActivity.this, "" + Status, Toast.LENGTH_SHORT).show();
                            String TransId = jObj.getString("ipay_id");
                            String Mobile = jObj.getString("account_no");
                            String TransAmount = jObj.getString("trans_amt");
                            String Datetime = jObj.getString("datetime");
                            Intent confirmation = new Intent(DTHRechargeActivity.this, PaymentHistoryActivity.class);
                            confirmation.putExtra("Yourrchid", RandomChildCode);
                            confirmation.putExtra("Trans_Id", TransId);
                            confirmation.putExtra("Mobile", Mobile);
                            confirmation.putExtra("Status", Status);
                            confirmation.putExtra("Date", Datetime);
                            confirmation.putExtra("TansAmount", TransAmount);
                            startActivity(confirmation);
                            DTHRechargeActivity.this.finish();
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
                    Toast.makeText(DTHRechargeActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", myProfile.UserLogin);
                    params.put("Customernumber", Mobile);
                    params.put("Yourrchid", RandomChildCode);
                    params.put("Optname", optName);
                    params.put("Optcode", OptId);
                    params.put("operatorname", optName);
                    params.put("wallet_bal", myProfile.SWallet);
                    params.put("remaining_bal", restAmount);
                    params.put("Amount", Amount);
                    params.put("amount",Amount);
                    params.put("date", String.valueOf(currentTime));
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
                progressDialog = progressDialog.show(DTHRechargeActivity.this, "", "Please wait...", false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Transfer_url, new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String Status = jObj.getString("status");
                            String MSG = jObj.getString("msg");
                            Toast.makeText(DTHRechargeActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DTHRechargeActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
