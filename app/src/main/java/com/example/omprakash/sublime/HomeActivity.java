package com.example.omprakash.sublime;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import Fragments.BankFragment;
import Fragments.HomeFragment;
import Fragments.ProfileFragment;
import Fragments.ShopFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setIcon(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
       // getSupportActionBar().setIcon(R.drawable.header1);
        actionBar.show();

       // loadFragment(new HomeFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_Home:
                fragment = new HomeFragment();
                loadFragment(new HomeFragment());
                break;

            case R.id.navigation_balance:
                fragment = new BankFragment();
                loadFragment(new HomeFragment());
                break;

            case R.id.navigation_cart:
                fragment = new ShopFragment();
                loadFragment(new HomeFragment());
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                loadFragment(new HomeFragment());
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_profile) {

            //Intent profileIntent = new Intent(DashboardActivity.this, SettingActivity.class);
           // startActivity(profileIntent);
        }

        if (id == R.id.action_Login) {


        }
        return super.onOptionsItemSelected(item);
    }

}
