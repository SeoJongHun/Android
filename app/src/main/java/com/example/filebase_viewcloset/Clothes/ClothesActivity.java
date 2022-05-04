package com.example.filebase_viewcloset.Clothes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filebase_viewcloset.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ClothesActivity extends AppCompatActivity {

    TextView txt_select_color, txt_select_location, txt_select_type;
    ImageView select_img;
    int img;
    //계절
    CheckBox check_clothes_spring, check_clothes_summer, check_clothes_winter, check_clothes_fall;
    //TPO
    CheckBox check_daily, check_meeting, check_promise, check_work, check_travel, check_sports, check_rest, check_others;

    Button btn_clothes_join, btn_clothes_reset;
    //계절
    String spring, summer, fall, winter;
    //TPO
    String daily, meeting, promise, work, travel, sports, rest, others;

    String clothes_color, clothes_location;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "joinpage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

//        //firebase
//        String db_url = "https://viewcloset-7c705-default-rtdb.firebaseio.com/";
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
//        DatabaseReference dbRef = database.getReference("join");
//        //옷 정보
//        DatabaseReference clothes_color= dbRef.child("color");
//        DatabaseReference clothes_location= dbRef.child("location");
//
//
//        //계절
//        DatabaseReference springf= dbRef.child("spring");
//        DatabaseReference summerf = dbRef.child("summer");
//        DatabaseReference fallf = dbRef.child("fall");
//        DatabaseReference winterf = dbRef.child("winter");
//
//        //TPO
//        DatabaseReference dailyf = dbRef.child("daily");
//        DatabaseReference meetingf = dbRef.child("meeting");
//        DatabaseReference promisef = dbRef.child("promise");
//        DatabaseReference workf = dbRef.child("work");
//        DatabaseReference travelf = dbRef.child("travel");
//        DatabaseReference sportsf = dbRef.child("sports");
//        DatabaseReference othersf = dbRef.child("other");
//        DatabaseReference restf = dbRef.child("rest");


        firebaseAuth = FirebaseAuth.getInstance();
        //어뎁터에서 가져오는 값
        txt_select_color = findViewById(R.id.txt_select_color);
        txt_select_location = findViewById(R.id.txt_select_location);
        txt_select_type = findViewById(R.id.txt_select_type);
        select_img = findViewById(R.id.select_img);

        //사용자가 설정하는 값
        //계절
        check_clothes_spring = findViewById(R.id.check_clothes_spring);
        check_clothes_summer = findViewById(R.id.check_clothes_summer);
        check_clothes_fall = findViewById(R.id.check_clothes_fall);
        check_clothes_winter = findViewById(R.id.check_clothes_winter);

        //TPO
        check_daily = findViewById(R.id.check_daily);
        check_meeting = findViewById(R.id.check_meeting);
        check_promise = findViewById(R.id.check_promise);
        check_work = findViewById(R.id.check_work);
        check_travel = findViewById(R.id.check_travel);
        check_sports = findViewById(R.id.check_sports);
        check_rest = findViewById(R.id.check_rest);
        check_others = findViewById(R.id.check_others);

        //버튼
        btn_clothes_join = findViewById(R.id.btn_clothes_join);
        btn_clothes_reset = findViewById(R.id.btn_clothes_reset);

        //어뎁터에서 가져온 값 담는 부분
        Intent intent = getIntent();
        txt_select_color.setText(intent.getStringExtra("color"));
        txt_select_location.setText(intent.getStringExtra("location"));
        txt_select_type.setText(intent.getStringExtra("type"));
        select_img.setImageResource(intent.getIntExtra("img", img));

        //계절
        //봄
        check_clothes_spring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_clothes_spring.isChecked()) {
                    spring = check_clothes_spring.getText().toString();
                    Log.v("spring", spring);
                } else {

                }
            }
        });
        //여름
        check_clothes_summer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_clothes_summer.isChecked()) {
                    summer = check_clothes_summer.getText().toString();
                    Log.v("summer", summer);
                }
            }
        });
        //가을
        check_clothes_fall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_clothes_fall.isChecked()) {
                    fall = check_clothes_fall.getText().toString();
                    Log.v("fall", fall);
                }
            }
        });
        //겨울
        check_clothes_winter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_clothes_winter.isChecked()) {
                    winter = check_clothes_winter.getText().toString();
                    Log.v("winter", winter);
                }
            }
        });


        //TPO
        //일상
        check_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_daily.isChecked()) {
                    daily = check_daily.getText().toString();
                    Log.v("daily", daily);
                } else {

                }
            }
        });
        //여행
        check_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_travel.isChecked()) {
                    travel = check_travel.getText().toString();
                    Log.v("travel", travel);
                } else {

                }
            }
        });
        //운동
        check_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_sports.isChecked()) {
                    sports = check_sports.getText().toString();
                    Log.v("sports", sports);
                } else {

                }
            }
        });
        //휴식
        check_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_rest.isChecked()) {
                    rest = check_rest.getText().toString();
                    Log.v("rest", rest);
                } else {

                }
            }
        });
        //모임
        check_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_meeting.isChecked()) {
                    meeting = check_meeting.getText().toString();
                    Log.v("meeting", meeting);
                } else {

                }
            }
        });
        //업무
        check_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_work.isChecked()) {
                    work = check_work.getText().toString();
                    Log.v("work", work);
                } else {

                }
            }
        });
        //약속
        check_promise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_promise.isChecked()) {
                    promise = check_promise.getText().toString();
                    Log.v("promise", promise);
                } else {

                }
            }
        });
        //기타
        check_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_others.isChecked()) {
                    others = check_others.getText().toString();
                    Log.v("others", others);
                } else {

                }
            }
        });

        // 초기화 버튼
        btn_clothes_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //계절
                check_clothes_spring.setChecked(false);
                check_clothes_summer.setChecked(false);
                check_clothes_fall.setChecked(false);
                check_clothes_winter.setChecked(false);

                //TPO
                check_daily.setChecked(false);
                check_travel.setChecked(false);
                check_sports.setChecked(false);
                check_rest.setChecked(false);
                check_meeting.setChecked(false);
                check_work.setChecked(false);
                check_promise.setChecked(false);
                check_others.setChecked(false);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();


                Map<String, Object> Board = new HashMap<>();
                //계절
                Board.put("spring", "");
                Board.put("summer", "");
                Board.put("fall", "");
                Board.put("winter", "");
                //TPO
                Board.put("daily", "");
                Board.put("travel", "");
                Board.put("sports", "");
                Board.put("rest", "");
                Board.put("meeting", "");
                Board.put("work", "");
                Board.put("promise", "");
                Board.put("other", "");
                Board.put("daily", "");
                Board.put("daily", "");




                Toast.makeText(getApplicationContext(), "초기화되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        clothes_color = txt_select_color.getText().toString();
        clothes_location = txt_select_location.getText().toString();


        // 등록 버튼
        btn_clothes_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();

                if (clothes_location.equals("1번")) {
                    Map<String, Object> Board1 = new HashMap<>();
                    //옷정보
                    Board1.put("color", clothes_color);
                    Board1.put("location", clothes_location);
                    //계절
                    Board1.put("spring", spring);
                    Board1.put("summer", summer);
                    Board1.put("fall", fall);
                    Board1.put("winter", winter);
                    //TPO
                    Board1.put("daily", daily);
                    Board1.put("travel", travel);
                    Board1.put("sports", sports);
                    Board1.put("rest", rest);
                    Board1.put("meeting", meeting);
                    Board1.put("work", work);
                    Board1.put("promise", promise);
                    Board1.put("other", others);
                    Board1.put("daily", daily);
                    Board1.put("daily", daily);

                    db.collection("view_closet")
                            .document("member")
                            .collection("id")
                            .document(uid)
                            .collection("clothes")
                            .document("1번")
                            .set(Board1)
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
                }


                if (clothes_location.equals("2번")) {
                    Map<String, Object> Board2 = new HashMap<>();
                    //옷정보
                    Board2.put("color", clothes_color);
                    Board2.put("location", clothes_location);
                    //계절
                    Board2.put("spring", spring);
                    Board2.put("summer", summer);
                    Board2.put("fall", fall);
                    Board2.put("winter", winter);
                    //TPO
                    Board2.put("daily", daily);
                    Board2.put("travel", travel);
                    Board2.put("sports", sports);
                    Board2.put("rest", rest);
                    Board2.put("meeting", meeting);
                    Board2.put("work", work);
                    Board2.put("promise", promise);
                    Board2.put("other", others);



                    db.collection("view_closet")
                            .document("member")
                            .collection("id")
                            .document(uid)
                            .collection("clothes")
                            .document("2번")
                            .set(Board2)
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
                }


                if (clothes_location.equals("3번")) {
                    Map<String, Object> Board3 = new HashMap<>();
                    //옷정보
                    Board3.put("color", clothes_color);
                    Board3.put("location", clothes_location);
                    //계절
                    Board3.put("spring", spring);
                    Board3.put("summer", summer);
                    Board3.put("fall", fall);
                    Board3.put("winter", winter);
                    //TPO
                    Board3.put("daily", daily);
                    Board3.put("travel", travel);
                    Board3.put("sports", sports);
                    Board3.put("rest", rest);
                    Board3.put("meeting", meeting);
                    Board3.put("work", work);
                    Board3.put("promise", promise);
                    Board3.put("other", others);

                    db.collection("view_closet")
                            .document("member")
                            .collection("id")
                            .document(uid)
                            .collection("clothes")
                            .document("3번")
                            .set(Board3)
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
                }

//                Log.v("add",spring);
//                //옷정보
//

//
//                //계절
//                springf.setValue(spring);
//                summerf.setValue(summer);
//                fallf.setValue(fall);
//                winterf.setValue(winter);
//                //TPO
//                dailyf.setValue(daily);
//                travelf.setValue(travel);
//                sportsf.setValue(sports);
//                restf.setValue(rest);
//                meetingf.setValue(meeting);
//                workf.setValue(work);
//                promisef.setValue(promise);
//                othersf.setValue(others);

                Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

    }
}