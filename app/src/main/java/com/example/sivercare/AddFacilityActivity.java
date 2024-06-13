package com.example.sivercare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFacilityActivity extends AppCompatActivity {

    private EditText afText1, afText2, afText3, afText4;
    private Button afButtonJoin;
    private DatabaseHelper dbHelper;
    private String adminId, adminPassword, adminPhone, adminEmail, adminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfacility);

        // UI 컴포넌트 초기화
        afText1 = findViewById(R.id.AFText1);
        afText2 = findViewById(R.id.AFText2);
        afText3 = findViewById(R.id.AFText3);
        afText4 = findViewById(R.id.AFText4);
        afButtonJoin = findViewById(R.id.AFButtonJoin);

        // DatabaseHelper 초기화
        dbHelper = new DatabaseHelper(this);

        // Intent에서 관리자 정보 가져오기
        Intent intent = getIntent();
        adminId = intent.getStringExtra("adminId");
        adminPassword = intent.getStringExtra("adminPassword");
        adminPhone = intent.getStringExtra("adminPhone");
        adminEmail = intent.getStringExtra("adminEmail");
        adminName = intent.getStringExtra("adminName");

        // 등록 버튼 리스너 설정
        afButtonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFacility();
            }
        });
    }

    private void addFacility() {
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

        Admin admin = new Admin(adminId, adminPassword, adminPhone, adminEmail, adminName);

        Facility facility = new Facility(city, district, subdistrict, name, admin, null, 0);
        long newRowId = dbHelper.addFacility(facility);

        if (newRowId != -1) {
            Toast.makeText(this, "시설 등록 성공!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddFacilityActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "시설 등록 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}