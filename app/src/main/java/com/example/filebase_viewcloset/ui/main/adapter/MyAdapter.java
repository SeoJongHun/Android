package com.example.filebase_viewcloset.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.filebase_viewcloset.C_Fragment.C_Fragment_1;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_10;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_2;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_3;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_4;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_5;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_6;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_7;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_8;
import com.example.filebase_viewcloset.C_Fragment.C_Fragment_9;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;

    public MyAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new C_Fragment_1();
        else if(index==1) return new C_Fragment_2();
        else if(index==2) return new C_Fragment_3();
        else if(index==3) return new C_Fragment_4();
        else if(index==4) return new C_Fragment_5();
        else if(index==5) return new C_Fragment_6();
        else if(index==6) return new C_Fragment_7();
        else if(index==7) return new C_Fragment_8();
        else if(index==8) return new C_Fragment_9();
        else return new C_Fragment_10();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }

}