package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class ListDoctorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConfirmationAdapter adapter;
    private ArrayList<Confirmation> confirmations;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        try {
            recyclerView = findViewById(R.id.list_of_doctors);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            confirmations = new ArrayList<>();
            adapter = new ConfirmationAdapter(this,confirmations);
            reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("Status").child("Doctors");
            getConfirmationData();

        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }
    private void getConfirmationData(){
        try {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Confirmation confirmation = dataSnapshot.getValue(Confirmation.class);
                        assert confirmation != null;
                        confirmations.add(confirmation);
                    }
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getBaseContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}