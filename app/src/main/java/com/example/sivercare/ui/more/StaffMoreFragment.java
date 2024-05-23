package com.example.sivercare.ui.more;

<<<<<<< HEAD
=======
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
>>>>>>> 5e27fa0 ([구현] 로그인, 회원가입, 로그아웃 기능 구현)
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sivercare.databinding.FragmentStaffmoreBinding;

public class StaffMoreFragment extends Fragment {

    private FragmentStaffmoreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StaffMoreViewModel staffMoreViewModel =
                new ViewModelProvider(this).get(StaffMoreViewModel.class);

        binding = FragmentStaffmoreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
=======
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
        SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // 로그인 화면으로 이동합니다
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // 현재 액티비티를 종료합니다
        getActivity().finish();
    }
}
>>>>>>> 5e27fa0 ([구현] 로그인, 회원가입, 로그아웃 기능 구현)
