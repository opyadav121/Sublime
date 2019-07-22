package com.sublime.sublimecash.sublime.E_Commerce;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.sublime.sublimecash.sublime.DashboardActivity;
import com.sublime.sublimecash.sublime.HomeActivity;
import com.sublime.sublimecash.sublime.R;
import com.sublime.sublimecash.sublime.Recharge.PrepaidActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Model.Oparater;
import Model.Profile;
import Model.Sarees;
import Model.SliderImage;
import me.relex.circleindicator.CircleIndicator;

public class ProductDetailsActivity extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPager = 0;
    ArrayList<Integer> picArray =new ArrayList<Integer>();
   // ArrayList<SliderImage> picArray =new ArrayList<SliderImage>();
   // AdapterItem adapterItem;
   // private static final Integer [] pic = {R.drawable.a, R.drawable.aa,R.drawable.aaa};
  //  SliderImage [] pic;
    Profile myProfile;
    TextView prodName,sellingPrice,printPrice,txtDescription,txtType,txtBrand,txtMaterial,txtWeight,txtColor,txtDetails;
    Button btnBuy;
    String name, prod_id, sellPrice,Mrp;
    String type,brandName,material,waigth,color,details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("SublimeCash");
        actionBar.show();

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        Mrp = intent.getStringExtra("MRP");
        sellPrice = intent.getStringExtra("Selling");
        prod_id = intent.getStringExtra("Prod_id");
        getDetails();
        btnBuy = findViewById(R.id.btnBuy);
        prodName = findViewById(R.id.prodName);
        sellingPrice = findViewById(R.id.sellingPrice);
        printPrice = findViewById(R.id.printPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtType = findViewById(R.id.txtType);
        txtBrand = findViewById(R.id.txtBrand);
        txtMaterial = findViewById(R.id.txtMaterial);
        txtWeight = findViewById(R.id.txtWeight);
        txtColor = findViewById(R.id.txtColor);
        txtDetails = findViewById(R.id.txtDetails);

        btnBuy.setText("Buy for \u20B9" +sellPrice);
        prodName.setText(name);
        sellingPrice.setText(" \u20B9"+sellPrice);
        printPrice.setText(" \u20B9"+Mrp);
        printPrice.setPaintFlags(printPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


    }
 /*   public void Slider1(){

        for (int i=0; i<pic.length; i++)
            picArray.add(pic[i]);
        mPager=findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(picArray,this));
        CircleIndicator indicator=findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final Handler handler=new Handler();
        final Runnable update=new Runnable() {
            @Override
            public void run() {
                if (currentPager == pic.length)
                {
                    currentPager = 0;
                }else {
                    mPager.setCurrentItem(currentPager++, true);
                }
            }
        };
        Timer swipeTime=new Timer();
        swipeTime.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },10000,10000);
    }

    public class MyAdapter extends PagerAdapter
    {
        private ArrayList<Integer> images;
        private LayoutInflater inflater;
        private Context context;

        public MyAdapter(ArrayList<Integer> images, Context context) {
            this.images = images;
            inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
        {
            return view.equals(object);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
        {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position)
        {
            View v=inflater.inflate(R.layout.slide,container,false);
            ImageView myImage=v.findViewById(R.id.image);
            myImage.setImageResource(images.get(position));
            container.addView(v,0);
            return v;
        }
    }   */

     public void getDetails(){
         RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
         String url = "http://www.sublimecash.com/ws2/index.php/welcome/product_detail/"+prod_id;
         StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()  {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONObject jOb = new JSONObject(response);
                     String Res = jOb.getString("res");
                     String img = jOb.getString("img");
                     String Res2 = jOb.getString("res2");
                    // JSONObject jObj = new JSONObject(Res);
                     JSONObject jObj = new JSONObject(img);
                     String imgName = jObj.getString("prod_img");
                  //   String [] arrSplit = imgName.split(",");
                    // pic = new SliderImage[arrSplit.length];

                     type = jObj.getString("prod_name");
                     material = jObj.getString("materials");
                     color = jObj.getString("color");
                     waigth = jObj.getString("weight");
                     JSONArray jsonArray = new JSONArray(Res2);
                     for (int i = 0; i < jsonArray.length(); i++) {
                         JSONObject jsonObject = jsonArray.getJSONObject(i);
                         brandName = jsonObject.getString("brand_name");
                         details = jsonObject.getString("include_content");
                     }
                     txtType.setText(type);
                     txtBrand.setText(brandName);
                     txtColor.setText(color);
                     txtMaterial.setText(material);
                     txtWeight.setText(waigth);
                     txtDetails.setText(details);
                    // Slider1();

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(ProductDetailsActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
             }
         });

         queue.add(stringRequest);
     }
}
