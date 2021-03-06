package com.example.filebase_viewcloset.Mypage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.filebase_viewcloset.LoginActivity;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class MypageDeleteActivity extends AppCompatActivity {

    Button btn_delete;
    EditText edt_delete_id, edt_delete_pw;

    String id = "";
    String pw = "";

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "joinpage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_delete);

        btn_delete = findViewById(R.id.btn_delete);
        edt_delete_id = findViewById(R.id.edit_delete_id);
        edt_delete_pw = findViewById(R.id.edit_delete_pw);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MypageDeleteActivity.this)
                        .setTitle("?????? ??????")
                        .setMessage("????????? ?????????????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            // ??????
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Log.d("????????????",  id + ","+pw);
                                ProgressDialog mDialog = new ProgressDialog(MypageDeleteActivity.this);
                                mDialog.setMessage("????????? ??????????????????");
                                mDialog.show();

                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User account deleted.");
                                                    Toast.makeText(getApplicationContext(),"????????? ?????????????????????.",Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                Intent intent = new Intent(MypageDeleteActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(MypageDeleteActivity.this, "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            // ??????
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MypageDeleteActivity.this, "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();

            }
        });







//        String db_url = "https://viewcloset-7c705-default-rtdb.firebaseio.com/";
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
//        DatabaseReference dbRef = database.getReference("join");
//
//
//
//        // ?????? ??????
//        btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                id = edt_delete_id.getText().toString();
//                pw = edt_delete_pw.getText().toString();
//
//                dbRef.child("id").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        id = snapshot.getValue(String.class);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                dbRef.child("pw").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        pw = snapshot.getValue(String.class);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MypageDeleteActivity.this)
//                        .setTitle("?????? ??????")
//                        .setMessage("????????? ?????????????????????????")
//                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                            @Override
//                            // ??????
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                if(id.equals(id) && pw.equals(pw)){
//
//                                    Log.d("????????????",  id + ","+pw);
//                                    ProgressDialog mDialog = new ProgressDialog(MypageDeleteActivity.this);
//                                    mDialog.setMessage("????????? ??????????????????");
//                                    mDialog.show();
//
//                                    dbRef.removeValue();
//
//                                     Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(MypageDeleteActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                }
//                                Toast.makeText(MypageDeleteActivity.this, "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
//                            @Override
//                            // ??????
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(MypageDeleteActivity.this, "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                AlertDialog msgDlg = msgBuilder.create();
//                msgDlg.show();


    }

}