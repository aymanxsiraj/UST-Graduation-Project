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

public class RemoveHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_history);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }

        //////////////
        TextView name = findViewById(R.id.the_h_name);
        TextView date = findViewById(R.id.the_h_date);
        name.setText(getIntent().getStringExtra("historyName"));
        date.setText(getIntent().getStringExtra("historyDate"));

        Button remove = findViewById(R.id.button_remove_history);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChildNode();
            }
        });

        Button cancel = findViewById(R.id.button_cancel_remove_history);
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
        String historyID = getIntent().getStringExtra("historyID");
        String UID = getIntent().getStringExtra("UID");
        assert UID != null;
        assert historyID != null;
        DatabaseReference childReference = databaseReference.child("Users").child("Patient")
                .child(UID).child("History").child(historyID);

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