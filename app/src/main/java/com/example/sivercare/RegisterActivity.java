package com.example.sivercare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerTextId, registerTextPassword, registerTextPhone, registerTextEmail, registerTextName;
    private Button registerButtonRegister, registerButtonCancel;
    private RadioGroup radioGroupUserType;
    private DatabaseHelper dbHelper;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // UI 컴포넌트 초기화
        registerTextId = findViewById(R.id.RegisterTextId);
        registerTextPassword = findViewById(R.id.RegisterTextPassword);
        registerTextPhone = findViewById(R.id.RegisterTextPhone);
        registerTextEmail = findViewById(R.id.RegisterTextEmail);
        registerTextName = findViewById(R.id.RegisterTextName);
        registerButtonRegister = findViewById(R.id.RegisterButtonRegister);
        registerButtonCancel = findViewById(R.id.RegisterButtonCancel);
        radioGroupUserType = findViewById(R.id.radioGroupUserType);

        // DatabaseHelper 초기화
        dbHelper = new DatabaseHelper(this);

        // 회원가입 버튼 리스너 설정
        registerButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // 취소 버튼 리스너 설정
        registerButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registerUser() {
        Log.d(TAG, "registerUser: 회원가입 시작");

        String id = registerTextId.getText().toString().trim();
        String password = registerTextPassword.getText().toString().trim();
        String phone = registerTextPhone.getText().toString().trim();
        String email = registerTextEmail.getText().toString().trim();
        String name = registerTextName.getText().toString().trim();
        int selectedUserTypeId = radioGroupUserType.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(id)) {
            registerTextId.setError("아이디를 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            registerTextPassword.setError("비밀번호를 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            registerTextPhone.setError("전화번호를 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            registerTextEmail.setError("이메일을 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            registerTextName.setError("이름을 입력해주세요");
            return;
        }

        if (selectedUserTypeId == -1) {
            Toast.makeText(this, "사용자 유형을 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedUserTypeButton = findViewById(selectedUserTypeId);
        String userType = selectedUserTypeButton.getText().toString();

        User user;
        if (userType.equals("직원")) {
            user = new Staff(id, password, phone, email, name);
        } else {
            user = new Admin(id, password, phone, email, name);
        }

        try {
            Log.d(TAG, "registerUser: 사용자 등록 시도");
            long result = dbHelper.addUser(user);
            if (result != -1) {
                Log.d(TAG, "registerUser: 회원가입 성공");
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                if (user instanceof Admin) {
                    Log.d(TAG, "registerUser: Admin으로 AddFacilityActivity로 이동");
                    Intent intent = new Intent(RegisterActivity.this, AddFacilityActivity.class);
                    intent.putExtra("adminId", id);
                    intent.putExtra("adminPassword", password);
                    intent.putExtra("adminPhone", phone);
                    intent.putExtra("adminEmail", email);
                    intent.putExtra("adminName", name);
                    startActivity(intent);
                } else {
                    Log.d(TAG, "registerUser: Staff으로 JoinActivity로 이동");
                    Intent intent = new Intent(RegisterActivity.this, JoinActivity.class);
                    intent.putExtra("staffId", id);
                    intent.putExtra("staffPassword", password);
                    intent.putExtra("staffPhone", phone);
                    intent.putExtra("staffEmail", email);
                    intent.putExtra("staffName", name);
                    startActivity(intent);
                }
            } else {
                Log.d(TAG, "registerUser: 회원가입 실패");
                Toast.makeText(this, "회원가입 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error registering user", e);
            Toast.makeText(this, "회원가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
