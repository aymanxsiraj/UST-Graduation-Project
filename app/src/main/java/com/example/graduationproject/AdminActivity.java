package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private TextView patients_size;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }
        ////////////////////////////////////////////////////////////////test



        CardView R_list = findViewById(R.id.adminRLIST);
        R_list.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ListDoctorActivity.class);
            startActivity(intent);
        });

        CardView Doctors = findViewById(R.id.adminDOCTORS);
        Doctors.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, DoctorToAdminActivity.class);
            startActivity(intent);
        });

        CardView Patients = findViewById(R.id.adminPATIENTS);
        Patients.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, PatientToAdminActivity.class);
            startActivity(intent);
        });

        getRootItemsCountForDoctors();
        getRootItemsCountForPatients();
    }

    public void getRootItemsCountForDoctors() {
        // Reference to the root of the database
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Doctors");;

        // Add a listener to read the data
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the size of root items
                long itemCount = dataSnapshot.getChildrenCount();
                System.out.println("Number of root items: " + itemCount);
                TextView doctors = findViewById(R.id.doctors_size);
                doctors.setText(String.valueOf(itemCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                System.err.println("Error: " + databaseError.getMessage());
            }
        });
    }

    public void getRootItemsCountForPatients() {
        // Reference to the root of the database
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Patient");;

        // Add a listener to read the data
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the size of root items
                long itemCount = dataSnapshot.getChildrenCount();
                System.out.println("Number of root items: " + itemCount);
                TextView doctors = findViewById(R.id.patients_size);
                doctors.setText(String.valueOf(itemCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                System.err.println("Error: " + databaseError.getMessage());
            }
        });
    }


}