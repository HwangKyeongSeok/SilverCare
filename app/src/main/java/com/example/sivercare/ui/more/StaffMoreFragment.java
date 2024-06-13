package com.example.sivercare.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sivercare.LoginActivity;
import com.example.sivercare.R;

public class StaffMoreFragment extends Fragment {

    private Button userMoreButtonLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staffmore, container, false);

        userMoreButtonLogout = view.findViewById(R.id.UserMoreButtonLogout);

        userMoreButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void logout() {
        // 사용자 세션 데이터를 제거합니다 (필요시 SharedPreferences 등을 사용하여 세션 데이터를 관리할 수 있습니다)
        // SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        // SharedPreferences.Editor editor = preferences.edit();
        // editor.clear();
        // editor.apply();

        // 로그인 화면으로 이동합니다
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // 현재 액티비티를 종료합니다
        getActivity().finish();
    }
}
