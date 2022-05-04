package com.example.filebase_viewcloset.clothing;
/* 18°C ~ 20°C 이하 일 경우 추천해주는 액티비티 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.filebase_viewcloset.R;


public class TemperatureClothingActivity5 extends AppCompatActivity {
    TextView tx9;
    ImageView btnToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing5);

        btnToHome = (ImageView) findViewById(R.id.btn_back);
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tx9 = (TextView)findViewById(R.id.textView9);
        Intent intent = getIntent(); /*데이터 수신*/

        String name = intent.getStringExtra("temperature");

        tx9.setText(name + "°C");

    }
}