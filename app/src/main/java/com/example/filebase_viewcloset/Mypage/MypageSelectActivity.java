package com.example.filebase_viewcloset.Mypage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MypageSelectActivity extends AppCompatActivity {

    TextView txt_sc_id, txt_sc_phone, txt_sc_gender, txt_sc_height, txt_sc_weight, txt_sc_nick;

    static String id;
    static String phone;
    static String gender;
    static String nick;
    static String height;
    static String weight;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "joinpage";




    Button btn_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_select);

        firebaseAuth = FirebaseAuth.getInstance();

        // 회원 정보 버튼
        btn_member = findViewById(R.id.btn_member_update);

        // 회원 정보 아이디
        txt_sc_id = findViewById(R.id.txt_my_id);
        txt_sc_nick = findViewById(R.id.txt_my_nick);
        txt_sc_phone = findViewById(R.id.txt_my_phone);
        txt_sc_gender = findViewById(R.id.txt_my_gender);
        txt_sc_height = findViewById(R.id.txt_my_height);
        txt_sc_weight = findViewById(R.id.txt_my_weight);

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
                            id = (String)document.getData().get("id");
                            nick = (String)document.getData().get("nick");
                            phone = (String)document.getData().get("phone");
                            gender = (String)document.getData().get("gender");
                            height = (String)document.getData().get("height");
                            weight = (String)document.getData().get("weight");

                            txt_sc_id.setText(id);
                            txt_sc_nick.setText(nick);
                            txt_sc_phone.setText(phone);
                            txt_sc_gender.setText(gender);
                            txt_sc_height.setText(height);
                            txt_sc_weight.setText(weight);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



//               docRef.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                id = (String)document.getData().get("id");
//                                nick = (String)document.getData().get("nick");
//                                phone = (String)document.getData().get("phone");
//                                gender = (String)document.getData().get("gender");
//                                height = (String)document.getData().get("height");
//                                weight = (String)document.getData().get("weight");
//
//                                txt_sc_id.setText(id);
//                                txt_sc_nick.setText(nick);
//                                txt_sc_phone.setText(phone);
//                                txt_sc_gender.setText(gender);
//                                txt_sc_height.setText(height);
//                                txt_sc_weight.setText(weight);
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });






//        String db_url = "https://viewcloset-7c705-default-rtdb.firebaseio.com/";
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
//        DatabaseReference dbRef = database.getReference("join");
//
//
//        dbRef.child("id").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                id = snapshot.getValue(String.class);
//                txt_sc_id.setText(id);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        dbRef.child("phone").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                phone = snapshot.getValue(String.class);
//                txt_sc_phone.setText(phone);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        dbRef.child("gender").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                gender = snapshot.getValue(String.class);
//                txt_sc_gender.setText(gender);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        dbRef.child("nick").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                nick = snapshot.getValue(String.class);
//                txt_sc_nick.setText(nick);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        dbRef.child("height").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                height = snapshot.getValue(String.class);
//                txt_sc_height.setText(height+"Cm");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        dbRef.child("weight").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                weight = snapshot.getValue(String.class);
//                txt_sc_weight.setText(weight+"Kg");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        btn_member.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MypageUpdateActivity.class);
//                startActivity(intent);
//            }
//        });

    }

}