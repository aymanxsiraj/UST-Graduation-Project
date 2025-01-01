package com.example.graduationproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class EntryPointToUpdatePatientsProfileActivity extends AppCompatActivity {

    private String profileName ="";
    private String profilePhone ="";
    private String profileEmail ="";
    private String profilePassword ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point_to_update_patients_profile);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        //////////////////

        CardView show_patient_profile = findViewById(R.id.show_patient_profile);
        show_patient_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntryPointToUpdatePatientsProfileActivity.this, ProfileActivity.class);
                intent.putExtra("name",profileName);
                intent.putExtra("email",profileEmail);
                intent.putExtra("phone",profilePhone);
                intent.putExtra("password",profilePassword);
                intent.putExtra("key","patient");
                startActivity(intent);
            }
        });

        try {
            getPatientName();
            getPatientEmail();
            getPatientPhone();
            getPatientPassword();
        }
        catch (Exception exception){
            Toast.makeText(getBaseContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void getPatientName(){
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            assert currentUser != null;
            String uid = currentUser.getUid();

            DatabaseReference databaseReference = FirebaseDatabase
                    .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
            databaseReference.child("Users").child("Patient").child(uid).child("patientName").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    profileName = Objects.requireNonNull(task.getResult().getValue()).toString();
                }
            });
        }
        catch (Exception exception){
            //
        }

    }


    private void getPatientEmail(){
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            assert currentUser != null;
            String uid = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase
                    .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
            databaseReference.child("Users").child("Patient").child(uid).child("patientEmail").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    profileEmail = Objects.requireNonNull(task.getResult().getValue()).toString();
                }
            });
        }
        catch (Exception exception){
            //
        }


    }


    private void getPatientPhone(){
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            assert currentUser != null;
            String uid = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase
                    .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
            databaseReference.child("Users").child("Patient").child(uid).child("patientPhone").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {

                    profilePhone = Objects.requireNonNull(task.getResult().getValue()).toString();
                }
            });
        }
        catch (Exception exception){
            //
        }

    }

    private void getPatientPassword(){
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            assert currentUser != null;
            String uid = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase
                    .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
            databaseReference.child("Users").child("Patient").child(uid).child("patientPassword").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    profilePassword = Objects.requireNonNull(task.getResult().getValue()).toString();
                }
            });
        }
        catch (Exception exception){
            //
        }

    }
}