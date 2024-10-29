package com.example.graduationproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private int FLAG = 0;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressBar Progress;
    private EditText userEmail;
    private EditText userPassword;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        ///////////////

        try {
            mAuth = FirebaseAuth.getInstance();

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/");
            mDatabase = database.getReference().child("Account");

            Progress = findViewById(R.id.progress);
            userEmail = findViewById(R.id.UserEmail);
            userPassword = findViewById(R.id.UserPassword);




            CheckBox checkBox = findViewById(R.id.check);



            /////////////////////


            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                //Toast.makeText(getBaseContext(),"="+isChecked,Toast.LENGTH_LONG).show();
                if(isChecked) {
                    FLAG = 1;
                }
                else {
                    FLAG = 0;
                }
            });




            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button reg = findViewById(R.id.regbtn);
            reg.setOnClickListener(v -> {
                if(FLAG == 1){
                    if(
                            userEmail.getText().toString().isEmpty()
                                    && userPassword.getText().toString().isEmpty()){
                        Snackbar.make(v, "insert email or password", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        Progress.setVisibility(View.VISIBLE);
                        DoctorRegister(
                                userEmail.getText().toString(),
                                userPassword.getText().toString()
                        );
                    }
                }

                else if(FLAG == 0){
                    if(
                            userEmail.getText().toString().isEmpty()
                                    && userPassword.getText().toString().isEmpty()){
                        Snackbar.make(v, "insert email or password", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                    else {
                        Progress.setVisibility(View.VISIBLE);
                        PatientRegister(
                                userEmail.getText().toString(),
                                userPassword.getText().toString()
                        );
                    }
                }

                //Toast.makeText(getBaseContext(),"FLAG = "+FLAG,Toast.LENGTH_LONG).show();
            });

        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        TextView textView = findViewById(R.id.haveAccount);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void DoctorRegister(String email, String password){


        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Progress.setVisibility(View.GONE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            user.sendEmailVerification();
                            Toast.makeText(getBaseContext(),"Create User With Email : success",Toast.LENGTH_LONG).show();
                            setUserType(user.getUid(),"Doctor");
                            setDoctorAccountConfirmationStatus(user.getUid(),email,"Progress");
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("U_email",email);
                            intent.putExtra("U_password",password);
                            intent.putExtra("UID",user.getUid());
                            startActivity(intent);



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Progress.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(), "/"+ Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    private void PatientRegister(String email, String password){
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Progress.setVisibility(View.GONE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            user.sendEmailVerification();
                            Toast.makeText(getBaseContext(),"Create User With Email : success",Toast.LENGTH_LONG).show();
                            setUserType(user.getUid(),"Patient");
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("U_email",email);
                            intent.putExtra("U_password",password);
                            intent.putExtra("UID",user.getUid());
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Progress.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(), "/"+ Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void setUserType(String UID,String TYPE){
        try {
            Users user = new Users(TYPE);
            mDatabase.child(UID).setValue(user);
        }
        catch (Exception e){
            Log.w(TAG, Objects.requireNonNull(e.getMessage()));
        }

    }


    private void setDoctorAccountConfirmationStatus(String UID,String EMAIL,String STATUS){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference().child("Status").child("Doctors");

        try {
            Confirmation confirmation = new Confirmation(UID,EMAIL,STATUS);
            reference.child(UID).setValue(confirmation);
        }
        catch (Exception e){
          Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }





    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //TODOs
    }
}