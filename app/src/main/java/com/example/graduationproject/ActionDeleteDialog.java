
package com.example.graduationproject;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class ActionDeleteDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Delete")
                .setMessage("are you sure want to delete this user !")
                .setIcon(R.drawable.baseline_delete_forever_24)
                .setPositiveButton("Remove", (dialog, which) -> {
                    try {
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("firebase", String.valueOf(e.getMessage()));
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                });
        return builder.create();

    }



}
