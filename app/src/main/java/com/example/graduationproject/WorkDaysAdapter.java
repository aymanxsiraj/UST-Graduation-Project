package com.example.graduationproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkDaysAdapter extends RecyclerView.Adapter<WorkDaysAdapter.Holder> {
    private final Context context;
    private final ArrayList<WorkDays> workDaysArrayList;

    private onUserClickListener listener;

    public WorkDaysAdapter(Context context, ArrayList<WorkDays> workDaysArrayList, onUserClickListener listener) {
        this.context = context;
        this.workDaysArrayList = workDaysArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.work_days_layout,parent,false);
        return new Holder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        WorkDays workDays = workDaysArrayList.get(position);
        holder.day.setText(workDays.getDay());
        holder.date.setText(workDays.getDate());
        holder.time.setText(workDays.getTime());
    }

    @Override
    public int getItemCount() {
        return workDaysArrayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        public TextView day;
        public TextView date;
        public TextView time;
        public Holder(@NonNull View itemView,onUserClickListener listener) {
            super(itemView);
            day = itemView.findViewById(R.id.work_day_day);
            date = itemView.findViewById(R.id.work_day_date);
            time = itemView.findViewById(R.id.work_time);
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
