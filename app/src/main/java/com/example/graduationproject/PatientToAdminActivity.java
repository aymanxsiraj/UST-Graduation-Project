package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientToAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Patient> patientArrayList;
    private PatientToAdminAdapter patientToAdminAdapter;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_to_admin);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }
        /////////////////////////////////////
        recyclerView = findViewById(R.id.patient_to_admin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /////////////////////////////////////
        patientArrayList = new ArrayList<>();
        patientToAdminAdapter = new PatientToAdminAdapter(this,patientArrayList);
        /////////////////////////////////////
        reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Patient");
        getPatientData();

    }

    private void getPatientData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Patient patient = dataSnapshot.getValue(Patient.class);
                    if(patient != null){
                        patientArrayList.add(patient);
                    }
                    else {
                        Toast.makeText(getBaseContext(),"Null",Toast.LENGTH_LONG).show();
                    }
                }
                recyclerView.setAdapter(patientToAdminAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}