package com.example.graduationproject;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class StatusCheckActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_check);
        // Hide the status bar
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

        ///////////////////////////////////////////////////

        try {
            databaseReference = FirebaseDatabase
                    .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
            AutoUserLogin(getIntent().getStringExtra("UID"));
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }



    private void AutoUserLogin(String UID){
        databaseReference.child("Auto").child(UID).child("type").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                String keyTest = Objects.requireNonNull(task.getResult().getValue()).toString();

                Toast.makeText(getBaseContext(),keyTest,Toast.LENGTH_LONG).show();
                if(keyTest.equals("patient")){
                    Intent intent = new Intent(StatusCheckActivity.this,PatientHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else if(keyTest.equals("doctor")){
                    Intent intent = new Intent(StatusCheckActivity.this,DoctorActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show());
    }
}