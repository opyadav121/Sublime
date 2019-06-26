package com.example.omprakash.sublime.Recharge;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.example.omprakash.sublime.R;

public class PayLandlineBillActivity extends AppCompatActivity {
    EditText txtOperator,txtOptNumber,txtAmount;
    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_landline_bill);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Pay your Bill");
        actionBar.show();

        btnPay = findViewById(R.id.btnPay);
        txtOperator = findViewById(R.id.txtOperator);
        txtOptNumber = findViewById(R.id.txtOptNumber);
        txtAmount = findViewById(R.id.txtAmount);
       // Intent intent = getIntent();
      // String oprerator = intent.getStringExtra("Operator");
      // txtOperator.setText(oprerator);
    }
}
