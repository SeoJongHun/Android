package com.example.filebase_viewcloset.Clothes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filebase_viewcloset.R;
import com.example.filebase_viewcloset.VO.ClothesVO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.internal.StorageReferenceUri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClothesFragment extends Fragment {
    ImageView clothes_img;
    Button btn_list_delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_clothes, container, false);

        clothes_img = view.findViewById(R.id.clothes_img);
        btn_list_delete = view.findViewById(R.id.btn_list_delete);


        RecyclerView recyclerView = view.findViewById(R.id.list_clothes);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);


        ProductAdapter adapter = new ProductAdapter();

        adapter.addItem(new ClothesVO(R.drawable.t_red,"Red","1번","T_shirt"));
        adapter.addItem(new ClothesVO(R.drawable.s_white,"White","2번","Shirt"));
        adapter.addItem(new ClothesVO(R.drawable.o_green,"Green","3번","Outer"));
        adapter.addItem(new ClothesVO(R.drawable.o_black,"Black","4번","Outer"));
        adapter.addItem(new ClothesVO(R.drawable.l_yellow,"Yellow","5번","Sweatshirt"));
        adapter.addItem(new ClothesVO(R.drawable.l_brown,"Brown","6번","knit"));
        adapter.addItem(new ClothesVO(R.drawable.s_blue,"Blue","7번","Shirt"));
        adapter.addItem(new ClothesVO(R.drawable.black_jacket,"Black","8번","Outer"));
        adapter.addItem(new ClothesVO(R.drawable.black_outer,"Black","9번","Outer"));
        adapter.addItem(new ClothesVO(R.drawable.denim_blue,"Blue","10번","Denim"));
        adapter.addItem(new ClothesVO(R.drawable.pants_brown,"Brown","11번","Pants"));


        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnProductItemClickListener() {
            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {

                ClothesVO vo = adapter.getClothesVO(position);
                String color = vo.getClothes_color();
                int img = vo.getClothes_img();
                String location = vo.getClothes_location();
                String type = vo.getClothes_type();

                Intent intent = new Intent(getActivity(), ClothesActivity.class);

                intent.putExtra("color",color);
                intent.putExtra("location",location);
                intent.putExtra("type",type);
                intent.putExtra("img",img);

                startActivity(intent);

                Toast.makeText(getActivity(), location+" 옷을 선택하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });



        int resID = getResources().getIdentifier("ex_clothes2", "drawable", requireActivity().getPackageName());



        return view;


    }


}