package com.example.graduationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DoctorToAdminAdapter extends RecyclerView.Adapter<DoctorToAdminAdapter.Holder>{
    private final Context context;
    private final ArrayList<Doctor> doctorArrayList;

    private onUserClickListener listener;



    public DoctorToAdminAdapter(Context context, ArrayList<Doctor> doctorArrayList, onUserClickListener listener) {
        this.context = context;
        this.doctorArrayList = doctorArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_to_admin_layout,parent,false);
        return new Holder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Doctor doctor = doctorArrayList.get(position);
        holder.name.setText(doctor.getDoctorName());
        holder.email.setText(doctor.getDoctorEmail());
        holder.phone.setText(doctor.getDoctorPhone());
        holder.password.setText(doctor.getDoctorPassword());
        holder.specialist.setText(doctor.getDoctorSpecialist());
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

        public Holder(@NonNull View itemView, onUserClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.doctor_name_admin);
            email = itemView.findViewById(R.id.doctor_email_admin);
            phone = itemView.findViewById(R.id.doctor_phone_admin);
            password = itemView.findViewById(R.id.doctor_password_admin);
            specialist = itemView.findViewById(R.id.doctor_specialist_admin);


            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onUserClick(position);
                    }
                }
            });
        }
    }


    public interface onUserClickListener{
        void onUserClick(int position);
    }

    public void setOnUserClickListener(onUserClickListener listener){
        this.listener = listener;
    }
}
