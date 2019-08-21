package com.sublime.sublimecash.sublime.Recharge;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sublime.sublimecash.sublime.R;

import java.text.NumberFormat;
import java.util.Currency;

import Common.Session;
import Model.Profile;

public class BagActivity extends AppCompatActivity {
    TextView txtTotalAmount,txtWalletAmount,txtCommission,details,detailsCommission;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Sublime Cash ");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        txtWalletAmount  = findViewById(R.id.txtWalletAmount);
        txtCommission = findViewById(R.id.txtCommission);
        details = findViewById(R.id.details);
        detailsCommission = findViewById(R.id.detailsCommission);
        detailsCommission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BagActivity.this,BagActivity.class);
                startActivity(intent);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BagActivity.this,SWalletHistoryActivity.class);
                startActivity(intent);
            }
        });

        final Double Remain = Double.parseDouble(myProfile.EWallet) + Double.parseDouble(myProfile.SWallet);
        txtTotalAmount.setText("Total  "+" \u20B9"+Remain);
        txtWalletAmount.setText("Wallet Amount\n"+"E-Wallet: \u20B9"+myProfile.EWallet +"\nS-Wallet \u20B9"+myProfile.SWallet );
        txtCommission.setText("Commission\n"+" \u20B9"+myProfile.commission_paid);
    }
}