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


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView errorMsg;
    private Intent intent;
    private int FLAG = 0;

    private ProgressBar progressBar;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


            progressBar = findViewById(R.id.progressMain);
            CheckBox check = findViewById(R.id.checkMain);
            mAuth = FirebaseAuth.getInstance();
            errorMsg = findViewById(R.id.errorMsg);
            intent = new Intent();
            EditText email,pass;
            email = findViewById(R.id.UserEmailMain);
            pass = findViewById(R.id.UserPasswordMain);
            Button login = findViewById(R.id.loginMain);




            email.setText(getIntent().getStringExtra("U_email"));
            pass.setText(getIntent().getStringExtra("U_password"));




            check.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked){
                    FLAG = 1;
                }
                else {
                    FLAG = 0;
                }
            });


            login.setOnClickListener(v -> {
                try {
                    if(FLAG == 0){
                        if(!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()){
                            progressBar.setVisibility(View.VISIBLE);
                            UserLogin(email.getText().toString(),pass.getText().toString());
                        }
                        else {
                            Snackbar.make(v, "insert email or password", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                    else if(FLAG == 1){
                        if(email.getText().toString().equalsIgnoreCase("admin") && pass.getText().toString().equalsIgnoreCase("1234")){
                            AdminLogin();
                        }
                        else {
                            Snackbar.make(v, "reject entry permit !", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView reg = findViewById(R.id.registerBTN);

            reg.setOnClickListener(v -> {
                intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            });

        ////////////////////////////////


    }

    private void AdminLogin(){
        Toast.makeText(getBaseContext(),"ADMIN ROOT MODE !!",Toast.LENGTH_LONG).show();
        intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    private void UserLogin(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        if(user.isEmailVerified()){
                            errorMsg.setVisibility(View.GONE);
                            TestType(user.getUid(),email,password);
                        }
                        else {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setTextColor(getResources().getColor(R.color.red));
                            errorMsg.setText(R.string.verify_yor_email_to_login);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getBaseContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                });
    }




    private void TestType(String UID, String EMAIL, String PASSWORD){
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        databaseReference.child("Account").child(UID).child("type").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                progressBar.setVisibility(View.GONE);
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                String keyTest = Objects.requireNonNull(task.getResult().getValue()).toString();

                if(keyTest.equals("Doctor")){
                   sendDoctorREQUEST(UID,EMAIL,PASSWORD);
                }
                else if(keyTest.equals("Patient")){
                    Intent intent = new Intent(MainActivity.this, SecurityCircleActivity.class);
                    intent.putExtra("UID",UID);
                    intent.putExtra("Email",EMAIL);
                    intent.putExtra("Password",PASSWORD);
                    intent.putExtra("Type","Patient");
                    startActivity(intent);
                }
            }
        });
    }

    private void sendDoctorREQUEST(String UID, String EMAIL, String PASSWORD){
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        databaseReference.child("Status").child("Doctors").child(UID).child("confirm").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                String keyTest = Objects.requireNonNull(task.getResult().getValue()).toString();

                if(keyTest.equals("Progress")){
                    Toast.makeText(getBaseContext(),"your request on progress ",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                else if(keyTest.equals("No")){
                    Toast.makeText(getBaseContext(),"your request was rejected",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                else if(keyTest.equals("Yes")){
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(MainActivity.this, SecurityCircleActivity.class);
                    intent.putExtra("UID",UID);
                    intent.putExtra("Email",EMAIL);
                    intent.putExtra("Password",PASSWORD);
                    intent.putExtra("Type","Doctor");
                    startActivity(intent);
                }
            }
        });
    }








    @Override
    protected void onStart() {
        super.onStart();
    }
}