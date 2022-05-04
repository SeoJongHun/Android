package com.example.filebase_viewcloset;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView gif_viewcloset = (ImageView) findViewById(R.id.gif_viewcloset);
        Glide.with(this).load(R.drawable.viewcloset_02).into(gif_viewcloset);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        }, 3000);

    }


}