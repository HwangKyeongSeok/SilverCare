package com.example.sivercare.ui.Info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sivercare.databinding.FragmentStaffinfoBinding;

public class StaffInfoFragment extends Fragment {

    private FragmentStaffinfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StaffInfoViewModel staffInfoViewModel =
                new ViewModelProvider(this).get(StaffInfoViewModel.class);

        binding = FragmentStaffinfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}