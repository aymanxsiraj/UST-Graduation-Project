package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.TextView;

public class FinalReportActivity extends AppCompatActivity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);
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

        //////////////////////////////////////////////
        TextView name = findViewById(R.id.final_name);
        TextView date = findViewById(R.id.final_date);
        TextView phone = findViewById(R.id.final_phone);
        TextView age = findViewById(R.id.final_age);
        TextView description = findViewById(R.id.final_description);

        name.setText(getIntent().getStringExtra("name"));
        date.setText(getIntent().getStringExtra("date"));
        phone.setText(getIntent().getStringExtra("phone"));
        age.setText(getIntent().getStringExtra("age"));
        description.setText(getIntent().getStringExtra("description"));

    }
}