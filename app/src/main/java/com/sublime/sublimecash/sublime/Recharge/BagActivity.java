package com.sublime.sublimecash.sublime.Recharge;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sublime.sublimecash.sublime.R;

import java.text.NumberFormat;
import java.util.Currency;

import Common.Session;
import Model.Profile;

public class BagActivity extends AppCompatActivity {
    TextView txtTotalAmount,txtWalletAmount,txtCommission;
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
        final int Remain = Integer.parseInt(myProfile.EWallet) + Integer.parseInt(myProfile.SWallet);
        
        txtTotalAmount.setText("Total  "+" \u20B9"+Remain);
        txtWalletAmount.setText("Wallet Amount\n"+"E-Wallet: \u20B9"+myProfile.EWallet +"\nS-Wallet \u20B9"+myProfile.SWallet );
        txtCommission.setText("Commission\n"+" \u20B9"+myProfile.commission_paid);
    }
}