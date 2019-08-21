package com.sublime.sublimecash.sublime;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.E_Commerce.LehengaActivity;
import com.sublime.sublimecash.sublime.E_Commerce.MensActivity;
import com.sublime.sublimecash.sublime.E_Commerce.SareesActivity;
import com.sublime.sublimecash.sublime.E_Commerce.SuitsActivity;
import com.sublime.sublimecash.sublime.E_Commerce.WomensActivity;
import com.sublime.sublimecash.sublime.History.DailyIncomeActivity;
import com.sublime.sublimecash.sublime.History.DirectBonazaActivity;
import com.sublime.sublimecash.sublime.History.ROIIncomeActivity;
import com.sublime.sublimecash.sublime.History.RewardsActivity;
import com.sublime.sublimecash.sublime.Recharge.AddMoneyActivity;
import com.sublime.sublimecash.sublime.Recharge.BagActivity;
import com.sublime.sublimecash.sublime.Recharge.BigrockActivity;
import com.sublime.sublimecash.sublime.Recharge.BinaryIncomeActivity;
import com.sublime.sublimecash.sublime.Recharge.BookMyFlavoursActivity;
import com.sublime.sublimecash.sublime.Recharge.BroadbandActivity;
import com.sublime.sublimecash.sublime.Recharge.CleartripActivity;
import com.sublime.sublimecash.sublime.Recharge.CouponActivity;
import com.sublime.sublimecash.sublime.Recharge.CouponRequestActivity;
import com.sublime.sublimecash.sublime.Recharge.DTHActivity;
import com.sublime.sublimecash.sublime.Recharge.EWalletHistoryActivity;
import com.sublime.sublimecash.sublime.Recharge.ElectricityBillActivity;
import com.sublime.sublimecash.sublime.Recharge.EthicActivity;
import com.sublime.sublimecash.sublime.Recharge.FabHotelsActivity;
import com.sublime.sublimecash.sublime.Recharge.FirstCryActivity;
import com.sublime.sublimecash.sublime.Recharge.GasBillActivity;
import com.sublime.sublimecash.sublime.Recharge.GiftActivity;
import com.sublime.sublimecash.sublime.Recharge.GiftAloveActivity;
import com.sublime.sublimecash.sublime.Recharge.Gud2Activity;
import com.sublime.sublimecash.sublime.Recharge.IncentiveActivity;
import com.sublime.sublimecash.sublime.Recharge.InsuranceRenualActivity;
import com.sublime.sublimecash.sublime.Recharge.JockeyActivity;
import com.sublime.sublimecash.sublime.Recharge.LandlineActivity;
import com.sublime.sublimecash.sublime.Recharge.MakemyTripActivity;
import com.sublime.sublimecash.sublime.Recharge.MedLifeActivity;
import com.sublime.sublimecash.sublime.Recharge.MobilePostpaidActivity;
import com.sublime.sublimecash.sublime.Recharge.MoneyTransferActivity;
import com.sublime.sublimecash.sublime.Recharge.NNNOActivity;
import com.sublime.sublimecash.sublime.Recharge.NetMaedsActivity;
import com.sublime.sublimecash.sublime.Recharge.NutrafyActivity;
import com.sublime.sublimecash.sublime.Recharge.OyoActivity;
import com.sublime.sublimecash.sublime.Recharge.PaperFryActivity;
import com.sublime.sublimecash.sublime.Recharge.PizzaHutActivity;
import com.sublime.sublimecash.sublime.Recharge.PostpaidActivity;
import com.sublime.sublimecash.sublime.Recharge.PrepaidActivity;
import com.sublime.sublimecash.sublime.Recharge.PrepaidRechargeActivity;
import com.sublime.sublimecash.sublime.Recharge.ReebokActivity;
import com.sublime.sublimecash.sublime.Recharge.RydonActivity;
import com.sublime.sublimecash.sublime.Recharge.SWalletHistoryActivity;
import com.sublime.sublimecash.sublime.Recharge.SukhhiActivity;
import com.sublime.sublimecash.sublime.Recharge.SupportActivity;
import com.sublime.sublimecash.sublime.Recharge.ThemeParkActivity;
import com.sublime.sublimecash.sublime.Recharge.UserListActivity;
import com.sublime.sublimecash.sublime.Recharge.WaterActivity;
import com.sublime.sublimecash.sublime.Recharge.Zee5Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Common.Constants;
import Common.Session;
import Model.History;
import Model.Profile;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ProgressDialog progressDialog;
    Profile myProfile;
    String Status;
    RequestQueue requestQueue;
    VideoView videoView;
    private WebView webAdd;
    private static ViewPager mPager;
    private static int currentPager = 0;
    ArrayList<Integer> picArray = new ArrayList<Integer>();
    private static final Integer [] pic = {R.drawable.ban6, R.drawable.ban4,R.drawable.ban1new, R.drawable.ban2new,R.drawable.ban3new};
    private MenuItem item;
    private ImageView mobilePostpaid,mobilePrepaid,electricity,iconGas,iconWater,iconInsurance,
            iconBroadband,iconDth,iconGift,IconTheme,iconLandline,imgWomen,imgMen,
            iconSarees,iconLehenga,iconWestern,iconSuits,iconEthinic,iconCasual,iconFormal,iconSports;
    CircleImageView iconEthic,iconRydon,bigrock,netmeds,nnnow,oyo,makemytrip,iconfirstCry,iconJockey,
            iconGiftAlove,iconSukhhi,iconPaperFry,icongud2,iconFabHotels,cleartrip,iconBookmyFlaours,
            iconMedLife,iconPizzaHut,iconNaturefy,iconZee5,iconReebok,
            addMoney,iconMoneyTransfer,iconRefer,iconAeps;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_Home:
                    if (myProfile == null || myProfile.UserLogin.matches("")) {
                        Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(LoginIntent);
                    } else {
                        Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(home);
                    }
                    return true;
                case R.id.navigation_business:
                    if (myProfile == null || myProfile.UserLogin.matches("")) {
                        Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(LoginIntent);
                    } else {
                        Intent dasboard = new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(dasboard);
                    }
                    return true;
                case R.id.navigation_bank:
                    if (myProfile == null || myProfile.UserLogin.matches("")) {
                        Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(LoginIntent);
                    } else {
                        Intent bag = new Intent(getApplicationContext(), BagActivity.class);
                        startActivity(bag);
                    }
                    return true;
                case R.id.navigation_cart:
                    if (myProfile == null || myProfile.UserLogin.matches("")) {
                        Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(LoginIntent);
                    } else {
                        Intent shopping = new Intent(getApplicationContext(), OrdersActivity.class);
                        startActivity(shopping);
                    }
                    return true;
                case R.id.navigation_profile:
                    if (myProfile == null || myProfile.UserLogin.matches("")) {
                        Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(LoginIntent);
                    } else {
                        Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profile);
                    }
                    return true;
            }
            return false;
        }
    };

    ImageView notice;
    TextView textOne;

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
        myProfile = Session.GetProfile(getApplicationContext());
        videoView=findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }// to repeat the video
        });
        notice = findViewById(R.id.notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCount();
                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        textOne = findViewById(R.id.textOne);

        videoView.setMediaController(new MediaController(this));
        videoView.start();
        webAdd = findViewById(R.id.webAdd);
        webAdd.loadUrl("https://media.vcommission.com/brand/files/vcm/9296/Zee5_CPA_Skyfire_320x50.jpg" );
        webAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddsActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView navigView = findViewById(R.id.navig_view);
        requestQueue = Volley.newRequestQueue(this);
        //mTextMessage = findViewById(R.id.message);
        navigView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Slider1();
        SetFlippers();
        WalletBalance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_notification) {
            if (myProfile == null || myProfile.UserLogin.matches("")) {
                Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            } else {
                Intent profileIntent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(profileIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        this.item = item;
        int id = item.getItemId();
        if (myProfile == null || myProfile.UserLogin.matches("")) {
            Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(LoginIntent);
        } else {
            if (id == R.id.nav_gallery) {
            } else if (id == R.id.nav_slideshow) {
            } else if (id == R.id.action_Pan) {
                Intent intent = new Intent(HomeActivity.this, KYCActivity.class);
                startActivity(intent);
            }else if (id == R.id.action_SWallet) {
                Intent intent = new Intent(HomeActivity.this, SWalletHistoryActivity.class);
                startActivity(intent);
            }else if (id == R.id.action_Support) {
                Intent intent = new Intent(HomeActivity.this, SupportActivity.class);
                startActivity(intent);
            }else if (id == R.id.action_Incentive) {
                Intent intent = new Intent(HomeActivity.this, IncentiveActivity.class);
                startActivity(intent);
            }else if (id == R.id.action_SBWallet) {
                item.setTitle("SB-Cash                "+"\u20B9 "+myProfile.SbWallet);

            }else if (id == R.id.action_LogOut) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure?");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Session.LogOff(getApplicationContext());
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog Alert = builder.create();
                Alert.show();
                return true;
            }
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
    }

    private void SetFlippers(){
        addMoney =  findViewById(R.id.addMoney);
        addMoney.setOnClickListener(new clicker());
        iconMoneyTransfer =  findViewById(R.id.iconMoneyTransfer);
        iconMoneyTransfer.setOnClickListener(new clicker());
        iconRefer =  findViewById(R.id.iconRefer);
        iconRefer.setOnClickListener(new clicker());
        iconAeps =  findViewById(R.id.iconAeps);
        iconAeps.setOnClickListener(new clicker());
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
        iconLandline = findViewById(R.id.iconLandline);
        iconLandline.setOnClickListener(new clicker());
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
        iconfirstCry =  findViewById(R.id.iconfirstCry);
        iconfirstCry.setOnClickListener(new clicker());
        iconJockey =  findViewById(R.id.iconJockey);
        iconJockey.setOnClickListener(new clicker());
        iconGiftAlove =  findViewById(R.id.iconGiftAlove);
        iconGiftAlove.setOnClickListener(new clicker());
        iconSukhhi =  findViewById(R.id.iconSukhhi);
        iconSukhhi.setOnClickListener(new clicker());
        iconPaperFry =  findViewById(R.id.iconPaperFry);
        iconPaperFry.setOnClickListener(new clicker());
        icongud2 =  findViewById(R.id.icongud2);
        icongud2.setOnClickListener(new clicker());
        iconFabHotels =  findViewById(R.id.iconFabHotels);
        iconFabHotels.setOnClickListener(new clicker());
        cleartrip =  findViewById(R.id.cleartrip);
        cleartrip.setOnClickListener(new clicker());
        iconBookmyFlaours =  findViewById(R.id.iconBookmyFlaours);
        iconBookmyFlaours.setOnClickListener(new clicker());
        iconMedLife =  findViewById(R.id.iconMedLife);
        iconMedLife.setOnClickListener(new clicker());
        iconPizzaHut =  findViewById(R.id.iconPizzaHut);
        iconPizzaHut.setOnClickListener(new clicker());
        iconNaturefy =  findViewById(R.id.iconNaturefy);
        iconNaturefy.setOnClickListener(new clicker());
        iconZee5 =  findViewById(R.id.iconZee5);
        iconZee5.setOnClickListener(new clicker());
        iconReebok =  findViewById(R.id.iconReebok);
        iconReebok.setOnClickListener(new clicker());
        imgWomen =  findViewById(R.id.imgWomen);
        imgWomen.setOnClickListener(new clicker());
        imgMen =  findViewById(R.id.imgMen);
        imgMen.setOnClickListener(new clicker());

        iconSarees =  findViewById(R.id.iconSarees);
        iconSarees.setOnClickListener(new clicker());
        iconLehenga =  findViewById(R.id.iconLehenga);
        iconLehenga.setOnClickListener(new clicker());
        iconWestern =  findViewById(R.id.iconWestern);
        iconWestern.setOnClickListener(new clicker());
        iconSuits =  findViewById(R.id.iconSuits);
        iconSuits.setOnClickListener(new clicker());
        iconEthinic =  findViewById(R.id.iconEthinic);
        iconEthinic.setOnClickListener(new clicker());
        iconCasual =  findViewById(R.id.iconCasual);
        iconCasual.setOnClickListener(new clicker());
        iconFormal =  findViewById(R.id.iconFormal);
        iconFormal.setOnClickListener(new clicker());
        iconSports =  findViewById(R.id.iconSports);
        iconSports.setOnClickListener(new clicker());
    }

    class clicker implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (myProfile == null || myProfile.UserLogin.matches("")) {
                Intent LoginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            } else {
            switch (v.getId()) {
                    case R.id.addMoney: {
                        Intent postpaid = new Intent(HomeActivity.this, AddMoneyActivity.class);
                        startActivity(postpaid);
                        break;
                    }
                    case R.id.iconMoneyTransfer: {
                        Intent prepaid = new Intent(HomeActivity.this, MoneyTransferActivity.class);
                        startActivity(prepaid);
                        break;
                    }
                    case R.id.iconRefer: {
                        Intent electric = new Intent(HomeActivity.this, RefferActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconAeps: {
                        Intent gas = new Intent(HomeActivity.this, AEPSActivity.class);
                        startActivity(gas);
                        break;
                    }
                    case R.id.mobilePostpaid: {
                        Intent postpaid = new Intent(HomeActivity.this, PostpaidActivity.class);
                        startActivity(postpaid);
                        break;
                    }
                    case R.id.mobilePrepaid: {
                        Intent prepaid = new Intent(HomeActivity.this, PrepaidActivity.class);
                        startActivity(prepaid);
                        break;
                    }
                    case R.id.electricity: {
                        Intent electric = new Intent(HomeActivity.this, ElectricityBillActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconGas: {
                        Intent gas = new Intent(HomeActivity.this, GasBillActivity.class);
                        startActivity(gas);
                        break;
                    }
                    case R.id.iconWater: {
                        Intent water = new Intent(HomeActivity.this, WaterActivity.class);
                        startActivity(water);
                        break;
                    }
                    case R.id.iconInsurance: {
                        Intent insurance = new Intent(HomeActivity.this, InsuranceRenualActivity.class);
                        startActivity(insurance);
                        break;
                    }
                    case R.id.iconBroadband: {
                        Intent broadband = new Intent(HomeActivity.this, BroadbandActivity.class);
                        startActivity(broadband);
                        break;
                    }
                    case R.id.iconDth: {
                        Intent dth = new Intent(HomeActivity.this, DTHActivity.class);
                        startActivity(dth);
                        break;
                    }
                    case R.id.iconLandline: {
                        Intent dth = new Intent(HomeActivity.this, LandlineActivity.class);
                        startActivity(dth);
                        break;
                    }
                    case R.id.netmeds: {
                        Intent electric = new Intent(HomeActivity.this, NetMaedsActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.nnnow: {
                        Intent electric = new Intent(HomeActivity.this, NNNOActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.oyo: {
                        Intent electric = new Intent(HomeActivity.this, OyoActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.makemytrip: {
                        Intent electric = new Intent(HomeActivity.this, MakemyTripActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.bigrock: {
                        Intent dth = new Intent(HomeActivity.this, BigrockActivity.class);
                        startActivity(dth);
                        break;
                    }
                    case R.id.iconGift: {
                        Intent electric = new Intent(HomeActivity.this, GiftActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.IconTheme: {
                        Intent electric = new Intent(HomeActivity.this, ThemeParkActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconEthic: {
                        Intent electric = new Intent(HomeActivity.this, EthicActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconRydon: {
                        Intent electric = new Intent(HomeActivity.this, RydonActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconfirstCry: {
                        Intent electric = new Intent(HomeActivity.this, FirstCryActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconJockey: {
                        Intent electric = new Intent(HomeActivity.this, JockeyActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconGiftAlove: {
                        Intent electric = new Intent(HomeActivity.this, GiftAloveActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconSukhhi: {
                        Intent dth = new Intent(HomeActivity.this, SukhhiActivity.class);
                        startActivity(dth);
                        break;
                    }
                    case R.id.iconPaperFry: {
                        Intent electric = new Intent(HomeActivity.this, PaperFryActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.icongud2: {
                        Intent electric = new Intent(HomeActivity.this, Gud2Activity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconFabHotels: {
                        Intent electric = new Intent(HomeActivity.this, FabHotelsActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.cleartrip: {
                        Intent electric = new Intent(HomeActivity.this, CleartripActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconBookmyFlaours: {
                        Intent electric = new Intent(HomeActivity.this, BookMyFlavoursActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconMedLife: {
                        Intent electric = new Intent(HomeActivity.this, MedLifeActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconPizzaHut: {
                        Intent electric = new Intent(HomeActivity.this, PizzaHutActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconNaturefy: {
                        Intent electric = new Intent(HomeActivity.this, NutrafyActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconZee5: {
                        Intent dth = new Intent(HomeActivity.this, Zee5Activity.class);
                        startActivity(dth);
                        break;
                    }
                    case R.id.iconReebok: {
                        Intent electric = new Intent(HomeActivity.this, ReebokActivity.class);
                        startActivity(electric);
                        break;
                    }
                    case R.id.imgMen: {
                        Intent dth = new Intent(HomeActivity.this, MensActivity.class);
                        startActivity(dth);
                        break;
                    }
                    case R.id.imgWomen: {
                        Intent electric = new Intent(HomeActivity.this, WomensActivity.class);
                        startActivity(electric);
                        break;
                    }

                    case R.id.iconSarees: {
                        Intent electric = new Intent(HomeActivity.this, SareesActivity.class);
                       // electric.putExtra("Saree","Type");
                        startActivity(electric);
                        break;
                    }
                    case R.id.iconLehenga: {
                         Intent electric = new Intent(HomeActivity.this, LehengaActivity.class);
                         startActivity(electric);
                        break;
                    }
                    case R.id.iconWestern: {
                        //  Intent electric = new Intent(HomeActivity.this, PizzaHutActivity.class);
                        // startActivity(electric);
                        AlertSoldOut();
                        break;
                    }
                    case R.id.iconSuits: {
                          Intent electric = new Intent(HomeActivity.this, SuitsActivity.class);
                          startActivity(electric);
                        break;
                    }
                    case R.id.iconEthinic: {
                        //Intent dth = new Intent(HomeActivity.this, Zee5Activity.class);
                        // startActivity(dth);
                        AlertSoldOut();
                        break;
                    }
                    case R.id.iconCasual: {
                        // Intent electric = new Intent(HomeActivity.this, ReebokActivity.class);
                        // startActivity(electric);
                        AlertSoldOut();
                        break;
                    }
                    case R.id.iconFormal: {
                        // Intent dth = new Intent(HomeActivity.this, MensActivity.class);
                        //  startActivity(dth);
                        AlertSoldOut();
                        break;
                    }
                    case R.id.iconSports: {
                        AlertSoldOut();
                        //  Intent electric = new Intent(HomeActivity.this, WomensActivity.class);
                        //  startActivity(electric);
                        break;
                    }
                }
            }
        }
    }

    public void AlertSoldOut(){
        AlertDialog.Builder builder= new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Sold Out!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog Alert = builder.create();
        Alert.show();
    }

    public void WalletBalance(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String Wallet_url= Constants.Application_URL+"/users/index.php/Recharge/wallet";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Wallet_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    myProfile.EWallet = jObj.getString("E-Wallet");
                    myProfile.SWallet = jObj.getString("S-Wallet");
                    myProfile.PendingWallet = jObj.getString("Pending_balance");
                    myProfile.SbWallet = jObj.getString("sbcash");
                    Session.AddProfile(getApplicationContext(), myProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        AlertDialog.Builder builder= new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Are you sure to Exit ?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                HomeActivity.this.finish();
            }
        });
        AlertDialog Alert = builder.create();
        Alert.show();

      /*  if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }   */
    }

    public void NotificationCount(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String Wallet_url= Constants.Application_URL+"/users/index.php/user/notification_count";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Wallet_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Count = jObj.getString("count");
                    textOne.setText(Count);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
