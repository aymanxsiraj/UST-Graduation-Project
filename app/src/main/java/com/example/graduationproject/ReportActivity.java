package com.example.graduationproject;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ReportActivity extends AppCompatActivity {




    private EditText name,phone,age,description;
    private DatabaseReference reference;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        /////////////////////////////////////

        reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users");



        name = findViewById(R.id.patient_report_name);
        phone = findViewById(R.id.patient_report_phone);
        age = findViewById(R.id.patient_report_age);
        description = findViewById(R.id.patient_description);
        TextView Day = findViewById(R.id.available_day);
        TextView Date = findViewById(R.id.date_result_patient);
        Day.setText(getIntent().getStringExtra("DAY"));
        Date.setText(getIntent().getStringExtra("DATE"));

        String UID = getIntent().getStringExtra("UID");
        String ID = getIntent().getStringExtra("ID");









        Button submit = findViewById(R.id.submit_appointment);
        submit.setOnClickListener(v -> {
            if(
                    name.getText().toString().isEmpty() &&
                    phone.getText().toString().isEmpty() &&
                    age.getText().toString().isEmpty() &&
                    description.getText().toString().isEmpty()
            ){
                name.setError("insert your name !");
                phone.setError("insert your phone number !");
                age.setError("insert your age !");
                description.setError("insert your case description !");
                Snackbar.make(v, "Empty...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else {
                CommitReport(ID,UID,
                        getIntent().getStringExtra("DATE"),
                        name.getText().toString(),
                        phone.getText().toString(),
                        age.getText().toString(),
                        description.getText().toString()
                        );
            }
        });

    }


    private void CommitReport(String ID, String UID, String date, String name, String phone, String age, String description){
        Map<String,Object> report = new HashMap<>();
        report.put("date",date);
        report.put("name",name);
        report.put("phone",phone);
        report.put("age",age);
        report.put("description",description);
        String reportID = reference.push().getKey();
        assert reportID != null;
        reference.child("Doctors").child(UID).child("WorkDays").child(ID).child("Patients").child(reportID)
                .updateChildren(report).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update successful
                        CommitHistory(
                                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(),
                                date,name,phone,age,description
                                );
                        Log.d("Firebase", "Root updated successfully");
                    } else {
                        // Update failed
                        Log.e("Firebase", "Failed to update root", task.getException());
                        Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void CommitHistory(String UID, String date, String name, String phone, String age, String description){
        Map<String,Object> report = new HashMap<>();
        report.put("date",date);
        report.put("name",name);
        report.put("phone",phone);
        report.put("age",age);
        report.put("description",description);
        String historyId = reference.push().getKey();
        assert historyId != null;
        reference.child("Patient").child(UID).child("History").child(historyId)
                .updateChildren(report).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update successful
                        Intent intent = new Intent(ReportActivity.this, BookAppointmentWithDoctorActivity.class);
                        startActivity(intent);
                        Toast.makeText(getBaseContext(),"History",Toast.LENGTH_SHORT).show();
                        Log.d("Firebase", "Root updated successfully");
                    } else {
                        // Update failed
                        Log.e("Firebase", "Failed to update root", task.getException());
                        Toast.makeText(getBaseContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }






}