package com.example.graduationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientsAppointmentsAdapter extends RecyclerView.Adapter<PatientsAppointmentsAdapter.Holder> {

    private final Context context;
    private final ArrayList<PatientReport> patientReportArrayList;
    private onUserClickListener listener;

    private onUserLongClickListener longClickListener;

    public PatientsAppointmentsAdapter(Context context, ArrayList<PatientReport> patientReportArrayList, onUserClickListener listener, onUserLongClickListener longClickListener) {
        this.context = context;
        this.patientReportArrayList = patientReportArrayList;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.patients_appointments_layout,parent,false);
        return new Holder(view,listener,longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        PatientReport patientReport = patientReportArrayList.get(position);
        holder.date.setText(patientReport.getDate());
        holder.name.setText(patientReport.getName());
        holder.age.setText(String.format("age : %s", patientReport.getAge()));
        holder.phone.setText(patientReport.getPhone());
    }

    @Override
    public int getItemCount() {
        return patientReportArrayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        private final TextView date;
        private final TextView name;
        private final TextView age;
        private final TextView phone;
        public Holder(@NonNull View itemView,onUserClickListener listener,onUserLongClickListener longClickListener) {
            super(itemView);
            date = itemView.findViewById(R.id.date_from_report);
            name = itemView.findViewById(R.id.name_from_report);
            age = itemView.findViewById(R.id.age_from_report);
            phone = itemView.findViewById(R.id.phone_from_report);

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onUserClick(position);
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                if(longClickListener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        longClickListener.onUserLongClick(position);
                    }
                }
                return false;
            });
        }
    }
    public interface onUserClickListener{
        void onUserClick(int position);
    }

    public void setOnUserClickListener(onUserClickListener listener){
        this.listener = listener;
    }

    //////////////////////

    public interface onUserLongClickListener{
        void onUserLongClick(int position);
    }

    public void setOnUserLongClickListener(onUserLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

}
