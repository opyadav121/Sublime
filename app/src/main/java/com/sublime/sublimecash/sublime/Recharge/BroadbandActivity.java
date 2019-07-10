package com.sublime.sublimecash.sublime.Recharge;

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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.LandlineOperater;
import Model.Oparater;
import Model.Profile;

public class BroadbandActivity extends AppCompatActivity {
    GridView gridViewLandline;
    ArrayList optList=new ArrayList<>();
    AdapterBroadband adapterBroadband;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadband);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Operator");
        actionBar.show();

        gridViewLandline = findViewById(R.id.gridViewLandline);
        optList.add(new LandlineOperater("AIRTEL", R.drawable.airtelicon));
        optList.add(new LandlineOperater("BSNL", R.drawable.bsnl));
        optList.add(new LandlineOperater("ACT Broadband", R.drawable.act));
        optList.add(new LandlineOperater("ANI Network", R.drawable.ani));
        optList.add(new LandlineOperater("Allince ", R.drawable.alliance));
        optList.add(new LandlineOperater("Asianet Broadband", R.drawable.asianet));
        optList.add(new LandlineOperater("Hathway Broadband", R.drawable.hathway));
        optList.add(new LandlineOperater("MTNL Delhi", R.drawable.mtnl));
        optList.add(new LandlineOperater("Tikona ", R.drawable.tikona));
        adapterBroadband=new AdapterBroadband(this, R.layout.gridview_gift, optList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        gridViewLandline.setAdapter(adapterBroadband);

        gridViewLandline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  String operator = (String) optList.get(position);
                Intent intent = new Intent(BroadbandActivity.this,PayLandlineBillActivity.class);
                // intent.putExtra("Operator", operator);
                startActivity(intent);
                // LandlineActivity.this.finish();
            }
        });

    }
    public class AdapterBroadband extends ArrayAdapter {

        ArrayList optList = new ArrayList<>();

        public AdapterBroadband(Context context, int textViewResourceId, ArrayList objects) {
            super(context, textViewResourceId, objects);
            optList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_operater, null);
            TextView textView = convertView.findViewById(R.id.testName);
            ImageView imageView = convertView.findViewById(R.id.image);
            LandlineOperater tempOpt = (LandlineOperater) optList.get(position);
            textView.setText(tempOpt.getoptName());
            imageView.setImageResource(tempOpt.getoptImage());
            return convertView;
        }
    }
}
