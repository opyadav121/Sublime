package com.sublime.sublimecash.sublime;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class PaymentHistoryActivity extends AppCompatActivity {

    TextView txtTransID,txtError,txtRemaining,txtYourChild,txtRechargeID,txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Payment History");
        actionBar.show();


        txtTransID = findViewById(R.id.txtTransID);
        txtError = findViewById(R.id.txtError);
        txtRemaining = findViewById(R.id.txtRemaining);
        txtYourChild = findViewById(R.id.txtYourChild);
        txtRechargeID = findViewById(R.id.txtRechargeID);
        txtDate = findViewById(R.id.txtDate);

        Intent intent = getIntent();
        String TransID = intent.getStringExtra("Yourrchid");
        String Trans_Amt =  intent.getStringExtra("TansAmount");
        String Mobile = intent.getStringExtra("Mobile");
        String Status =  intent.getStringExtra("Status");
        String RechargeID =intent.getStringExtra("Trans_Id");
        String Date =intent.getStringExtra("Date");
        txtTransID.setText(TransID);
        txtError.setText(Mobile);
        txtRemaining.setText(Trans_Amt);
        txtYourChild.setText(Status);
        txtRechargeID.setText(RechargeID);
        txtDate.setText(Date);
    }
}
