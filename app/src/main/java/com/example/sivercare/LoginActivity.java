package com.example.sivercare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sivercare.ui.adminmain.AdminMainFragment;
import com.example.sivercare.ui.adminmain.AdminMainViewModel;
import com.example.sivercare.ui.list.StaffListFragment;
import com.example.sivercare.ui.list.StaffListViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText loginTextId, loginTextPassword;
    private Button loginButtonLogin, loginButtonRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI 컴포넌트 초기화
        loginTextId = findViewById(R.id.LoginTextId);
        loginTextPassword = findViewById(R.id.LoginTextPassword);
        loginButtonLogin = findViewById(R.id.LoginButtonLogin);
        loginButtonRegister = findViewById(R.id.LoginButtonRegister);

        // DatabaseHelper 초기화
        dbHelper = new DatabaseHelper(this);

        // 로그인 버튼 리스너 설정
        loginButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // 회원가입 버튼 리스너 설정
        loginButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String id = loginTextId.getText().toString().trim();
        String password = loginTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(id)) {
            loginTextId.setError("아이디를 입력해주세요");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            loginTextPassword.setError("비밀번호를 입력해주세요");
            return;
        }

        boolean isUserExists = dbHelper.checkUser(id, password);

        if (isUserExists) {
            String userType = dbHelper.getUserType(id, password);

            if (userType != null) {
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();

                if (userType.equals("직원")) {
                    Intent intent = new Intent(LoginActivity.this, StaffMainActivity.class); // StaffActivity로 이동
                    startActivity(intent);
                } else if (userType.equals("관리자")) {
                    Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class); // AdminActivity로 이동
                    startActivity(intent);
                }
                finish();
            } else {
                Toast.makeText(this, "로그인 실패. 사용자 유형을 확인할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "로그인 실패. 아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}

