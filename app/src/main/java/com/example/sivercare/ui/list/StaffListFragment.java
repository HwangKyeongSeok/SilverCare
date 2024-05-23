package com.example.sivercare.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sivercare.databinding.FragmentStafflistBinding;

public class StaffListFragment extends Fragment {

    private FragmentStafflistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StaffListViewModel staffListViewModel =
                new ViewModelProvider(this).get(StaffListViewModel.class);

        binding = FragmentStafflistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}