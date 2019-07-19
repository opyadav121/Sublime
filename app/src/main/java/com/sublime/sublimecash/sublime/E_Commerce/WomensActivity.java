package com.sublime.sublimecash.sublime.E_Commerce;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sublime.sublimecash.sublime.R;

import java.util.ArrayList;

import Model.EComMen;
import Model.EComWomen;

public class WomensActivity extends AppCompatActivity {
    GridView gridViewWomen;
    ArrayList subList=new ArrayList<>();
    TestSubject myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_womens);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();

        gridViewWomen = findViewById(R.id.gridViewWomen);
        subList.add(new EComWomen("Saree", R.drawable.saree));
        subList.add(new EComWomen("Lehenga", R.drawable.lehenga));
        subList.add(new EComWomen("Suits", R.drawable.suit));
        subList.add(new EComWomen("Western Wear", R.drawable.western));
        subList.add(new EComWomen("Sports Shoes", R.drawable.womensport));
        subList.add(new EComWomen("Casual Shoes", R.drawable.womenscausal));
        subList.add(new EComWomen("Flats", R.drawable.flats));
        subList.add(new EComWomen("Heels", R.drawable.heels));
        subList.add(new EComWomen("Boots", R.drawable.boots));

        myAdapter=new TestSubject(this, R.layout.ecomgrid_womens, subList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        gridViewWomen.setAdapter(myAdapter);
        gridViewWomen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WomensActivity.this, WomensActivity.class);
                startActivity(intent);
                finish();
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
            convertView = inflater.inflate(R.layout.ecomgrid_mens, null);
            TextView textView = convertView.findViewById(R.id.testName);
            ImageView imageView = convertView.findViewById(R.id.image);
            EComWomen tempSubject = (EComWomen) subList.get(position);
            textView.setText(tempSubject.getsubName());
            imageView.setImageResource(tempSubject.getSubImage());
            return convertView;
        }
    }
}
