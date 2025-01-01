package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.graduationproject.databinding.ActivityBookAppointmentWithDoctorBinding;

public class BookAppointmentWithDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        com.example.graduationproject.databinding.ActivityBookAppointmentWithDoctorBinding binding = ActivityBookAppointmentWithDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button book = findViewById(R.id.Book_Appointment);
        book.setOnClickListener(v -> {
            Intent intent = new Intent(BookAppointmentWithDoctorActivity.this, AvailableDateActivity.class);
            intent.putExtra("UID",getIntent().getStringExtra("UID"));
            startActivity(intent);
        });

        TextView name = findViewById(R.id.doctor_name_patient_home);
        TextView specialist = findViewById(R.id.doctor_specialist_patient_home);
        TextView email = findViewById(R.id.doctor_email_patient_home);
        TextView phone = findViewById(R.id.doctor_phone_patient_home);

        name.setText(getIntent().getStringExtra("Name"));
        specialist.setText(getIntent().getStringExtra("Specialist"));
        email.setText(getIntent().getStringExtra("Email"));
        phone.setText(getIntent().getStringExtra("Phone"));


    }
}