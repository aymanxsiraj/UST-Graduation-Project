package com.example.graduationproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AdmitDeletePatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_delete_patient);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }
        ////////////

        Button remove = findViewById(R.id.button_confirm_delete_patient);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteUser();
            }
        });

    }

    private void DeleteUser(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "User account deleted successfully", Toast.LENGTH_SHORT).show();
                            DeleteAssociatedDataFromFirebaseRealtimeDatabasePatientsRoot(getIntent().getStringExtra("UID"));
                            DeleteAssociatedDataFromFirebaseRealtimeDatabaseAutoRoot(getIntent().getStringExtra("UID"));
                            DeleteAssociatedDataFromFirebaseRealtimeDatabaseAccountRoot(getIntent().getStringExtra("UID"));
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "Failed to delete user: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            //FirebaseAuth.getInstance().signOut();
                        }
                    });
        } else {
            Toast.makeText(getBaseContext(), "No user is signed in", Toast.LENGTH_SHORT).show();
            //FirebaseAuth.getInstance().signOut();
        }
    }

    private void DeleteAssociatedDataFromFirebaseRealtimeDatabasePatientsRoot(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users").child("Patient").child(UID);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getBaseContext(),"User data deleted from Realtime Database.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void DeleteAssociatedDataFromFirebaseRealtimeDatabaseAutoRoot(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Auto").child(UID);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getBaseContext(),"User data deleted from Realtime Database.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void DeleteAssociatedDataFromFirebaseRealtimeDatabaseAccountRoot(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Account").child(UID);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getBaseContext(),"User data deleted from Realtime Database.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onPause() {
        FirebaseAuth.getInstance().signOut();
        finish();
        super.onPause();
    }

    @Override
    protected void onStop() {
        FirebaseAuth.getInstance().signOut();
        finish();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        FirebaseAuth.getInstance().signOut();
        finish();
        super.onDestroy();
    }
}