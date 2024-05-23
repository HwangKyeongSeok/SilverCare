package com.example.sivercare.ui.admininfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sivercare.databinding.FragmentAdmininfoBinding;

public class AdminInfoFragment extends Fragment {

    private FragmentAdmininfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminInfoViewModel adminInfoViewModel =
                new ViewModelProvider(this).get(AdminInfoViewModel.class);

        binding = FragmentAdmininfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}