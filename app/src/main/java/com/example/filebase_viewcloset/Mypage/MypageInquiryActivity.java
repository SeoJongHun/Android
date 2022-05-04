package com.example.filebase_viewcloset.Mypage;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.filebase_viewcloset.MainActivity;
import com.example.filebase_viewcloset.R;

import java.util.HashMap;
import java.util.Map;

public class MypageInquiryActivity extends AppCompatActivity {
    CheckBox inquiry_clothes, inquiry_my, inquiry_other, inquiry_style, inquiry_home, inquiry_calendar;
    EditText edt_inquiry_other, edt_inquiry_content, edt_inquiry_email;
    Button btn_inquiry_shot, btn_inquiry_reset;
    String check_clothes, check_my, check_other, check_home, check_style, check_calendar;
    TextView txt_inquiry_result;
    String result1, result2, result3, result4, result5,result6;

    RequestQueue requestQueue ;
    Request request;

    MypageFragment mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_inquiry);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        mypage = new MypageFragment();

        // 문의하기 체크박스
        inquiry_clothes = findViewById(R.id.inquiry_clothes);
        inquiry_my = findViewById(R.id.inquiry_my);
        inquiry_other = findViewById(R.id.inquiry_other);
        inquiry_home = findViewById(R.id.inquiry_home);
        inquiry_calendar = findViewById(R.id.inquiry_calendar);
        inquiry_style = findViewById(R.id.inquriy_style);

        // 문의하기 edt
        edt_inquiry_content = findViewById(R.id.edt_inquiry_content);
        edt_inquiry_other = findViewById(R.id.edt_inquiry_other);
        edt_inquiry_email = findViewById(R.id.edt_inquiry_email);

        // 문의하기 btn
        btn_inquiry_shot = findViewById(R.id.btn_inquiry_shot);
        btn_inquiry_reset = findViewById(R.id.btn_inquiry_reset);

        //체크박스 값
        txt_inquiry_result = findViewById(R.id.txt_inquiry_result);

        // 리셋 버튼
        btn_inquiry_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //체크박스
                inquiry_home.setChecked(false);
                inquiry_my.setChecked(false);
                inquiry_calendar.setChecked(false);
                inquiry_style.setChecked(false);
                inquiry_other.setChecked(false);
                inquiry_clothes.setChecked(false);
                //EDT
                edt_inquiry_content.setText("");
                edt_inquiry_email.setText("");
                edt_inquiry_other.setText("");

                Toast.makeText(getApplicationContext(), "초기화되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        txt_inquiry_result.setVisibility(View.INVISIBLE);

        //제출 버튼
        btn_inquiry_shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //초기값
                String result = "";
                // 입력값
                String edt_content = edt_inquiry_content.getText().toString();
                String edt_other = edt_inquiry_other.getText().toString();
                String edt_email = edt_inquiry_email.getText().toString();
                //체크 박스
                if (inquiry_clothes.isChecked()) {
                    result += inquiry_clothes.getText().toString();
                }
                if (inquiry_home.isChecked()) {
                    result += inquiry_home.getText().toString();
                }
                if (inquiry_style.isChecked()) {
                    result += inquiry_style.getText().toString();
                }
                if (inquiry_calendar.isChecked()) {
                    result += inquiry_calendar.getText().toString();
                }
                if (inquiry_my.isChecked()) {
                    result += inquiry_my.getText().toString();
                }
                if (inquiry_other.isChecked()) {
                    result += inquiry_other.getText().toString();
                }
                txt_inquiry_result.setText(result);

                String inquiry_check = txt_inquiry_result.getText().toString();

                String url = "http://172.30.1.30:8090/AndroidServer/InquiryController";

                request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("1")){
                                    Toast.makeText(getApplicationContext(),"문의 접수가 되었습니다.", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "문의가 접수가 되었습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MypageInquiryActivity.this, MainActivity.class);
                                startActivity(intent);

                                Log.d("check", error.toString());
                            }
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                         //체크박스
                        params.put("inquiry_check", inquiry_check);
                        // 문의 란
                        params.put("inquiry_edt_other", edt_other);
                        params.put("inquiry_edt_content", edt_content);
                        params.put("inquiry_edt_email", edt_email);

                        return params;

                    }
                }; // request 종료 부분
                requestQueue.add(request);

            }
        });


    }


}