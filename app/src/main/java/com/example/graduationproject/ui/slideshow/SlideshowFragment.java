package com.example.graduationproject.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.graduationproject.AboutProjectActivity;
import com.example.graduationproject.EntryPointToUpdatePatientsProfileActivity;
import com.example.graduationproject.ProfileActivity;
import com.example.graduationproject.databinding.FragmentSlideshowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardView open_edit_activity = binding.cardToOpenEditActivity;
        open_edit_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EntryPointToUpdatePatientsProfileActivity.class);
                startActivity(intent);
            }
        });

        CardView open_about_activity = binding.cardToOpenAboutActivity;
        open_about_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getContext(), AboutProjectActivity.class);
               startActivity(intent);
            }
        });

        CardView contact_support = binding.cardToOpenSupportActivity;
        contact_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Coming Soon...",Toast.LENGTH_LONG).show();
                openWhatsApp("249990033981");
            }
        });


        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void openWhatsApp(String phoneNumber) {
        // Format the phone number (ensure it is in international format without '+' sign)
        String formattedNumber = phoneNumber.replace("+", "");

        try {
            // Check if WhatsApp is installed
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/" + formattedNumber));
            startActivity(intent);
        } catch (Exception e) {
            // WhatsApp is not installed
            Toast.makeText(getContext(), "WhatsApp is not installed on your device", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}