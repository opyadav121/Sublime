package com.sublime.sublimecash.sublime.Recharge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import Model.Gift;

public class GiftActivity extends AppCompatActivity {

    GridView gridViewGift;
    List<Gift> giftList=new ArrayList<>();
    AdapterGift adapterGift;
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
        gridViewGift = findViewById(R.id.gridViewGift);
        adapterGift= new AdapterGift(this, R.layout.gridview_gift, giftList) ;
        gridViewGift.setAdapter(adapterGift);
        gridViewGift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GiftActivity.this,GiftBuyActivity.class);
                startActivity(intent);
            }
        });
        Operators();

    }
    public void Operators() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Voucher/VouchersList";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Gift opt = new Gift();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        opt.gift_card_name = jObj.getString("gift_card_name");
                        opt.sp_key = jObj.getString("sp_key");
                        opt.imgName = jObj.getString("img");
                        String Status = jObj.getString("status");
                        if (Status.equalsIgnoreCase("1")){
                            giftList.add(opt);
                        }else {}
                    }
                    adapterGift.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GiftActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

        gridViewGift.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gift optname  = adapterGift.getItem(position);
                String optImageName = optname.imgName;
                String optName = optname.gift_card_name;
                String optId = optname.sp_key;
                Intent intent = new Intent(GiftActivity.this, GiftBuyActivity.class);
                intent.putExtra("Image", optImageName);
                intent.putExtra("optName", optName);
                intent.putExtra("OptId", optId);
                startActivity(intent);
            }
        });
    }
    class AdapterGift extends ArrayAdapter {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterGift(Context context, int resource, List<Gift> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return giftList.size();
        }

        @Nullable
        @Override
        public Gift getItem(int position) {
            return  giftList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.gridview_gift, null);
                    holder = new ViewHolder();
                    holder.image = convertView.findViewById(R.id.image);
                    holder.txtOperatorName = convertView.findViewById(R.id.txtOperatorName);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Gift opt = getItem(position);
                holder.txtOperatorName.setText(opt.gift_card_name);
                String url1 = "http://www.sublimecash.com/users/opt/gift/" + opt.imgName;
                Picasso.with(getApplicationContext()).load(url1).into(holder.image);
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                Toast.makeText(getApplicationContext(),"Could not Load Data", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }
    private class ViewHolder
    {
        TextView txtOperatorName;
        ImageView image;
    }
}
