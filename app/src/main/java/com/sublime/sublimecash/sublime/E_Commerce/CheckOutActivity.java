package com.sublime.sublimecash.sublime.E_Commerce;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sublime.sublimecash.sublime.CorrespondanceAddressActivity;
import com.sublime.sublimecash.sublime.EditProfileActivity;
import com.sublime.sublimecash.sublime.R;

import Common.Session;
import Model.Profile;

public class CheckOutActivity extends AppCompatActivity {
    TextView btnChangeAddress,deliveryAddress,ItemName,returnPolicy,txtCount,txtMrp,
            txtDiscount,txtTotal,txtGSTN;
    ImageView imgItem,btnAdd,btnMinus;
    Button btnPay;
    String name,Mrp,sellPrice,img,_stringVal;
    Profile myProfile;
    int Discount;
    Integer i = 1;
    String totalMRP,totalSP,discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        Mrp = intent.getStringExtra("MRP");
        sellPrice = intent.getStringExtra("Selling");
        img = intent.getStringExtra("imgName");
        _stringVal = intent.getStringExtra("Qnty");
        int mrp = Integer.parseInt(Mrp);
        int SP = Integer.parseInt(sellPrice);


        btnPay= findViewById(R.id.btnPay);
        btnChangeAddress= findViewById(R.id.btnChangeAddress);
        deliveryAddress= findViewById(R.id.deliveryAddress);
        ItemName= findViewById(R.id.ItemName);
        returnPolicy= findViewById(R.id.returnPolicy);
        txtCount= findViewById(R.id.txtCount);
        txtMrp= findViewById(R.id.txtMrp);
        txtDiscount= findViewById(R.id.txtDiscount);
        txtTotal= findViewById(R.id.txtTotal);
        txtGSTN= findViewById(R.id.txtGSTN);
        imgItem= findViewById(R.id.imgItem);
        btnAdd= findViewById(R.id.btnAdd);
        btnMinus= findViewById(R.id.btnMinus);
        txtGSTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CheckOutActivity.this, GSTINActivity.class);
                startActivity(intent1);
            }
        });
        btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CheckOutActivity.this, CorrespondanceAddressActivity.class);
                startActivity(intent1);
            }
        });

        deliveryAddress.setText(myProfile.UserName+"\n"+myProfile.Address+"\nDist: "+myProfile.district+",  "+myProfile.State+"\nContact no.: "+myProfile.MobileNumber);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //String _stringVal;
                Log.d("src", "Increasing value...");
                i = i + 1;
                _stringVal = String.valueOf(i);
                txtCount.setText(_stringVal);

               int TotalMrp = mrp*i;
               int Total_SP = SP*i;
               Discount = TotalMrp - Total_SP;
               totalMRP = Integer.toString(TotalMrp);
                totalSP = Integer.toString(Total_SP);
                discount = Integer.toString(Discount);
                txtMrp.setText(" \u20B9"+totalMRP);
                txtDiscount.setText(" \u20B9"+discount);
                txtTotal.setText(" \u20B9"+totalSP);

            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String _stringVal;
                Log.d("src", "Decreasing value...");
                if (i > 0) {
                    i = i - 1;
                    _stringVal = String.valueOf(i);
                    txtCount.setText(_stringVal);

                    int TotalMrp = mrp*i;
                    int Total_SP = SP*i;
                    Discount = TotalMrp - Total_SP;
                    totalMRP = Integer.toString(TotalMrp);
                    totalSP = Integer.toString(Total_SP);
                    discount = Integer.toString(Discount);
                    txtMrp.setText(" \u20B9"+totalMRP);
                    txtDiscount.setText(" \u20B9"+discount);
                    txtTotal.setText(" \u20B9"+totalSP);

                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        //Discount = Integer.valueOf(Mrp) - Integer.valueOf(sellPrice);

        String dis = Integer.toString(Discount);
        ItemName.setText(name);
        txtMrp.setText(" \u20B9"+Mrp);
        txtDiscount.setText(" \u20B9"+dis);
        txtTotal.setText(" \u20B9"+sellPrice);

        String url1 = "http://sublimecash.com/upload/product/" + img;
        Picasso.with(getApplicationContext()).load(url1).into(imgItem);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, PaymentOptionActivity.class);
                // intent.putExtra("Image", optImageName);
                intent.putExtra("Price", totalSP);
                startActivity(intent);
            }
        });
    }
}
