package com.example.graduationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PatientToAdminAdapter extends RecyclerView.Adapter<PatientToAdminAdapter.Holder>{
    private final Context context;
    private final ArrayList<Patient> patientArrayList;

    public PatientToAdminAdapter(Context context, ArrayList<Patient> patientArrayList) {
        this.context = context;
        this.patientArrayList = patientArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patient_to_admin_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Patient patient = patientArrayList.get(position);
        holder.name.setText(patient.getPatientName());
        holder.email.setText(patient.getPatientEmail());
        holder.phone.setText(patient.getPatientPhone());
        holder.password.setText(patient.getPatientPassword());
        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "patient deleted ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView email;
        private final TextView phone;
        private final TextView password;
        private final Button deleteUser;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.patient_name_admin);
            email = itemView.findViewById(R.id.patient_email_admin);
            phone = itemView.findViewById(R.id.patient_phone_admin);
            password = itemView.findViewById(R.id.patient_password_admin);
            deleteUser = itemView.findViewById(R.id.admin_delete_patient);
        }
    }
}
