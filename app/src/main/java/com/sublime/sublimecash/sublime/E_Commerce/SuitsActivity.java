package com.sublime.sublimecash.sublime.E_Commerce;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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

import java.util.ArrayList;
import java.util.List;

import Common.Session;
import Model.Lehenga;
import Model.Profile;
import Model.Suit;

public class SuitsActivity extends AppCompatActivity {
    GridView gridViewSuits;
    List<Suit> suitsList=new ArrayList<>();
    AdapterSuit adapterSuit;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suits);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();
        myProfile = Session.GetProfile(getApplicationContext());
        gridViewSuits = findViewById(R.id.gridViewSuits);
        adapterSuit=new AdapterSuit(SuitsActivity.this, R.layout.gridvewsarees, suitsList);
        gridViewSuits.setAdapter(adapterSuit);
        getSuits();

    }

    public void getSuits() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://sublimecash.com/ws2/index.php/front_controller/show_all/Women/Clothing/Ethnic%20wear/Suits";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jOb = new JSONObject(response);
                    String Res = jOb.getString("res");
                    JSONArray jsonArray = new JSONArray(Res);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Suit opt = new Suit();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        opt.prod_name = jObj.getString("prod_name");
                        opt.prod_id = jObj.getString("id");
                        String imgName = jObj.getString("prod_img");
                        String[] arrSplit = imgName.split(",");
                        for (int j=0; j < 1; j++) {
                            opt.prod_img = arrSplit[j];
                        }
                        opt.cat_type = jObj.getString("cat_type");
                        opt.description = jObj.getString("description");
                        opt.stock = jObj.getString("stock");
                        opt.sub_cat = jObj.getString("sub_cat");
                        opt.prod_cat = jObj.getString("prod_cat");
                        opt.include_content = jObj.getString("include_content");
                        opt.vendor_name = jObj.getString("vendor_name");
                        opt.vendor_email = jObj.getString("vendor_email");
                        opt.selling_price = jObj.getString("selling_price");
                        opt.mrp = jObj.getString("mrp");
                        suitsList.add(opt);
                    }
                    adapterSuit.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuitsActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

        gridViewSuits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Suit optname  = (Suit) adapterSuit.getItem(position);
                String prodID = optname.prod_id;
                String Name = optname.prod_name;
                String imageName = optname.prod_img;
                String SellingPrice = optname.selling_price;
                String mrp = optname.mrp;
                String des = optname.description;
                Intent intent = new Intent(SuitsActivity.this, ProductDetailsActivity.class);
                // intent.putExtra("Image", optImageName);
                intent.putExtra("Name", Name);
                intent.putExtra("Selling", SellingPrice);
                intent.putExtra("MRP", mrp);
                intent.putExtra("Prod_id", prodID);
                intent.putExtra("imgName", imageName);
                intent.putExtra("descrip",des);
                startActivity(intent);
            }
        });
    }
    class AdapterSuit extends ArrayAdapter {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterSuit(Context context, int resource, List<Suit> objects) {
            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return suitsList.size();
        }
        @Nullable
        @Override
        public Suit getItem(int position) {
            return suitsList.get(position);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.gridvewsarees, null);
                    holder = new ViewHolder();
                    holder.image = convertView.findViewById(R.id.image);
                    holder.prodName = convertView.findViewById(R.id.prodName);
                    holder.sellingPrice = convertView.findViewById(R.id.sellingPrice);
                    holder.printPrice = convertView.findViewById(R.id.printPrice);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Suit opt = getItem(position);
                holder.prodName.setText(opt.prod_name);
                holder.sellingPrice.setText("\u20B9"+opt.selling_price);
                holder.printPrice.setText("\u20B9"+opt.mrp);
                holder.printPrice.setPaintFlags(holder.printPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                String url1 = "http://sublimecash.com/upload/product/" + opt.prod_img;
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
    } private class ViewHolder
    {
        TextView prodName,sellingPrice,printPrice;
        ImageView image;
    }
}
