package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorSheetActivity extends AppCompatActivity {
    private ArrayList<WorkDays> workDaysArrayList;
    private RecyclerView recyclerView;
    private WorkDaysAdapter workDaysAdapter;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sheet);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        /////////////////////////////////////////
        String UID = getIntent().getStringExtra("UID");
        recyclerView = findViewById(R.id.recycle_sheet);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workDaysArrayList = new ArrayList<>();
        workDaysAdapter = new WorkDaysAdapter(this, workDaysArrayList, new WorkDaysAdapter.onUserClickListener() {
            @Override
            public void onUserClick(int position) {
                WorkDays workDays = workDaysArrayList.get(position);
                Intent intent = new Intent(DoctorSheetActivity.this, PatientsAppointmentsActivity.class);
                intent.putExtra("UIDR",workDays.getId());
                intent.putExtra("UID",UID);
                startActivity(intent);
            }
        });
        assert UID != null;
        reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Users").child("Doctors").child(UID).child("WorkDays");
        getData();
    }

    private void getData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WorkDays workDays = dataSnapshot.getValue(WorkDays.class);
                    workDaysArrayList.add(workDays);
                }
                recyclerView.setAdapter(workDaysAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}