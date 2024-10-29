package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;

import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class DoctorActivity extends AppCompatActivity {
    private TextView doctorName;
    private TextView doctorSpecialist;
    private TextView doctorEmail;
    private  TextView doctorPhone;
    private FirebaseUser currentUser;
    private String dayName = null;
    private String selectedDate = null;

    private String profileName;
    private String profilePhone;
    private String profileEmail;
    private String profilePassword;

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_title);
        }


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();





        /////////////////////////////////////////////

        LinearLayout layout = findViewById(R.id.liner_profile);
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorActivity.this, ProfileActivity.class);
            intent.putExtra("name",profileName);
            intent.putExtra("email",profileEmail);
            intent.putExtra("phone",profilePhone);
            intent.putExtra("password",profilePassword);
            startActivity(intent);
        });

        Button sheet = findViewById(R.id.listing);
        sheet.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorActivity.this,DoctorSheetActivity.class);
            intent.putExtra("UID",currentUser.getUid());
            startActivity(intent);
        });


        TextView dateTime = findViewById(R.id.doctor_times_home);
        doctorName = findViewById(R.id.doctor_name_home);
        doctorSpecialist = findViewById(R.id.doctor_specialist_home);
        doctorEmail = findViewById(R.id.doctor_email_home);
        doctorPhone = findViewById(R.id.doctor_phone_home);


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        dateTime.setText(String.format(":%d/%d/%d", day, month, year));


        DatePicker datePicker = findViewById(R.id.calendar_date);
        datePicker.setOnDateChangedListener((view, year1, monthOfYear, dayOfMonth) -> {

            Calendar date = Calendar.getInstance();
            date.set(year1,monthOfYear,dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            dayName = simpleDateFormat.format(date.getTime());

            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            selectedDate = simpleDateFormat1.format(date.getTime());



        });
        CardView add = findViewById(R.id.add_appointment);
        add.setOnClickListener(v -> {
            if(dayName == null && selectedDate == null){
                Snackbar.make(v, "select the date from the datePicker !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else {
                Intent intent = new Intent(DoctorActivity.this, AddAppointmentDateActivity.class);
                intent.putExtra("p_date",selectedDate);
                intent.putExtra("p_day_name",dayName);
                intent.putExtra("UID",currentUser.getUid());
                startActivity(intent);
            }
        });








        getDoctorName();
        getDoctorSpecialist();
        getDoctorEmail();
        getDoctorPhone();
        getDoctorPassword();


    }

    private void getDoctorName(){
        assert currentUser != null;
        String uid = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference.child("Users").child("Doctors").child(uid).child("doctorName").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                profileName = Objects.requireNonNull(task.getResult().getValue()).toString();
                doctorName.setText(Objects.requireNonNull(task.getResult().getValue()).toString());
            }
        });
    }

    private void getDoctorSpecialist(){
        assert currentUser != null;
        String uid = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference.child("Users").child("Doctors").child(uid).child("doctorSpecialist").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                doctorSpecialist.setText(Objects.requireNonNull(task.getResult().getValue()).toString());
            }
        });
    }

    private void getDoctorEmail(){
        assert currentUser != null;
        String uid = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference.child("Users").child("Doctors").child(uid).child("doctorEmail").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                profileEmail = Objects.requireNonNull(task.getResult().getValue()).toString();
                doctorEmail.setText(Objects.requireNonNull(task.getResult().getValue()).toString());
            }
        });
    }


    private void getDoctorPhone(){
        assert currentUser != null;
        String uid = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference.child("Users").child("Doctors").child(uid).child("doctorPhone").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                profilePhone = Objects.requireNonNull(task.getResult().getValue()).toString();
                doctorPhone.setText(Objects.requireNonNull(task.getResult().getValue()).toString());
            }
        });
    }

    private void getDoctorPassword(){
        assert currentUser != null;
        String uid = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference.child("Users").child("Doctors").child(uid).child("doctorPassword").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                profilePassword = Objects.requireNonNull(task.getResult().getValue()).toString();
            }
        });
    }




    public void openDialog(){
        AlertDialogProvider provider = new AlertDialogProvider();
        provider.show(getSupportFragmentManager(),"alert dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.action_logout){
            openDialog();
            return true;
        }
        else if(item.getItemId() == R.id.action_logout){
            Toast.makeText(getBaseContext(),"action about",Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }


}