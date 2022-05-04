package com.example.filebase_viewcloset.Claendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.filebase_viewcloset.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class CalendarFragment extends Fragment {

    TextView txt_calendar_view, txt_calendar_context, txt_calendar_title;
    EditText edt_calendar_context,edt_calendar_title;
    Button btn_calendar_reset, btn_calendar_add, btn_calendar_update;
    CalendarView calendarView;
    String context = "";
    String title = "";

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "joinpage";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        //캘린더 버튼
        btn_calendar_reset = view.findViewById(R.id.btn_calendar_reset);
        btn_calendar_add = view.findViewById(R.id.btn_calendar_add);
        btn_calendar_update = view.findViewById(R.id.btn_calendar_update);
        // 캘린더 제목, 내용
        edt_calendar_context = view.findViewById(R.id.edt_calendar_context);
        edt_calendar_title = view.findViewById(R.id.edt_calendar_title);
        txt_calendar_context = view.findViewById(R.id.txt_calendar_context);
        txt_calendar_title = view.findViewById(R.id.txt_calendar_title);

        // 캘린더 날짜 표기
        txt_calendar_view = view.findViewById(R.id.txt_calendar_view);

        //캘린더
        calendarView = view.findViewById(R.id.calendarView);

        //버튼 숨기기
        btn_calendar_reset.setVisibility(View.INVISIBLE);
        btn_calendar_update.setVisibility(View.INVISIBLE);

        //텍스트 숨기기
        txt_calendar_title.setVisibility(View.INVISIBLE);
        txt_calendar_context.setVisibility(View.INVISIBLE);

        firebaseAuth =  FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();




        //캘린더 클릭 시
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                btn_calendar_add.setVisibility(View.VISIBLE);

                edt_calendar_context.setText("");
                edt_calendar_title.setText("");

                edt_calendar_title.setVisibility(View.VISIBLE);
                edt_calendar_context.setVisibility(View.VISIBLE);

                txt_calendar_view.setText(String.format("%d/%d/%d", year, month+1,day));



            }
        });
        //저장버튼
        btn_calendar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = edt_calendar_context.getText().toString();
                title = edt_calendar_title.getText().toString();
                txt_calendar_context.setText(context);
                txt_calendar_title.setText(title);

                btn_calendar_add.setVisibility(View.INVISIBLE);
                btn_calendar_update.setVisibility(View.VISIBLE);
                btn_calendar_reset.setVisibility(View.VISIBLE);

                edt_calendar_context.setVisibility(View.INVISIBLE);
                edt_calendar_title.setVisibility(View.INVISIBLE);

                txt_calendar_context.setVisibility(View.VISIBLE);
                txt_calendar_title.setVisibility(View.VISIBLE);


                Map<String, Object> Board = new HashMap<>();
                Board.put("calendar",txt_calendar_view.getText().toString());
                Board.put("title",edt_calendar_title.getText().toString());
                Board.put("content",edt_calendar_context.getText().toString());

                db.collection("view_closet")
                        .document("member")
                        .collection("id")
                        .document(uid)
                        .collection("calendar")
                        .document("content")
                        .set(Board)
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

                Toast.makeText(getActivity(),"저장되었습니다.",Toast.LENGTH_SHORT).show();

                // 수정 버튼
                btn_calendar_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        edt_calendar_title.setVisibility(View.VISIBLE);
                        edt_calendar_context.setVisibility(View.VISIBLE);

                        txt_calendar_title.setVisibility(View.INVISIBLE);
                        txt_calendar_context.setVisibility(View.INVISIBLE);

                        edt_calendar_title.setText(title);
                        edt_calendar_context.setText(context);

                        btn_calendar_add.setVisibility(View.VISIBLE);
                        btn_calendar_reset.setVisibility(View.INVISIBLE);
                        btn_calendar_update.setVisibility(View.INVISIBLE);

                        txt_calendar_context.setText(edt_calendar_context.getText());
                        txt_calendar_title.setText(edt_calendar_title.getText());

                        Toast.makeText(getActivity(),"수장되었습니다.",Toast.LENGTH_SHORT).show();

                    }
                });
                //초기화 버튼
                btn_calendar_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        txt_calendar_context.setVisibility(View.INVISIBLE);
                        txt_calendar_title.setVisibility(View.INVISIBLE);

                        txt_calendar_view.setText("");
                        edt_calendar_context.setText("");
                        edt_calendar_title.setText("");

                        edt_calendar_context.setVisibility(View.VISIBLE);
                        edt_calendar_title.setVisibility(View.VISIBLE);

                        btn_calendar_add.setVisibility(View.INVISIBLE);
                        btn_calendar_reset.setVisibility(View.VISIBLE);
                        btn_calendar_update.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity(),"초기화되었습니다.",Toast.LENGTH_SHORT).show();

                    }
                });




                if (txt_calendar_context.getText() == null || txt_calendar_title.getText() == null){

                    Toast.makeText(getActivity(), "빈칸을 확인해주세요", Toast.LENGTH_SHORT).show();
                }


            }
        });









        return view;
    }
}