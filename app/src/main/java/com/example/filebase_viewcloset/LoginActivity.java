package com.example.filebase_viewcloset;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filebase_viewcloset.Mypage.MypageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    EditText edt_id, edt_pw;
    Button btn_login, btn_join;
    MypageFragment mypage;
    RequestQueue requestQueue;
    String db_id = "";
    String db_pw = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mypage = new MypageFragment();

        firebaseAuth =  FirebaseAuth.getInstance();

        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);

        btn_join = findViewById(R.id.btn_join);
        btn_login = findViewById(R.id.btn_login);

//        requestQueue = Volley.newRequestQueue(getApplicationContext());

//        String db_url = "https://viewcloset-7c705-default-rtdb.firebaseio.com/";
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
//        DatabaseReference dbRef = database.getReference("join");

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edt_id.getText().toString().trim();
                String pw = edt_pw.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(id,pw)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                                    mDialog.setMessage("잠시만 기다려주세요");
                                    mDialog.show();
                                    Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(),"아이디랑 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });





//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String id = edt_id.getText().toString();
//                String pw = edt_pw.getText().toString();
//
//                dbRef.child("id").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        db_id = snapshot.getValue(String.class);
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });
//                // PW
//                dbRef.child("pw").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        db_pw = snapshot.getValue(String.class);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                if(id.equals(db_id) && pw.equals(db_pw)){
//                    Log.d("등록버튼",  db_id + ","+db_pw);
//                    ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
//                    mDialog.setMessage("잠시만 기다려주세요");
//                    mDialog.show();
//
//                    // Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//
//            }
//        });

    }
}