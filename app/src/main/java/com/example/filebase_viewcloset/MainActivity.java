package com.example.filebase_viewcloset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.filebase_viewcloset.Claendar.CalendarFragment;
import com.example.filebase_viewcloset.Clothes.ClothesFragment;
import com.example.filebase_viewcloset.Home.HomeFragment;
import com.example.filebase_viewcloset.Mypage.MypageDeleteActivity;
import com.example.filebase_viewcloset.Mypage.MypageFragment;
import com.example.filebase_viewcloset.Ootd.OotdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ClothesFragment clothes;
    HomeFragment home;
    CalendarFragment calendar;
    MypageFragment mypage;
    OotdFragment ootd;
    BottomNavigationView bottom_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clothes = new ClothesFragment();
        home = new HomeFragment();
        calendar = new CalendarFragment();
        mypage = new MypageFragment();
        ootd = new OotdFragment();


        bottom_menu = findViewById(R.id.bottom_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, home).commit();

        bottom_menu.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home_id:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, home).commit();
                        break;
                    case R.id.Clothes_id:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, clothes).commit();
                        break;
                    case R.id.Calendar_id:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, calendar).commit();
                        break;
                    case R.id.Ootd_id:
                        Intent intent = new Intent(MainActivity.this, W_Main.class);
                        startActivity(intent);
                        break;
                    case R.id.MyPage_id:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, mypage).commit();
                        break;
                }
                return true;
            }
        });

    }


}