package com.example.omprakash.sublime.Recharge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.omprakash.sublime.HomeActivity;
import com.example.omprakash.sublime.LoginActivity;
import com.example.omprakash.sublime.R;

import Common.Session;

public class GiftBuyActivity extends AppCompatActivity {
    TextView txtTC;
    Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_buy);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Gift Card");
        actionBar.show();
        btnBuy= findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtTC = findViewById(R.id.txtTC);
        txtTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(GiftBuyActivity.this);
                builder.setTitle("Terms and Conditions");
                builder.setMessage("1.Amazon.in E-Gift Cards (“EGCs”) are issued by Qwikcilver Solutions Private Limited (“Qwikcilver”).\n" + "EGCs may be used only for the purchase of eligible products on Amazon.in.\n" + "2.This E- Gift Card can be redeemed only once.\n" + "3.EGC balances must be used within 1 year of the date of purchase.\n" + "EGCs cannot be transferred for value or redeemed for cash.\n" + "4.Qwikcilver, Amazon Seller Services Private Limited (“Amazon”) or their affiliates are not responsible if an EGC is lost, stolen, destroyed or used without permission.\n" + "5.To redeem your EGC, visit www.amazon.in/addgiftcard.\n" + "6.For complete terms and conditions, see www.amazon.in/giftcardtnc.\n" + "7.E-Gift Cards are normally delivered instantly. But sometimes due to system issues, the delivery can be delayed up-to 24 hours.\n" + "8.No returns and no refunds on gift cards, E- gift cards and gift vouchers shipped by woohoo.in. Please check the refund policy at http://www.woohoo.in/faq for further details.\n" + "10.To login & view your gift card transaction statement, please click here and go to the tab Amazon.in");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog Alert = builder.create();
                Alert.show();
            }
        });
    }
}
