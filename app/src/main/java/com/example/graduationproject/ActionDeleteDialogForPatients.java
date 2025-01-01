package com.example.graduationproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ActionDeleteDialogForPatients extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Delete")
                .setMessage("are you sure want to delete this user !")
                .setPositiveButton("Delete", (dialog, which) -> {

                    try {

                        Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                });
        return builder.create();
    }

    private void DeleteAssociatedDataFromFirebaseRealtimeDatabasePatientsRoot(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users").child("Patient").child(UID);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(),"User data deleted from Realtime Database.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void DeleteAssociatedDataFromFirebaseRealtimeDatabaseStatusRoot(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Status").child("Doctors").child(UID);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(),"User data deleted from Realtime Database.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void DeleteAssociatedDataFromFirebaseRealtimeDatabaseAutoRoot(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Auto").child(UID);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(),"User data deleted from Realtime Database.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void DeleteAssociatedDataFromFirebaseRealtimeDatabaseAccountRoot(String UID){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Account").child(UID);
        userRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(),"User data deleted from Realtime Database.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

}
