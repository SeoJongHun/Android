package com.example.filebase_viewcloset;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filebase_viewcloset.VO.MemberVO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    RadioGroup radio;
    Button btn_reset, btn_join_s;
    EditText join_id, join_pw, join_nick, join_phone, join_height, join_weight;
    TextView join_gender;
    RequestQueue requestQueue;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "joinpage";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join);

        join_id = findViewById(R.id.join_id);
        join_pw = findViewById(R.id.join_pw);
        join_nick = findViewById(R.id.join_nick);
        join_phone = findViewById(R.id.join_phone);
        join_height = findViewById(R.id.join_height);
        join_weight = findViewById(R.id.join_weight);
        join_gender = findViewById(R.id.join_gender);

        btn_join_s = findViewById(R.id.btn_join_s);
        btn_reset = findViewById(R.id.btn_reset);


        radio = findViewById(R.id.radio);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_m:
                        join_gender.setText("남자");
                        break;
                    case R.id.rb_w:
                        join_gender.setText("여자");
                        break;
                }
            }
        });
        join_gender.setVisibility(View.INVISIBLE);
        firebaseAuth =  FirebaseAuth.getInstance();

        btn_join_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = join_id.getText().toString().trim();
                String pw = join_pw.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(id,pw).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //가입 성공시
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String uid = user.getUid();
                            String id = join_id.getText().toString().trim();
                            String nick = join_nick.getText().toString().trim();
                            String phone = join_phone.getText().toString().trim();
                            String height = join_height.getText().toString().trim();
                            String weight = join_weight.getText().toString().trim();
                            String gender = join_gender.getText().toString().trim();

                            HashMap<Object,String>hashMap = new HashMap<>();

                            hashMap.put("id",id);
                            hashMap.put("nick",nick);
                            hashMap.put("phone",phone);
                            hashMap.put("height",height);
                            hashMap.put("weight",weight);
                            hashMap.put("gender",gender);

                            db.collection("view_closet")
                                    .document("member")
                                    .collection("id")
                                    .document(uid)
                                    .set(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("User");
                            reference.child(uid).setValue(hashMap);



                            Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(),"회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            ProgressDialog mDialog = new ProgressDialog(JoinActivity.this);
                            mDialog.setMessage("잠시만 기다려주세요");
                            mDialog.show();

                        }else{
                            Toast.makeText(getApplicationContext(),"이미 존재하는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });
//        String db_url = "https://viewcloset-7c705-default-rtdb.firebaseio.com/";
//        // 파이어베이스 설정
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
//        DatabaseReference dbRef = database.getReference("join");
//
//        DatabaseReference id = dbRef.child("id");
//        DatabaseReference pw = dbRef.child("pw");
//        DatabaseReference nick = dbRef.child("nick");
//        DatabaseReference phone = dbRef.child("phone");
//        DatabaseReference gender = dbRef.child("gender");
//        DatabaseReference height = dbRef.child("height");
//        DatabaseReference weight = dbRef.child("weight");




        requestQueue = Volley.newRequestQueue(getApplicationContext());

//        btn_join_s.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                id.setValue(join_id.getText().toString());
//                pw.setValue(join_pw.getText().toString());
//                nick.setValue(join_nick.getText().toString());
//                phone.setValue(join_phone.getText().toString());
//                gender.setValue(join_gender.getText().toString());
//                height.setValue(join_height.getText().toString());
//                weight.setValue(join_weight.getText().toString());
//
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//
//
//            }
//        });
    }
}




