package com.example.filebase_viewcloset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.filebase_viewcloset.ui.main.adapter.MainTabPagerAdapter;
import com.example.filebase_viewcloset.util.GpsTracker;
import com.example.filebase_viewcloset.util.PreferenceManager;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class W_Main extends AppCompatActivity {

    private TabLayout tabLayout;
    private MainTabPagerAdapter pagerAdpater;
    private ViewPager viewPager;

    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmain);

        initView();

        if(PreferenceManager.getBoolean(this, "MAIN_NOTICE_DIALOG")!=true){
            //showMainNoticeDialog();
        }
    }

    private void initSharedPreference(){
        Log.e("SEULGI SP", ""+PreferenceManager.getFloat(this, "LATITUDE"));
        Log.e("SEULGI SP", ""+PreferenceManager.getFloat(this, "LONGITUDE"));
        if(PreferenceManager.getFloat(this, "LATITUDE")==-1F){
            if(latitude!=0.0 && longitude!=0.0){
                PreferenceManager.setFloat(this,"LATITUDE",(float)latitude);
            }
            else {
                PreferenceManager.setFloat(this,"LATITUDE",37.5172f);
            }
        }
        if(PreferenceManager.getFloat(this, "LONGITUDE")==-1F){
            if(latitude!=0.0 && longitude!=0.0){
                PreferenceManager.setFloat(this,"LONGITUDE",(float)longitude);
            }
            else {
                PreferenceManager.setFloat(this,"LONGITUDE",127.0473f);
            }
        }
        if(PreferenceManager.getString(this, "CITY").equals("")){
            PreferenceManager.setString(getApplicationContext(),"CITY","??????????????? ?????????");
        }

        PreferenceManager.setInt(this, "REGION_NUMBER",1);
    }

    private void initView() {
        /* tab layout */
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        pagerAdpater = new MainTabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        int region_number=PreferenceManager.getInt(this,"REGION_NUMBER");

        pagerAdpater.initFragment(region_number);
        for(int i=0;i<region_number;i++){
            tabLayout.addTab(tabLayout.newTab());
        }

        viewPager = (ViewPager) findViewById(R.id.main_tab_viewpager);
        viewPager.setAdapter(pagerAdpater);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerAdpater.notifyDataSetChanged();
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdpater.getWeatherFragment(tab.getPosition()).displayWeather(getApplicationContext());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

//    private void showMainNoticeDialog() {
//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//                .setTitle("??? ?????? ?????? ??? ??????")
//                .setMessage("???????????? ???????????? ?????? ????????? ?????? ????????? ???????????????!")
//                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setNegativeButton("?????? ?????? ??????", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        PreferenceManager.setBoolean(getApplicationContext(), "MAIN_NOTICE_DIALOG",true);
//                    }
//                })
//                .show();
//    }


}