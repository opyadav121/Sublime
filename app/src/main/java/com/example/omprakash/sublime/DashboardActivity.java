package com.example.omprakash.sublime;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Common.Session;
import Model.Profile;
import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class DashboardActivity extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPager = 0;
    ArrayList<Integer> picArray=new ArrayList<Integer>();
    private static final Integer [] pic = {R.drawable.poster1, R.drawable.poster2,R.drawable.poster3, R.drawable.poster4};

    Profile myProfile;
    CircleImageView imageProfile;
    TextView txtName,lastLoginTime,userId,doj,txtUnclearBalance,txtMobile,txtSelfIncome,txtTeamSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Dashboard");
        actionBar.show();
        Slider1();
        myProfile = Session.GetProfile(this);
        imageProfile = findViewById(R.id.imageProfile);
        txtName = findViewById(R.id.txtName);
        lastLoginTime = findViewById(R.id.lastLoginTime);
        userId = findViewById(R.id.userId);
        doj = findViewById(R.id.doj);
        txtUnclearBalance = findViewById(R.id.txtUnclearBalance);
        txtMobile = findViewById(R.id.txtMobile);
        txtSelfIncome = findViewById(R.id.txtSelfIncome);
        txtTeamSize = findViewById(R.id.txtTeamSize);

        txtName.setText(myProfile.UserName);
        txtMobile.setText(myProfile.MobileNumber);
        userId.setText(myProfile.UserID);
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

}
