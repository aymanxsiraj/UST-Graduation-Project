package com.example.graduationproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private EditText name,email,phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        name = findViewById(R.id.name_profile);
        email = findViewById(R.id.email_profile);
        phone = findViewById(R.id.phone_profile);
        password = findViewById(R.id.password_profile);

        getProfile();

        Button update = findViewById(R.id.button_update);
        update.setOnClickListener(v -> {
            if(name.getText().toString().isEmpty() && phone.getText().toString().isEmpty()){
                Toast.makeText(getBaseContext(),"empty value",Toast.LENGTH_LONG).show();
            }
            else {
                String key = getIntent().getStringExtra("key");

                assert key != null;
                if(key.equals("doctor")){
                    Toast.makeText(getBaseContext(),"doctor profile",Toast.LENGTH_LONG).show();
                    updateDoctorNameAndPhone(name.getText().toString(),phone.getText().toString());
                }
                else if(key.equals("patient")){
                    Toast.makeText(getBaseContext(),"patient profile",Toast.LENGTH_LONG).show();
                    updatePatientNameAndPhone(name.getText().toString(),phone.getText().toString());
                }
                else {
                    System.out.println("system");
                }
            }
        });


    }

    private void getProfile(){
        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        phone.setText(getIntent().getStringExtra("phone"));
        password.setText(getIntent().getStringExtra("password"));
    }

    private void updateDoctorNameAndPhone(String name, String phone){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        assert user != null;
        DatabaseReference reference  = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Doctors").child(user.getUid());

        Map<String,Object> data = new HashMap<>();
        data.put("doctorName",name);
        data.put("doctorPhone",phone);

        reference.updateChildren(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Update successful
                Toast.makeText(getBaseContext(),"finish",Toast.LENGTH_LONG).show();
                Log.d("Firebase", "Root updated successfully");
                finish();
            } else {
                // Update failed
                Log.e("Firebase", "Failed to update root", task.getException());
                Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePatientNameAndPhone(String name, String phone){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        assert user != null;
        DatabaseReference reference  = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Patient").child(user.getUid());

        Map<String,Object> data = new HashMap<>();
        data.put("patientName",name);
        data.put("patientPhone",phone);

        reference.updateChildren(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Update successful
                Toast.makeText(getBaseContext(),"finish",Toast.LENGTH_LONG).show();
                Log.d("Firebase", "Root updated successfully");
                finish();
            } else {
                // Update failed
                Log.e("Firebase", "Failed to update root", task.getException());
                Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}