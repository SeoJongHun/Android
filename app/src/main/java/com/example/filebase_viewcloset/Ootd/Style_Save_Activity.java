package com.example.filebase_viewcloset.Ootd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.filebase_viewcloset.R;
import com.example.filebase_viewcloset.VO.S_Save_ContentVO;
import com.example.filebase_viewcloset.ui.main.adapter.Style_Save_Adapter;

import java.util.ArrayList;

public class Style_Save_Activity extends AppCompatActivity {

    ListView style_save_list_view;
    ArrayList<S_Save_ContentVO> dataset;
    Style_Save_Adapter s_apater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_save);

        style_save_list_view = findViewById(R.id.list_clothes);
        dataset = new ArrayList<>();

        // 데이터 추가
        dataset.add(new S_Save_ContentVO(R.drawable.o_black, R.drawable.pants_brown, "Black", "Blue"));
        dataset.add(new S_Save_ContentVO(R.drawable.s_blue, R.drawable.denim_blue, "Blue", "Blue"));
        dataset.add(new S_Save_ContentVO(R.drawable.t_red, R.drawable.denim_blue, "Red", "Blue"));
        dataset.add(new S_Save_ContentVO(R.drawable.s_white, R.drawable.pants_brown, "White", "Brown"));

        s_apater = new Style_Save_Adapter(getApplicationContext(),R.layout.style_save_list, dataset);

        style_save_list_view.setAdapter(s_apater);
    }
}