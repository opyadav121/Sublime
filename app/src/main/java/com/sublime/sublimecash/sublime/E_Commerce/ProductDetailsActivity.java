package com.sublime.sublimecash.sublime.E_Commerce;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.HtmlCompat;
import android.support.v4.util.LruCache;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.sublime.sublimecash.sublime.DashboardActivity;
import com.sublime.sublimecash.sublime.HomeActivity;
import com.sublime.sublimecash.sublime.R;
import com.sublime.sublimecash.sublime.Recharge.AddMoneyActivity;
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
import Model.SliderUtils;
import me.relex.circleindicator.CircleIndicator;

public class ProductDetailsActivity extends AppCompatActivity {

    GridView gridViewItem;
    private static ViewPager mPager;
    private static int currentPager = 0;
   // ArrayList<Integer> picArray =new ArrayList<Integer>();
    ArrayList<SliderImage> ItemList =new ArrayList<>();
    AdapterItem adapterItem;
    private static final Integer [] pic = {R.drawable.a, R.drawable.aa,R.drawable.aaa};
  //  SliderImage [] pic;
    Profile myProfile;
    TextView prodName,sellingPrice,printPrice,txtDescription,txtType,txtBrand,txtMaterial,txtWeight,txtColor,txtDetails;
    Button btnBuy;
    String name, prod_id, sellPrice,Mrp,desc,qnty="1";
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
        gridViewItem = findViewById(R.id.gridViewItem);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        Mrp = intent.getStringExtra("MRP");
        sellPrice = intent.getStringExtra("Selling");
        prod_id = intent.getStringExtra("Prod_id");
        desc = intent.getStringExtra("descrip");
        String imgname = intent.getStringExtra("imgName");
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
        txtDescription.setText(HtmlCompat.fromHtml(desc, 0));
        printPrice.setPaintFlags(printPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
      //  mPager = (ViewPager) findViewById(R.id.pager);
        adapterItem=new AdapterItem(ProductDetailsActivity.this, R.layout.gridveiw_item,ItemList);
        gridViewItem.setAdapter(adapterItem);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CheckOutActivity.class);
                 intent.putExtra("Qnty", qnty);
                intent.putExtra("Name", name);
                intent.putExtra("Selling", sellPrice);
                intent.putExtra("MRP", Mrp);
                intent.putExtra("imgName",imgname);

                startActivity(intent);
            }
        });
    }

 /* public void Slider1(){

      //  for (int i=0; i<pic.length; i++)
      //      picArray.add(pic[i]);

        mPager=findViewById(R.id.pager);
      //  mPager.setAdapter(new MyAdapter(picArray,this));
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
    }  */

    class AdapterItem extends ArrayAdapter {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterItem(Context context, int resource, ArrayList<SliderImage> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return ItemList.size();
        }

        @Nullable
        @Override
        public SliderImage getItem(int position) {
            return ItemList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.slide, null);
                    holder = new ViewHolder();
                    holder.image = convertView.findViewById(R.id.image);
                   // holder.txtOperatorName = convertView.findViewById(R.id.optName);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                SliderImage opt = getItem(position);
               // holder.txtOperatorName.setText(opt.ImageName);
                String url1 = "http://sublimecash.com/upload/product/" + opt.ImageName;
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
                     SliderImage prodImg = new SliderImage();
                     String [] arrSplit = imgName.split(",");
                     for (int j=0; j < 1; j++) {
                         prodImg.ImageName = arrSplit[j];
                         ItemList.add(prodImg);
                     }
                     adapterItem.notifyDataSetChanged();
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
