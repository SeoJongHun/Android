package com.example.filebase_viewcloset.Mypage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.filebase_viewcloset.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MypageFragment extends Fragment {


    TextView txt_mypage_nick;

    Button btn_mp_update;
    Button btn_mp_delete;
    Button btn_mp_member;
    Button btn_mp_inquriy;
    String nick;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "joinpage";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        //각종 버튼
        btn_mp_member = view.findViewById(R.id.btn_mp_member);
        btn_mp_update = view.findViewById(R.id.btn_mp_update);
        btn_mp_delete = view.findViewById(R.id.btn_mp_delete);
        btn_mp_inquriy = view.findViewById(R.id.btn_mp_inquriy);

        // 닉네임 변경 텍스트
        txt_mypage_nick = view.findViewById(R.id.txt_mypage_nick);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        DocumentReference docRef = db.collection("view_closet")
                .document("member")
                .collection("id")
                .document(uid);


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        nick = (String)document.getData().get("nick");

                        txt_mypage_nick.setText(nick+"님 환영합니다.");

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });







//        String db_url = "https://viewcloset-7c705-default-rtdb.firebaseio.com/";
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
//        DatabaseReference dbRef = database.getReference(uid);
//
//        dbRef.child("nick").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                nick = snapshot.getValue(String.class);
//                txt_mypage_nick.setText(nick+"님 환영합니다.");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //회원 정보
        btn_mp_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MypageSelectActivity.class);
                startActivity(intent);

            }
        });

        //회원 수정
        btn_mp_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MypageUpdateActivity.class);
                startActivity(intent);
            }
        });

        //회원 삭제
        btn_mp_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MypageDeleteActivity.class);
                startActivity(intent);
            }
        });
        // 문의하기
        btn_mp_inquriy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MypageInquiryActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }





}