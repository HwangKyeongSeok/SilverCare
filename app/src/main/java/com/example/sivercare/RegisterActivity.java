package com.example.sivercare;

<<<<<<< HEAD
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
=======
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerTextId, registerTextPassword, registerTextPhone, registerTextEmail, registerTextName;
    private CheckBox registerCheckUser, registerCheckAdmin;
    private Button registerButtonRegister, registerButtonCancel;
    private DatabaseHelper dbHelper;

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
        registerCheckUser = findViewById(R.id.RegisterCheckUser);
        registerCheckAdmin = findViewById(R.id.RegisterCheckAdmin);
        registerButtonRegister = findViewById(R.id.RegisterButtonRegister);
        registerButtonCancel = findViewById(R.id.RegisterButtonCancel);

        // DatabaseHelper 초기화
        dbHelper = new DatabaseHelper(this);

        // 버튼 리스너 설정
        registerButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        registerButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registerUser() {
        String id = registerTextId.getText().toString().trim();
        String password = registerTextPassword.getText().toString().trim();
        String phone = registerTextPhone.getText().toString().trim();
        String email = registerTextEmail.getText().toString().trim();
        String name = registerTextName.getText().toString().trim();
        boolean isUser = registerCheckUser.isChecked();
        boolean isAdmin = registerCheckAdmin.isChecked();

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

        if (!isUser && !isAdmin) {
            Toast.makeText(this, "사용자 유형을 선택해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        User user;
        if (isUser) {
            user = new Staff(id, password, phone, email, name);
        } else {
            user = new Admin(id, password, phone, email, name);
        }

        long newRowId = dbHelper.addUser(user);

        if (newRowId != -1) {
            Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();

            if (isAdmin) {
                Intent intent = new Intent(RegisterActivity.this, AddFacilityActivity.class);
                intent.putExtra("adminId", id);
                intent.putExtra("adminPassword", password);
                intent.putExtra("adminPhone", phone);
                intent.putExtra("adminEmail", email);
                intent.putExtra("adminName", name);
                startActivity(intent);
            } else {
                Intent intent = new Intent(RegisterActivity.this, JoinActivity.class);
                startActivity(intent);
            }
            finish();
        } else {
            Toast.makeText(this, "회원가입 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
>>>>>>> 5e27fa0 ([구현] 로그인, 회원가입, 로그아웃 기능 구현)
