package com.example.graduationproject.ui.PatientHome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.graduationproject.InternalSpecialistActivity;
import com.example.graduationproject.OrthopedicSpecialistActivity;
import com.example.graduationproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardView cardView = binding.orthopedicCard;
        CardView cardView1 = binding.internalCard;

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OrthopedicSpecialistActivity.class);
            startActivity(intent);
        });
        cardView1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), InternalSpecialistActivity.class);
            startActivity(intent);
        });

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}