package com.sublime.sublimecash.sublime.History;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sublime.sublimecash.sublime.OrdersActivity;
import com.sublime.sublimecash.sublime.R;

import Fragments.AllOrdersFragment;
import Fragments.AmazonFragment;
import Fragments.BankDetailsFragment;
import Fragments.BhimDetailsFragment;
import Fragments.FlipkartFragment;
import Fragments.IIAshopingFragment;
import Fragments.OthersFragment;
import Fragments.OyoFragment;
import Fragments.PaytmQRFragment;
import Fragments.RechargeBillsFragment;
import Fragments.SwiggyFragment;

public class BankDetailsActivity extends AppCompatActivity {
    ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private String CurrentPage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Bank Details");
        actionBar.show();

        TabLayout tabs =  findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);
        tabs.addTab(tabs.newTab().setText("Bank Details"));
        tabs.addTab(tabs.newTab().setText("Paytm Details"));
        tabs.addTab(tabs.newTab().setText("Bhim Details"));
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0)
                {
                    CurrentPage = "BankDetailsFragment";
                }
                else if(tab.getPosition() == 1)
                {
                    CurrentPage = "PaytmQRFragment";
                }
                else if(tab.getPosition() == 2)
                {
                    CurrentPage = "BhimDetailsFragment";
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

        private BankDetailsFragment bankDetailsFragment;
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
                    if(bankDetailsFragment==null)
                    {
                        bankDetailsFragment = new BankDetailsFragment();
                    }
                    return bankDetailsFragment;
                case 1:
                    return new PaytmQRFragment();
                case 2:
                    // Movies fragment activity
                    return new BhimDetailsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0)
            {
                return "Bank Details";
            }
            else if (position==1)
            {
                return "Paytm Details";

            }
            else if (position==2)
            {
                return "Bhim Details";
            }
            return null;
        }
    }
}
