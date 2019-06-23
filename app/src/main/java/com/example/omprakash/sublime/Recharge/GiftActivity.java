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
        subList.add(new Gift("Gift_1", R.drawable.aa1));
        subList.add(new Gift("Gift_2", R.drawable.aa2));
        subList.add(new Gift("Gift_3", R.drawable.aa3));
        subList.add(new Gift("Gift_4", R.drawable.aa4));
        subList.add(new Gift("Gift_5", R.drawable.aa5));
        subList.add(new Gift("Gift_6", R.drawable.aa6));
        subList.add(new Gift("Gift_7", R.drawable.aa7));
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
