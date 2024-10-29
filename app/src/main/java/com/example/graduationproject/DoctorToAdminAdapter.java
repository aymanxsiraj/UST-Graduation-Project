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

public class DoctorToAdminAdapter extends RecyclerView.Adapter<DoctorToAdminAdapter.Holder>{
    private final Context context;
    private final ArrayList<Doctor> doctorArrayList;

    public DoctorToAdminAdapter(Context context, ArrayList<Doctor> doctorArrayList) {
        this.context = context;
        this.doctorArrayList = doctorArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_to_admin_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Doctor doctor = doctorArrayList.get(position);
        holder.name.setText(doctor.getDoctorName());
        holder.email.setText(doctor.getDoctorEmail());
        holder.phone.setText(doctor.getDoctorPhone());
        holder.password.setText(doctor.getDoctorPassword());
        holder.specialist.setText(doctor.getDoctorSpecialist());
        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "doctor deleted ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView email;
        private final TextView phone;
        private final TextView password;
        private final TextView specialist;
        private final Button deleteUser;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctor_name_admin);
            email = itemView.findViewById(R.id.doctor_email_admin);
            phone = itemView.findViewById(R.id.doctor_phone_admin);
            password = itemView.findViewById(R.id.doctor_password_admin);
            specialist = itemView.findViewById(R.id.doctor_specialist_admin);
            deleteUser = itemView.findViewById(R.id.admin_delete_doctor);
        }
    }
}
