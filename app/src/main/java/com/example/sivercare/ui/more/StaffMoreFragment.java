package com.example.sivercare.ui.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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