package com.example.filebase_viewcloset.Clothes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filebase_viewcloset.R;
import com.example.filebase_viewcloset.VO.ClothesVO;


import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements OnProductItemClickListener{
    ArrayList<ClothesVO> items = new ArrayList<>();
    OnProductItemClickListener listener;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_clothes, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       ClothesVO vo = items.get(position);
        holder.setItem(vo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(ClothesVO vo){
        items.add(vo);
    }

    public ClothesVO getClothesVO(int position){

        return items.get(position);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }
    public void setOnItemClickListener(OnProductItemClickListener listener){
        this.listener = listener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textName;
        TextView textcolor;
        TextView textlocation;
        Button btn_list_delete;




        public ViewHolder(View itemView, final OnProductItemClickListener listener){
            super(itemView);
            imageView = itemView.findViewById(R.id.clothes_img);
            textName = itemView.findViewById(R.id.textName);
            textcolor = itemView.findViewById(R.id.txt_clothes_color);
            textlocation = itemView.findViewById(R.id.txt_clothes_location);
            btn_list_delete = itemView.findViewById(R.id.btn_list_delete);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){   //onItemClick 호출
                        listener.onItemClick(ViewHolder.this, view,position);
                    }
                }
            });
        }

        public void setItem(ClothesVO vo){
            textName.setText(vo.getClothes_type());
            textlocation.setText(vo.getClothes_location());
            textcolor.setText(vo.getClothes_color());
            imageView.setImageResource(vo.getClothes_img());
        }


    }
}
