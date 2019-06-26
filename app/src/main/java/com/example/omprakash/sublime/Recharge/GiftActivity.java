package com.example.omprakash.sublime.Recharge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omprakash.sublime.R;

import java.util.ArrayList;

import Model.Gift;

public class GiftActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList subList=new ArrayList<>();
    TestSubject myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Gift Card");
        actionBar.show();

        gridView = findViewById(R.id.gridView);
        subList.add(new Gift("Amazon eGift", R.drawable.aa1));
        subList.add(new Gift("Bata eGift", R.drawable.aa2));
        subList.add(new Gift("Croma eGift", R.drawable.aa3));
        subList.add(new Gift("Flipkart eGift", R.drawable.aa4));
        subList.add(new Gift("Himalaya eGift", R.drawable.aa5));
        subList.add(new Gift("Levis eGift", R.drawable.aa6));
        subList.add(new Gift("Peter England eGift", R.drawable.aa7));
        subList.add(new Gift("Big Bazaar", R.drawable.bigbazar));
        subList.add(new Gift("BigBasket ", R.drawable.bigbasketgift));
        subList.add(new Gift("CaratLane eGift", R.drawable.caratelane));
        subList.add(new Gift("Bookmyshow", R.drawable.bookmyshowgift));
        subList.add(new Gift("Arrow eGift", R.drawable.arrows));
        subList.add(new Gift("Cleartrip eGift ", R.drawable.creargift));
        subList.add(new Gift("Allen Solly eGift", R.drawable.allenesolly));
        subList.add(new Gift("Fastrack eGift", R.drawable.fastrack));
        subList.add(new Gift("Gant eGift ", R.drawable.gant));
        subList.add(new Gift("Helios eGift ", R.drawable.helios));
        subList.add(new Gift("Hidesign eGift", R.drawable.hidesign));
        subList.add(new Gift("IZOD eGift", R.drawable.izod));
        subList.add(new Gift("Joyalukkas Diamond ", R.drawable.joyalukkas));
        subList.add(new Gift("Lakme Salon", R.drawable.lakeme));
        subList.add(new Gift("MakeMyTrip eGift ", R.drawable.makemytripgift));
        subList.add(new Gift("MakeMyTrip Holiday", R.drawable.mmtholy));
        subList.add(new Gift("MakeMyTrip Hotel", R.drawable.mmthotel));
        subList.add(new Gift("Myntra eGift ", R.drawable.myntragift));
        subList.add(new Gift("Nautica eGift ", R.drawable.nautica));
        subList.add(new Gift("Nike eGift", R.drawable.nike));
        subList.add(new Gift("Nykaa eGift", R.drawable.nykaagift));
        subList.add(new Gift("P N Rao eGift", R.drawable.pnrao));
        subList.add(new Gift("Pantaloons eGift", R.drawable.pantaloons));
        subList.add(new Gift("Pavers England eGift", R.drawable.pavers));
        subList.add(new Gift("Planet Fashion ", R.drawable.planet));
        subList.add(new Gift("Prestige Smart Kitchen", R.drawable.prestige));
        subList.add(new Gift("PVR Cinemas eGift ", R.drawable.pvr));
        subList.add(new Gift("Shoppers Stop eGift", R.drawable.shopersstop));
        subList.add(new Gift("Thomas Cook eGift", R.drawable.thomascook));
        subList.add(new Gift("Titan eGift ", R.drawable.titan));
        subList.add(new Gift("US Polo Assn", R.drawable.uspolo));
        subList.add(new Gift("Van Heusen eGift", R.drawable.vanhussen));
        subList.add(new Gift("VLCC eGift ", R.drawable.vlcc));
        subList.add(new Gift("Westside eGift", R.drawable.westiside));
        subList.add(new Gift("Yatra eGift ", R.drawable.yatra));
        subList.add(new Gift("Urban Ladder eGift", R.drawable.urbanlader));
        subList.add(new Gift("Chumbak eGift", R.drawable.chumbak));
        subList.add(new Gift("Fabindia eGift", R.drawable.fabindiagift));
        subList.add(new Gift("Bluestone eGift", R.drawable.bluestone));
        subList.add(new Gift("Pepperfry eGift", R.drawable.paperfry));
        subList.add(new Gift("The Raymond Shop", R.drawable.raymond));
        subList.add(new Gift("Hypercity eGift", R.drawable.hypercity));
        subList.add(new Gift("Uber eGift ", R.drawable.ubereats));

        myAdapter=new TestSubject(this, R.layout.gridview_gift, subList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(GiftActivity.this,GiftBuyActivity.class);
                startActivity(intent);
            }
        });
    }


    public class TestSubject extends ArrayAdapter {

        ArrayList subList = new ArrayList<>();

        public TestSubject(Context context, int textViewResourceId, ArrayList objects) {
            super(context, textViewResourceId, objects);
            subList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_gift, null);
            TextView textView = convertView.findViewById(R.id.testName);
            ImageView imageView = convertView.findViewById(R.id.image);
            Gift tempSubject = (Gift) subList.get(position);
            textView.setText(tempSubject.getsubName());
            imageView.setImageResource(tempSubject.getSubImage());
            return convertView;
        }
    }
}
