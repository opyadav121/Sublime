package com.sublime.sublimecash.sublime.Recharge;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    TextView txtEWallet,txtSWallet,btnTransfer,txtOperator,btnPay;
    EditText txtMobileNumber,txtAmount;
    Button browsePlan,Offer;
    String SWallet_Balance,Ewalet_Balance,OperatorCode;
    ProgressDialog progressDialog;
    Profile myProfile;
    String RandomChildCode="";
    final Context context = this;

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
        txtEWallet = findViewById(R.id.txtEWallet);
        txtSWallet = findViewById(R.id.txtSWallet);
        btnTransfer = findViewById(R.id.btnTransfer);
        txtOperator = findViewById(R.id.txtOperator);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtAmount = findViewById(R.id.txtAmount);
        browsePlan = findViewById(R.id.browsePlan);
        Offer = findViewById(R.id.Offer);
        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });

        String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" +optImage;
        Picasso.with(getApplicationContext()).load(url1).into(imageOperator);
        txtOperator.setText(optName);
        WalletBalance();
    }

    public void WalletBalance(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String Wallet_url= Constants.Application_URL+"/users/index.php/Recharge/wallet";
        progressDialog = progressDialog.show(DTHRechargeActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Wallet_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONObject jObj = new JSONObject(response);
                    Ewalet_Balance = jObj.getString("E-Wallet");
                    SWallet_Balance = jObj.getString("S-Wallet");

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
                Toast.makeText(DTHRechargeActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
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
    public void Recharge(){
        RandomChildCode = ChildCode() + "A";
        final Date currentTime = Calendar.getInstance().getTime();
        final int Remain = Integer.parseInt(SWallet_Balance) - Integer.parseInt(txtAmount.getText().toString());
        String Recharge_url= Constants.Application_URL+"/users/index.php/Recharge/API_recharge";
        progressDialog = progressDialog.show(DTHRechargeActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Status = jObj.getString("Status");
                    Toast.makeText(DTHRechargeActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                    String RandomChildCode= jObj.getString("Yourrchid");
                    String Error = jObj.getString("Errormsg");
                    String Remaining = jObj.getString("Remain");
                    String RechargeID = jObj.getString("RechargeID");
                    Intent confirmation = new Intent(DTHRechargeActivity.this, PaymentHistoryActivity.class);
                    confirmation.putExtra("Yourrchid", RandomChildCode);
                    confirmation.putExtra("Errormsg",Error);
                    confirmation.putExtra("Remain",Remaining);
                    confirmation.putExtra("Status",Status);
                    confirmation.putExtra("RechargeID",RechargeID);
                    startActivity(confirmation);
                    DTHRechargeActivity.this.finish();
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
}
