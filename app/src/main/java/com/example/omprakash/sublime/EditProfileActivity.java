package com.example.omprakash.sublime;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import Fragments.BankFragment;
import Fragments.InsuranceFragment;
import Fragments.KYCFragment;
import Fragments.PersonalFragment;

public class EditProfileActivity extends AppCompatActivity {
    ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;
    private String CurrentPage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Profile");
        actionBar.show();

        TabLayout tabs =  findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);
        tabs.addTab(tabs.newTab().setText("Personal"));
        tabs.addTab(tabs.newTab().setText("Bank"));
        tabs.addTab(tabs.newTab().setText("KYC"));
        tabs.addTab(tabs.newTab().setText("Insurance"));

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 0)
                {
                    CurrentPage = "PersonalFragment";
                }
                else if(tab.getPosition() == 1)
                {
                    CurrentPage = "BankFragment";
                }
                else if(tab.getPosition() == 2)
                {
                    CurrentPage = "KYCFragment";
                }
                else if(tab.getPosition() == 3)
                {
                    CurrentPage = "InsuranceFragment";
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

        private  BankFragment bankFragment;
        private PersonalFragment personalFragment;
        private KYCFragment kycFragment;
        private InsuranceFragment insuranceFragment;
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
                    if(personalFragment==null)
                    {
                        personalFragment = new PersonalFragment();
                    }
                    return personalFragment;
                case 1:

                    return new BankFragment();

                case 2:
                    // Movies fragment activity
                    return new KYCFragment();
                case 3:
                    // Movies fragment activity
                    return new InsuranceFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0)
            {
                return "Personal";

            }
            else if (position==1)
            {
                return "Bank";

            }
            else if (position==2)
            {
                return "KYC";
            }
            else if (position==2)
            {
                return "Insurance";
            }
            return null;
        }
    }
}
