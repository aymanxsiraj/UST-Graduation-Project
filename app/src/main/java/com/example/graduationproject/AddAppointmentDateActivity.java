package com.example.graduationproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAppointmentDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment_date);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        ///////////////////////////////////////////////////////////////
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        TextView dayName = findViewById(R.id.work_day_day_add);
        TextView date = findViewById(R.id.work_day_date_add);
        TextView timeStart = findViewById(R.id.work_time_start_add);
        TextView timeEnd = findViewById(R.id.work_time_end_add);
        TextView timeType = findViewById(R.id.work_time_type_add);
        EditText setTimeStart = findViewById(R.id.editTextTime_star);
        EditText setTimeEnd = findViewById(R.id.editTextTime_end);

        dayName.setText(getIntent().getStringExtra("p_day_name"));
        date.setText(getIntent().getStringExtra("p_date"));

        setTimeStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                timeStart.setText(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timeStart.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                timeStart.setText(s);
            }
        });

        setTimeEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                timeEnd.setText(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timeEnd.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                timeEnd.setText(s);
            }
        });

        /////////////////////////////////////
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
          RadioButton radioButton = findViewById(checkedId);
          timeType.setText(radioButton.getText().toString());
        });



        Button uploadAppointment = findViewById(R.id.upload_appointment);
        uploadAppointment.setOnClickListener(v -> {
            String finalTime = timeStart.getText().toString()+" - "+timeEnd.getText().toString()+" "+timeType.getText().toString();
            //Toast.makeText(getBaseContext(),finalTime,Toast.LENGTH_LONG).show();

            if(setTimeStart.getText().toString().isEmpty() && setTimeEnd.getText().toString().isEmpty()){
                Snackbar.make(v, "start time or end time are empty !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else {
                uploadWorkDays(getIntent().getStringExtra("UID"),getIntent().getStringExtra("p_day_name"),getIntent().getStringExtra("p_date"),finalTime);
            }
        });
    }

    private void uploadWorkDays(String UID, String day, String date, String time){
        DatabaseReference  mDatabase = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Doctors").child(UID);
        String uniqueID = mDatabase.push().getKey();
        assert uniqueID != null;
        WorkDays workDays = new WorkDays(uniqueID,day,date,time);
        mDatabase.child("WorkDays").child(uniqueID).setValue(workDays);
        Intent intent = new Intent(AddAppointmentDateActivity.this,DoctorSheetActivity.class);
        intent.putExtra("UID",UID);
        startActivity(intent);
        finish();
    }
}