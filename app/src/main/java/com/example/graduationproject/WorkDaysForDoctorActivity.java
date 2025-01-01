package com.example.graduationproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WorkDaysForDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_days_for_doctor);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        /////////////

        TextView day_of_day = findViewById(R.id.day_of_day);
        TextView date_of_day = findViewById(R.id.date_of_day);
        day_of_day.setText(getIntent().getStringExtra("dayTAG"));
        date_of_day.setText(getIntent().getStringExtra("dateTAG"));

        Button remove = findViewById(R.id.button_remove_day);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChildNode();
            }
        });

        Button cancel = findViewById(R.id.button_cancel_remove);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void deleteChildNode() {
        // Get a reference to your Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        // Reference the specific child node you want to delete
        String dayID = getIntent().getStringExtra("dayID");
        String UID = getIntent().getStringExtra("doctorUID");
        assert UID != null;
        assert dayID != null;
        DatabaseReference childReference = databaseReference.child("Users").child("Doctors")
                .child(UID).child("WorkDays").child(dayID);

        // Remove the child
        childReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Deletion was successful
                Log.d("Firebase", "Child node deleted successfully.");
                Toast.makeText(getBaseContext(),"Child node deleted successfully",Toast.LENGTH_LONG).show();
                finish();
            } else {
                // Deletion failed
                Log.e("Firebase", "Failed to delete child node.", task.getException());
                Toast.makeText(getBaseContext(),"Failed to delete child node",Toast.LENGTH_LONG).show();
            }
        });
    }
}