package com.example.graduationproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorToAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DoctorToAdminAdapter doctorToAdminAdapter;
    private ArrayList<Doctor> doctorArrayList;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_to_admin);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }
        /////////////////////////////////////
        recyclerView = findViewById(R.id.doctor_to_admin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /////////////////////////////////////
        doctorArrayList = new ArrayList<>();
        doctorToAdminAdapter = new DoctorToAdminAdapter(this, doctorArrayList, new DoctorToAdminAdapter.onUserClickListener() {
            @Override
            public void onUserClick(int position) {
                Doctor mDoctors = doctorArrayList.get(position);
                UserLogin(mDoctors.getUID(),mDoctors.getDoctorEmail(),mDoctors.getDoctorPassword());
            }
        });
        /////////////////////////////////////
        reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Doctors");
        getDoctorData();
    }
    private void getDoctorData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    if(doctor != null){
                        doctorArrayList.add(doctor);
                    }
                    else {
                        Toast.makeText(getBaseContext(),"Null",Toast.LENGTH_LONG).show();
                    }
                }
                recyclerView.setAdapter(doctorToAdminAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void UserLogin(String UID, String email,String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        Intent intent = new Intent(DoctorToAdminActivity.this, AdmitDeleteActivity.class);
                        intent.putExtra("UID",UID);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getBaseContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });
    }

}