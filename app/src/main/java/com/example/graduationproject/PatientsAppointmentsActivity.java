package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PatientsAppointmentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<PatientReport> patientReportArrayList;
    private  PatientsAppointmentsAdapter patientsAppointmentsAdapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_appointments);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        //////////////////////////////////////
        recyclerView = findViewById(R.id.recycle_patients_reports);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientReportArrayList = new ArrayList<>();
        patientsAppointmentsAdapter = new PatientsAppointmentsAdapter(this, patientReportArrayList, position -> {
            PatientReport patientReport = patientReportArrayList.get(position);
            Intent intent = new Intent(PatientsAppointmentsActivity.this, FinalReportActivity.class);
            intent.putExtra("name",patientReport.getName());
            intent.putExtra("date",patientReport.getDate());
            intent.putExtra("phone",patientReport.getPhone());
            intent.putExtra("age",patientReport.getAge());
            intent.putExtra("description",patientReport.getDescription());
            startActivity(intent);
        });
        databaseReference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Doctors").child(Objects.requireNonNull(getIntent().getStringExtra("UID")))
                .child("WorkDays").child(Objects.requireNonNull(getIntent().getStringExtra("UIDR"))).child("Patients");
        getReportData();



    }

    private void getReportData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PatientReport patientReport = dataSnapshot.getValue(PatientReport.class);
                    patientReportArrayList.add(patientReport);

                }
                recyclerView.setAdapter(patientsAppointmentsAdapter);
                checkEmpty();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkEmpty(){
        CardView empty = findViewById(R.id.list_empty_appointment);
        if(patientReportArrayList.size() == 0){
            empty.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.GONE);
        }
    }
}