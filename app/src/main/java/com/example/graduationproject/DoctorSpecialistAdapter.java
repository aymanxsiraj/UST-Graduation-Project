package com.example.graduationproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorSpecialistAdapter extends RecyclerView.Adapter<DoctorSpecialistAdapter.DoctorHolder> {
    private final Context context;
    private final ArrayList<Doctor> doctor;
    private onUserClickListener listener;

    public DoctorSpecialistAdapter(Context context, ArrayList<Doctor> doctor, onUserClickListener listener) {
        this.context = context;
        this.doctor = doctor;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctors_view_to_patient,parent,false);
        return new DoctorHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        Doctor mDoctor = doctor.get(position);
        holder.time.setText(getTime());
        holder.doctor_name.setText(mDoctor.getDoctorName());
        holder.doctor_specialist.setText(mDoctor.getDoctorSpecialist());


    }

    @Override
    public int getItemCount() {
        return doctor.size();
    }

    static class DoctorHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView doctor_name;
        public TextView doctor_specialist;
        private DoctorHolder(View itemView,onUserClickListener listener){
            super(itemView);
            time = itemView.findViewById(R.id.doctor_time);
            doctor_name = itemView.findViewById(R.id.doctor_name);
            doctor_specialist = itemView.findViewById(R.id.doctor_sp);
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

    @SuppressLint("DefaultLocale")
    private String getTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%d:%d", hour, minute);
    }
}
