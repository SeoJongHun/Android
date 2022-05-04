package com.example.filebase_viewcloset.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filebase_viewcloset.R;
import com.example.filebase_viewcloset.VO.S_Save_ContentVO;

import java.util.ArrayList;

public class Style_Save_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<S_Save_ContentVO> dataset;

    private LayoutInflater inflater;

    public Style_Save_Adapter(Context context, int layout, ArrayList<S_Save_ContentVO> dataset){
        this.context = context;
        this.layout = layout;
        this.dataset = dataset;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Object getItem(int i) {
        return dataset.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null){
            view = inflater.inflate(layout, viewGroup, false);
        }

        ImageView clothes_img1 = view.findViewById(R.id.clothes_img1);
        ImageView clothes_img2 = view.findViewById(R.id.clothes_img2);
        TextView s_save_color1 = view.findViewById(R.id.s_save_color1);
        TextView s_save_color2 = view.findViewById(R.id.s_save_color2);

        S_Save_ContentVO vo = dataset.get(i);
        clothes_img1.setImageResource(vo.getS_style_img1());
        clothes_img2.setImageResource(vo.getS_style_img2());
        s_save_color1.setText(vo.getS_style_color1());
        s_save_color2.setText(vo.getS_style_color2());

        return view;
    }
}
