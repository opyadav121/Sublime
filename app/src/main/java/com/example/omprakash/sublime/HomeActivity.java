package com.example.omprakash.sublime;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.omprakash.sublime.Recharge.BigrockActivity;
import com.example.omprakash.sublime.Recharge.BroadbandActivity;
import com.example.omprakash.sublime.Recharge.DTHActivity;
import com.example.omprakash.sublime.Recharge.ElectricityBillActivity;
import com.example.omprakash.sublime.Recharge.EthicActivity;
import com.example.omprakash.sublime.Recharge.GasBillActivity;
import com.example.omprakash.sublime.Recharge.GiftActivity;
import com.example.omprakash.sublime.Recharge.InsuranceRenualActivity;
import com.example.omprakash.sublime.Recharge.MakemyTripActivity;
import com.example.omprakash.sublime.Recharge.MobilePostpaidActivity;
import com.example.omprakash.sublime.Recharge.NNNOActivity;
import com.example.omprakash.sublime.Recharge.NetMaedsActivity;
import com.example.omprakash.sublime.Recharge.OyoActivity;
import com.example.omprakash.sublime.Recharge.ThemeParkActivity;
import com.example.omprakash.sublime.Recharge.WaterActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private WebView webAdd;
    private static ViewPager mPager;
    private static int currentPager = 0;
    ArrayList<Integer> picArray = new ArrayList<Integer>();
    private static final Integer [] pic = {R.drawable.ban1, R.drawable.ban2,R.drawable.ban3, R.drawable.ban4};
    private MenuItem item;
    private ImageView mobilePostpaid,mobilePrepaid,electricity,iconGas,iconWater,iconInsurance,
            iconBroadband,iconDth,iconGift,IconTheme;
    CircleImageView iconEthic,iconRydon,bigrock,netmeds,nnnow,oyo,makemytrip;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    Intent home = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(home);
                    return true;
                case R.id.navigation_business:
                   Intent dasboard = new Intent(getApplicationContext(),DashboardActivity.class);
                   startActivity(dasboard);
                    return true;
                case R.id.navigation_bank:
                    // mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_cart:
                   // mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_profile:
                    // mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        webAdd = findViewById(R.id.webAdd);
        webAdd.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=9296&aff_id=7820&file_id=185040" );
        webAdd.loadUrl("https://media.vcommission.com/brand/files/vcm/9296/Zee5_CPA_Skyfire_320x50.jpg" );

        BottomNavigationView navigView = findViewById(R.id.navig_view);
        //mTextMessage = findViewById(R.id.message);
        navigView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Slider1();
        SetFlippers();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_profile) {

            Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
            return true;
        }
        if (id == R.id.action_Login) {

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
             startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        this.item = item;

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {
            String InviteMessage = "Try the Lets Catch app to Locate and track your friend  https://play.google.com/store/apps/details?id=net.anvisys.letscatch";
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,InviteMessage);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,"SEND"));
        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Slider1(){

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
        },4000,4000);
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
    }


    private void SetFlippers(){
        mobilePostpaid =  findViewById(R.id.mobilePostpaid);
        mobilePostpaid.setOnClickListener(new clicker());
        mobilePrepaid =  findViewById(R.id.mobilePrepaid);
        mobilePrepaid.setOnClickListener(new clicker());
        electricity =  findViewById(R.id.electricity);
        electricity.setOnClickListener(new clicker());
        iconGas =  findViewById(R.id.iconGas);
        iconGas.setOnClickListener(new clicker());
        iconWater =  findViewById(R.id.iconWater);
        iconWater.setOnClickListener(new clicker());
        iconInsurance =  findViewById(R.id.iconInsurance);
        iconInsurance.setOnClickListener(new clicker());
        iconBroadband = findViewById(R.id.iconBroadband);
        iconBroadband.setOnClickListener(new clicker());
        iconDth =  findViewById(R.id.iconDth);
        iconDth.setOnClickListener(new clicker());
        iconGift =  findViewById(R.id.iconGift);
        iconGift.setOnClickListener(new clicker());
        IconTheme =  findViewById(R.id.IconTheme);
        IconTheme.setOnClickListener(new clicker());
        iconEthic =  findViewById(R.id.iconEthic);
        iconEthic.setOnClickListener(new clicker());
        iconRydon =  findViewById(R.id.iconRydon);
        iconRydon.setOnClickListener(new clicker());
        bigrock =  findViewById(R.id.bigrock);
        bigrock.setOnClickListener(new clicker());
        netmeds =  findViewById(R.id.netmeds);
        netmeds.setOnClickListener(new clicker());
        nnnow =  findViewById(R.id.nnnow);
        nnnow.setOnClickListener(new clicker());
        oyo =  findViewById(R.id.oyo);
        oyo.setOnClickListener(new clicker());
        makemytrip =  findViewById(R.id.makemytrip);
        makemytrip.setOnClickListener(new clicker());

    }

    class clicker implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mobilePostpaid :{
                    Intent postpaid = new Intent(HomeActivity.this, MobilePostpaidActivity.class);
                    startActivity(postpaid);
                    break;
                }
                case R.id.mobilePrepaid :{
                    Intent prepaid = new Intent(HomeActivity.this, MobilePostpaidActivity.class);
                    startActivity(prepaid);
                    break;
                }
                case R.id.electricity :{
                    Intent electric = new Intent(HomeActivity.this, ElectricityBillActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.iconGas :{
                    Intent gas = new Intent(HomeActivity.this, GasBillActivity.class);
                    startActivity(gas);
                    break;
                }
                case R.id.iconWater :{
                    Intent water = new Intent(HomeActivity.this, WaterActivity.class);
                    startActivity(water);
                    break;
                }
                case R.id.iconInsurance :{
                    Intent insurance = new Intent(HomeActivity.this, InsuranceRenualActivity.class);
                    startActivity(insurance);
                    break;
                }
                case R.id.iconBroadband :{
                    Intent broadband = new Intent(HomeActivity.this, BroadbandActivity.class);
                    startActivity(broadband);
                    break;
                }
                case R.id.iconDth :{
                    Intent dth = new Intent(HomeActivity.this, DTHActivity.class);
                    startActivity(dth);
                    break;
                }
                case R.id.netmeds :{
                    Intent electric = new Intent(HomeActivity.this, NetMaedsActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.nnnow :{
                    Intent electric = new Intent(HomeActivity.this, NNNOActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.oyo :{
                    Intent electric = new Intent(HomeActivity.this, OyoActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.makemytrip :{
                    Intent electric = new Intent(HomeActivity.this, MakemyTripActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.bigrock :{
                    Intent dth = new Intent(HomeActivity.this, BigrockActivity.class);
                    startActivity(dth);
                    break;
                }
                case R.id.iconGift :{
                    Intent electric = new Intent(HomeActivity.this, GiftActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.IconTheme :{
                    Intent electric = new Intent(HomeActivity.this, ThemeParkActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.iconEthic :{
                    Intent electric = new Intent(HomeActivity.this, EthicActivity.class);
                    startActivity(electric);
                    break;
                }
                case R.id.iconRydon :{
                    Intent electric = new Intent(HomeActivity.this, ThemeParkActivity.class);
                    startActivity(electric);
                    break;
                }
            }
        }
    }

}
