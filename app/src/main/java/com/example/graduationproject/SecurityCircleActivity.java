package com.example.graduationproject;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SecurityCircleActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;
    private Spinner spinner;
    private String special;

    private DatabaseReference mDatabase;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_circle);

        WindowInsetsController insetsController = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            insetsController = getWindow().getInsetsController();
        }
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.statusBars());
            insetsController.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }

        // Hide the action bar if necessary
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ///////////////


        //////////////////////////////////////////////////

        mDatabase = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users");
        name = findViewById(R.id.User_Name);
        email = findViewById(R.id.User_Email);
        phone = findViewById(R.id.User_Mobile);
        password = findViewById(R.id.User_Password);
        spinner = findViewById(R.id.doctorSpin);
        Button commit = findViewById(R.id.commitBTN);

        ///////////////////////////////////////////////////

        spinner = findViewById(R.id.doctorSpin);
        String[]items = new String[]{"Internal Specialist","Orthopedic Specialist"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

          spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                special = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



       //////////////////////////////////////////////////////

        String UI = getIntent().getStringExtra("Type");
        if(Objects.requireNonNull(UI).equalsIgnoreCase("doctor")){
            spinner.setVisibility(View.VISIBLE);
            email.setText(getIntent().getStringExtra("Email"));
            email.setEnabled(false);
            password.setText(getIntent().getStringExtra("Password"));
            password.setEnabled(false);
        }
        else if(UI.equalsIgnoreCase("patient")){
            spinner.setVisibility(View.GONE);
            email.setText(getIntent().getStringExtra("Email"));
            email.setEnabled(false);
            password.setText(getIntent().getStringExtra("Password"));
            password.setEnabled(false);
        }

        try {
            testCurrentUser(UI,getIntent().getStringExtra("UID"));
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


        commit.setOnClickListener(v -> {
            if(name.getText().toString().isEmpty() && phone.getText().toString().isEmpty()){
                Snackbar.make(v, "insert name or phone number", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else {
                setUserArgumentAutoLoginToFirebaseRealTimeDatabase(UI,getIntent().getStringExtra("UID"));
                if(UI.equalsIgnoreCase("doctor")){
                   UpdateRealTimeDatabaseDoctorRoot(
                           name.getText().toString(),
                           email.getText().toString(),
                           phone.getText().toString(),
                           password.getText().toString(),
                           special,getIntent().getStringExtra("UID")
                           );
                }
                else if(UI.equalsIgnoreCase("patient")){
                    UpdateRealTimeDatabasePatientRoot(
                            name.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            password.getText().toString(),
                            getIntent().getStringExtra("UID")
                    );
                }
            }
        });


    }

    private void UpdateRealTimeDatabaseDoctorRoot(String name ,String email, String phone, String password, String specialist,String UID){
        //Doctor doctor = new Doctor(UID,name,email,phone,password,specialist);
        Map<String,Object> data = new HashMap<>();
        data.put("uid",UID);
        data.put("doctorName",name);
        data.put("doctorEmail",email);
        data.put("doctorPhone",phone);
        data.put("doctorPassword",password);
        data.put("doctorSpecialist",specialist);
        mDatabase.child("Doctors").child(UID).updateChildren(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Update successful
                Intent intent = new Intent(SecurityCircleActivity.this, DoctorActivity.class);
                startActivity(intent);
                Log.d("Firebase", "Root updated successfully");
            } else {
                // Update failed
                Log.e("Firebase", "Failed to update root", task.getException());
                Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void UpdateRealTimeDatabasePatientRoot(String name, String email, String phone, String password,String UID){
        //Patient patient = new Patient(UID,name,email,phone,password);
        Map<String,Object> data = new HashMap<>();
        data.put("uid",UID);
        data.put("patientName",name);
        data.put("patientEmail",email);
        data.put("patientPhone",phone);
        data.put("patientPassword",password);
        mDatabase.child("Patient").child(UID).updateChildren(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Update successful
                Intent intent = new Intent(SecurityCircleActivity.this, PatientHomeActivity.class);
                startActivity(intent);
                Log.d("Firebase", "Root updated successfully");
            } else {
                // Update failed
                Log.e("Firebase", "Failed to update root", task.getException());
                Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUserArgumentAutoLoginToFirebaseRealTimeDatabase(String type,String UID){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Auto");
        if(type.equals("Doctor")){
            AutoLogin autoLogin = new AutoLogin(UID,"doctor");
            databaseReference.child(UID).setValue(autoLogin);
        }
        else if(type.equals("Patient")){
            AutoLogin autoLogin = new AutoLogin(UID,"patient");
            databaseReference.child(UID).setValue(autoLogin);
        }

    }

    private void testCurrentUser(String type,String UID){
        if(type.equals("Doctor")){
            DatabaseReference reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Users").child("Doctors");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String U_ID = dataSnapshot.getKey(); // <- USER_ID TEST
                        assert U_ID != null;
                        if(U_ID.equals(UID)){
                            getUserName(type,UID);
                            getUserPhone(type,UID);
                            getDoctorSpecialist(UID);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(type.equals("Patient")){
            DatabaseReference reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Users").child("Patient");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String U_ID = dataSnapshot.getKey(); // <- USER_ID TEST
                        assert U_ID != null;
                        if(U_ID.equals(UID)){
                            getUserName(type,UID);
                            getUserPhone(type,UID);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }

    private void getUserName(String type,String UID){
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Users");

            if(type.equals("Doctor")){
                if(UID != null){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String USER_NAME = Objects.requireNonNull(snapshot.child("Doctors").child(UID).child("doctorName").getValue()).toString();

                            // get user name

                            if(USER_NAME.isEmpty()){
                                Toast.makeText(getBaseContext(),"new user",Toast.LENGTH_LONG).show();
                            }
                            else{
                                name.setText(USER_NAME);
                                name.setEnabled(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            else if(type.equals("Patient")){
                if(UID != null){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String USER_NAME = Objects.requireNonNull(snapshot.child("Patient").child(UID).child("patientName").getValue()).toString();

                            // get user name

                            if(USER_NAME.isEmpty()){
                                Toast.makeText(getBaseContext(),"new user",Toast.LENGTH_LONG).show();
                            }
                            else{
                                name.setText(USER_NAME);
                                name.setEnabled(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void getUserPhone(String type,String UID){
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Users");

            if(type.equals("Doctor")){
                if(UID != null){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String PHONE = Objects.requireNonNull(snapshot.child("Doctors").child(UID).child("doctorPhone").getValue()).toString();

                            // get user name

                            if(PHONE.isEmpty()){
                                Toast.makeText(getBaseContext(),"new user",Toast.LENGTH_LONG).show();
                            }
                            else{
                                phone.setText(PHONE);
                                phone.setEnabled(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
            else if(type.equals("Patient")){
                if(UID != null){
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String PHONE = Objects.requireNonNull(snapshot.child("Patient").child(UID).child("patientPhone").getValue()).toString();

                            // get user name

                            if(PHONE.isEmpty()){
                                Toast.makeText(getBaseContext(),"new user",Toast.LENGTH_LONG).show();
                            }
                            else{
                                phone.setText(PHONE);
                                phone.setEnabled(false);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void getDoctorSpecialist(String UID){
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String Specialist = Objects.requireNonNull(snapshot.child("Doctors").child(UID).child("doctorSpecialist").getValue()).toString();

                    // get user name

                    if(Specialist.isEmpty()){
                        Toast.makeText(getBaseContext(),"new user",Toast.LENGTH_LONG).show();
                    }
                    else{
                        special = Specialist;
                        spinner.setEnabled(false);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}