package com.example.graduationproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

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
        TextView f_name = findViewById(R.id.final_patient_name);
        TextView phone = findViewById(R.id.final_phone);
        TextView age = findViewById(R.id.final_age);
        TextView description = findViewById(R.id.final_description);
        TextView gender = findViewById(R.id.final_gender);

        name.setText(getIntent().getStringExtra("name"));
        date.setText(getIntent().getStringExtra("date"));
        f_name.setText(String.format("Patient N: %s", getIntent().getStringExtra("name")));
        phone.setText(String.format("Patient Phone: %s", getIntent().getStringExtra("phone")));
        age.setText(String.format("Patient Age: %s", getIntent().getStringExtra("age")));
        description.setText(getIntent().getStringExtra("description"));
        gender.setText(String.format("Gender: %s", getIntent().getStringExtra("gender")));

        LinearLayout layout = findViewById(R.id.patient_open_dec);
        layout.setOnClickListener(v -> description.setVisibility(View.VISIBLE));

        Button save = findViewById(R.id.take_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getVisibility() == View.VISIBLE){
                    description.setVisibility(View.GONE);
                    if(description.getVisibility() == View.GONE){
                        View rootView = findViewById(android.R.id.content);
                        ScreenshotUtils.takeScreenshot(rootView,getBaseContext(), "screenshot");
                    }
                }
            }
        });

    }
}