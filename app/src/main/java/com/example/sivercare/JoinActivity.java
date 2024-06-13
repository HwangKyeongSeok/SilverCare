package com.example.sivercare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {

    private EditText afText1, afText2, afText3, afText4;
    private Button afButtonJoin;
    private DatabaseHelper dbHelper;
    private String staffId, staffPassword, staffPhone, staffEmail, staffName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // UI 컴포넌트 초기화
        afText1 = findViewById(R.id.AFText1);
        afText2 = findViewById(R.id.AFText2);
        afText3 = findViewById(R.id.AFText3);
        afText4 = findViewById(R.id.AFText4);
        afButtonJoin = findViewById(R.id.AFButtonJoin);

        // DatabaseHelper 초기화
        dbHelper = new DatabaseHelper(this);

        // Intent에서 직원 정보 가져오기
        Intent intent = getIntent();
        staffId = intent.getStringExtra("staffId");
        staffPassword = intent.getStringExtra("staffPassword");
        staffPhone = intent.getStringExtra("staffPhone");
        staffEmail = intent.getStringExtra("staffEmail");
        staffName = intent.getStringExtra("staffName");

        // 등록 버튼 리스너 설정
        afButtonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinFacility();
            }
        });
    }

    private void joinFacility() {
        String city = afText1.getText().toString().trim();
        String district = afText2.getText().toString().trim();
        String subdistrict = afText3.getText().toString().trim();
        String name = afText4.getText().toString().trim();

        if (TextUtils.isEmpty(city)) {
            afText1.setError("시를 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(district)) {
            afText2.setError("구/군을 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(subdistrict)) {
            afText3.setError("면/읍/동을 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            afText4.setError("시설이름을 입력해주세요");
            return;
        }

        Facility facility = dbHelper.getFacility(city, district, subdistrict, name);

        if (facility != null) {
            Staff staff = new Staff(staffId, staffPassword, staffPhone, staffEmail, staffName);
            dbHelper.addStaffToFacility(facility.getId(), staff);
            Toast.makeText(this, "시설 참가 성공!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "시설이 존재하지 않습니다. 정보를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
