package com.example.filebase_viewcloset.Mypage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filebase_viewcloset.JoinActivity;
import com.example.filebase_viewcloset.LoginActivity;
import com.example.filebase_viewcloset.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MypageUpdateActivity extends AppCompatActivity {

    Button btn_update, btn_update_member, btn_update_check;
    EditText edt_update_pw, edt_update_nick, edt_update_phone, edt_update_weight, edt_update_height, edt_update_id;
    RadioGroup radio;
    TextView txt_update_gender, txt_update_id;
    String id;

    RequestQueue requestQueue;
    StringRequest request;


    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "joinpage";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_update);

        // 회원 수정 버튼
        btn_update = findViewById(R.id.btn_update);


        // 아이디 체크
        txt_update_id = findViewById(R.id.txt_update_id);

        // 라디오 텍스트
        txt_update_gender = findViewById(R.id.txt_update_gender);


        // 회원 수정 edt
        edt_update_nick = findViewById(R.id.edt_update_nick);
        edt_update_phone = findViewById(R.id.edt_update_phone);
        edt_update_weight = findViewById(R.id.edt_update_w);
        edt_update_height = findViewById(R.id.edt_update_h);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        // 회원 수정 radio
        radio = findViewById(R.id.radiogroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rb_u_m:
                        txt_update_gender.setText("남자");
                        break;
                    case R.id.rb_u_w:
                        txt_update_gender.setText("여자");
                        break;
                }
            }
        });

        txt_update_gender.setVisibility(View.INVISIBLE);

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

                        txt_update_id.setText(id);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String update_id = txt_update_id.getText().toString();
                String update_nick = edt_update_nick.getText().toString();
                String update_phone = edt_update_phone.getText().toString();
                String update_weight = edt_update_weight.getText().toString();
                String update_height = edt_update_height.getText().toString();
                String update_gender = txt_update_gender.getText().toString();

                HashMap<Object,String>hashMap = new HashMap<>();

                hashMap.put("id",update_id);
                hashMap.put("nick",update_nick);
                hashMap.put("phone",update_phone);
                hashMap.put("height",update_height);
                hashMap.put("weight",update_weight);
                hashMap.put("gender",update_gender);




                db.collection("view_closet")
                        .document("member")
                        .collection("id")
                        .document(uid)
                        .set(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");

                                ProgressDialog mDialog = new ProgressDialog(MypageUpdateActivity.this);
                                mDialog.setMessage("잠시만 기다려주세요");
                                mDialog.show();

                                Toast.makeText(getApplicationContext(),"회원정보 수정이 완료되었습니다.",Toast.LENGTH_SHORT);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        });

//        btn_update_check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String m_id = edt_update_id.getText().toString();
//
//                String url = "http://172.30.1.30:8090/AndroidServer/CheckController";
//
//                request = new StringRequest(
//                        Request.Method.POST,
//                        url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // 성공시
//                                if (response.equals("null")) {   // 아이디 중복체크 성공시
//                                    Toast.makeText(MypageUpdateActivity.this,"아이디가 같지 않습니다.",Toast.LENGTH_SHORT).show();
//                                } else {  // 아이디가 중복 되었을 시
//                                    Toast.makeText(MypageUpdateActivity.this, "아이디 확인", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // 통신 실패시
//                                Toast.makeText(MypageUpdateActivity.this, "통신 실패!", Toast.LENGTH_SHORT).show();
//                                Log.d("check", error.toString());
//
//                            }
//                        }
//
//                ) {
//                    @Nullable
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//
//                        Map<String, String> params = new HashMap<>();
//
//                        params.put("check_id", m_id);
//
//                        return params;
//
//                    }
//
//                };
//                requestQueue.add(request);
//
//            }
//        });


        // 회원 수정 완료 버튼
//        btn_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String m_id = edt_update_id.getText().toString();
//                String m_pw = edt_update_pw.getText().toString();
//                String m_nick = edt_update_nick.getText().toString();
//                String m_phone = edt_update_phone.getText().toString();
//                String m_gender = txt_update_gender.getText().toString();
//                String m_weight = edt_update_weight.getText().toString();
//                String m_height = edt_update_height.getText().toString();
//
//                String url = "http://172.30.1.30:8090/AndroidServer/UpdateController";
//
//
//
//                request = new StringRequest(
//                        Request.Method.POST,
//                        url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//
//                                Log.d("check", response+"");
//
//                                // 성공시
//                                if (response.equals("1")) {    //  회원수정 성공시
//                                    Toast.makeText(MypageUpdateActivity.this, "회원정보 수정완료", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                    Log.v("m_id", m_id);
//                                    Log.v("m_pw", m_pw);
//                                    Log.v("m_nick", m_nick);
//                                    Log.v("m_phone", m_phone);
//                                    Log.v("m_height", m_height);
//                                    Log.v("m_weight", m_weight);
//                                    Log.v("m_gender", m_gender);
//                                    Log.v("response", response);
//                                    Log.e("error", response);
//                                    startActivity(intent);
//                                    finish();
//                                } else {  // 회원수정 실패시
//                                    Toast.makeText(MypageUpdateActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // 실패시
//                                Toast.makeText(MypageUpdateActivity.this, "통신 실패!", Toast.LENGTH_SHORT).show();
//                                Log.d("check", error.toString());
//
//                            }
//                        }
//
//                ) {
//                    @Nullable
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//
//                        Map<String, String> params = new HashMap<>();
//
//                        params.put("update_pw", m_pw);
//                        params.put("update_phone", m_phone);
//                        params.put("update_gender", m_gender);
//                        params.put("update_nick", m_nick);
//                        params.put("update_height", m_height);
//                        params.put("update_weight", m_weight);
//                        params.put("update_id", m_id);
//
//                        return params;
//                    }
//
//                }; // request 종료 부분
//                requestQueue.add(request);
//            }
//        });
//
//        // 회원 정보 페이지 이동
//        btn_update_member.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MypageSelectActivity.class);
//                startActivity(intent);
//
//
//            }
//        });


    }
}