package com.sublime.sublimecash.sublime;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import Fragments.AmazonFragment;
import Fragments.FlipkartFragment;
import Fragments.IIAshopingFragment;
import Fragments.OthersFragment;
import Fragments.OyoFragment;
import Fragments.RechargeBillsFragment;
import Fragments.SwiggyFragment;

public class OrdersActivity extends AppCompatActivity {

    ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private String CurrentPage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("ORDERS");
        actionBar.show();

        TabLayout tabs =  findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);
        tabs.addTab(tabs.newTab().setText("Recharge & Bills"));
        tabs.addTab(tabs.newTab().setText("Shopping"));
        tabs.addTab(tabs.newTab().setText("Amazon"));
        tabs.addTab(tabs.newTab().setText("Flipkart"));
        tabs.addTab(tabs.newTab().setText("Swiggy"));
        tabs.addTab(tabs.newTab().setText("OYO"));
        tabs.addTab(tabs.newTab().setText("Others"));
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0)
                {
                    CurrentPage = "RechargeBillsFragment";
                }
                else if(tab.getPosition() == 1)
                {
                    CurrentPage = "IIAshopingFragment";
                }
                else if(tab.getPosition() == 2)
                {
                    CurrentPage = "AmazonFragment";
                }
                else if(tab.getPosition() == 3)
                {
                    CurrentPage = "FlipkartFragment";
                }
                else if(tab.getPosition() == 4)
                {
                    CurrentPage = "SwiggyFragment";
                }
                else if(tab.getPosition() == 5)
                {
                    CurrentPage = "OyoFragment";
                }
                else if(tab.getPosition() == 6)
                {
                    CurrentPage = "OthersFragment";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class TabsPagerAdapter extends FragmentPagerAdapter {

        private RechargeBillsFragment rechargeBillsFragment;
        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            // return super.getItemPosition(object);
            return POSITION_UNCHANGED;
        }
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    if(rechargeBillsFragment==null)
                    {
                        rechargeBillsFragment = new RechargeBillsFragment();
                    }
                    return rechargeBillsFragment;
                case 1:
                    // Movies fragment activity
                    return new IIAshopingFragment();
                case 2:
                    // Movies fragment activity
                    return new AmazonFragment();
                case 3:
                    return new FlipkartFragment();
                case 4:
                    // Movies fragment activity
                    return new SwiggyFragment();
                case 5:
                    // Movies fragment activity
                    return new OyoFragment();
                case 6:
                    // Movies fragment activity
                    return new OthersFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
           if (position==0)
            {
                return "Recharge & Bills";

            }
            else if (position==1)
            {
                return "Shopping";
            }
            else if (position==2)
            {
                return "Amazon";
            }
            else if (position==3)
            {
                return "Flipkart";
            }
            else if (position==4)
            {
                return "Swiggy";
            }
            else if (position==5)
            {
                return "OYO";
            }
            else if (position==6)
            {
                return "Others";
            }
            return null;
        }
    }
}
