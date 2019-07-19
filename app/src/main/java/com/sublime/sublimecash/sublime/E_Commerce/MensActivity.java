package com.sublime.sublimecash.sublime.E_Commerce;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.sublime.sublimecash.sublime.Recharge.GiftActivity;
import com.sublime.sublimecash.sublime.Recharge.GiftBuyActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import Model.EComMen;
import Model.Gift;

public class MensActivity extends AppCompatActivity {
    GridView gridViewMen;
    ArrayList subList=new ArrayList<>();
    TestSubject myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mens);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();

        gridViewMen = findViewById(R.id.gridViewMen);
        subList.add(new EComMen("Polo & T-Shirt", R.drawable.polot));
        subList.add(new EComMen("Ethnic Wear", R.drawable.ethnic));
        subList.add(new EComMen("Casual Shirt", R.drawable.casualshirt));
        subList.add(new EComMen("Formal Shirt", R.drawable.formalshirt));
        subList.add(new EComMen("Suits & Blazers", R.drawable.blazers));
        subList.add(new EComMen("Jeans", R.drawable.jeans));
        subList.add(new EComMen("Formal Trousers", R.drawable.formaltrousers));
        subList.add(new EComMen("Casual Trousers", R.drawable.casualtrousers));
      //  subList.add(new EComMen("Shorts", R.drawable.abcd));
        subList.add(new EComMen("Sports Shoes", R.drawable.sportshoes));
        subList.add(new EComMen("Casual Shoes", R.drawable.casualshoes));
        subList.add(new EComMen("Formal Shoes", R.drawable.formalshoes));
        subList.add(new EComMen("Sneakers", R.drawable.sneakers));
        subList.add(new EComMen("Sleepers & Sandals", R.drawable.sandals));
        myAdapter=new TestSubject(this, R.layout.ecomgrid_mens, subList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        gridViewMen.setAdapter(myAdapter);

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
            EComMen tempSubject = (EComMen) subList.get(position);
            textView.setText(tempSubject.getsubName());
            imageView.setImageResource(tempSubject.getSubImage());
            return convertView;
        }
    }
}
