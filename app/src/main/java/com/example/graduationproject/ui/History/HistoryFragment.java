package com.example.graduationproject.ui.History;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.graduationproject.FinalReportActivity;
import com.example.graduationproject.PatientReport;
import com.example.graduationproject.PatientsAppointmentsAdapter;
import com.example.graduationproject.RemoveHistoryActivity;
import com.example.graduationproject.databinding.FragmentGalleryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryFragment extends Fragment {

    private ArrayList<PatientReport> patientReportArrayList;
    private RecyclerView recyclerView;
    private PatientsAppointmentsAdapter patientsAppointmentsAdapter;
    private DatabaseReference reference;
    private FragmentGalleryBinding binding;

    @SuppressLint("UseRequireInsteadOfGet")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recycleHistory;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext()));
        patientReportArrayList = new ArrayList<>();
        patientsAppointmentsAdapter = new PatientsAppointmentsAdapter(getActivity().getApplicationContext(), patientReportArrayList, position -> {
            PatientReport patientReport = patientReportArrayList.get(position);
            Intent intent = new Intent(Objects.requireNonNull(getActivity()).getBaseContext(), FinalReportActivity.class);
            intent.putExtra("name", patientReport.getName());
            intent.putExtra("date", patientReport.getDate());
            intent.putExtra("phone", patientReport.getPhone());
            intent.putExtra("age", patientReport.getAge());
            intent.putExtra("description", patientReport.getDescription());
            intent.putExtra("gender",patientReport.getGender());
            startActivity(intent);
        }, new PatientsAppointmentsAdapter.onUserLongClickListener() {
            @Override
            public void onUserLongClick(int position) {
                PatientReport patientReport = patientReportArrayList.get(position);
                Intent intent = new Intent(getActivity(), RemoveHistoryActivity.class);
                intent.putExtra("UID", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                intent.putExtra("historyID",patientReport.getId());
                intent.putExtra("historyName",patientReport.getName());
                intent.putExtra("historyDate",patientReport.getDate());
                startActivity(intent);
            }
        });

        reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Patient")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("History");
        getHistoryData();



        final TextView textView = binding.textGallery;
        historyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void getHistoryData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PatientReport report = dataSnapshot.getValue(PatientReport.class);
                    patientReportArrayList.add(report);
                }
                recyclerView.setAdapter(patientsAppointmentsAdapter);
                checkEmpty();
            }

            @SuppressLint("UseRequireInsteadOfGet")
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getBaseContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkEmpty(){
        CardView empty = binding.listEmptyHistory;
        if(patientReportArrayList.size() == 0){
            empty.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}