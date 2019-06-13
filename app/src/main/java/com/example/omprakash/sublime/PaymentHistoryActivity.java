package com.example.omprakash.sublime;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class PaymentHistoryActivity extends AppCompatActivity {

    TextView txtTransID,txtError,txtRemaining,txtYourChild,txtRechargeID;

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

        Intent intent = getIntent();
        String TransID = intent.getStringExtra("Yourrchid");
        String Error =  intent.getStringExtra("Errormsg");
        String Remaining = intent.getStringExtra("Remain");
        String Status =  intent.getStringExtra("Status");
        String RechargeID =intent.getStringExtra("RechargeID");

        txtTransID.setText(TransID);
        txtError.setText(Error);
        txtRemaining.setText(Remaining);
        txtYourChild.setText(Status);
        txtRechargeID.setText(RechargeID);
    }
}
