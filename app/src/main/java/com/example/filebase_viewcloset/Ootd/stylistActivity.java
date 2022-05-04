package com.example.filebase_viewcloset.Ootd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filebase_viewcloset.R;
import com.example.filebase_viewcloset.W_Main;
import com.example.filebase_viewcloset.ui.main.MainWeatherFragment;

public class stylistActivity extends AppCompatActivity {

    ImageView img_stylist1, img_stylist2;
    Button btn_stylist_add, btn_stylist_reset;
    TextView txt_stylist_weather, txt_stylist_lc1,txt_stylist_lc2,txt_stylist_color1,txt_stylist_color2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist);
        // 이미지
        img_stylist1 = findViewById(R.id.img_stylist1);
        img_stylist2 = findViewById(R.id.img_stylist2);
        // 버튼
        btn_stylist_add = findViewById(R.id.btn_stylist_add);
        btn_stylist_reset = findViewById(R.id.btn_stylist_reset);
        //스타일 텍스트
        txt_stylist_weather = findViewById(R.id.txt_stylist_weather);
        txt_stylist_color1 = findViewById(R.id.txt_stylist_color1);
        txt_stylist_color2 = findViewById(R.id.txt_stylist_color2);
        txt_stylist_lc1 = findViewById(R.id.txt_stylist_lc1);
        txt_stylist_lc2 = findViewById(R.id.txt_stylist_lc2);

        img_stylist1.setImageResource(R.drawable.o_black);
        img_stylist2.setImageResource(R.drawable.pants_brown);

        txt_stylist_weather.setText("Spring");

        txt_stylist_color1.setText("black");
        txt_stylist_color2.setText("Brown");

        txt_stylist_lc1.setText("4번");
        txt_stylist_lc2.setText("11번");

        btn_stylist_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_stylist1.setImageResource(R.drawable.s_blue);
                img_stylist2.setImageResource(R.drawable.denim_blue);

                txt_stylist_weather.setText("Spring");

                txt_stylist_color1.setText("Blue");
                txt_stylist_color2.setText("Blue");

                txt_stylist_lc1.setText("7번");
                txt_stylist_lc2.setText("10번");
            }
        });

        btn_stylist_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(stylistActivity.this, "코디가 저장되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}