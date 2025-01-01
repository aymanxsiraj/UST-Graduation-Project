package com.example.graduationproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConfirmationAdapter  extends RecyclerView.Adapter<ConfirmationAdapter.ConfirmationHolder> {

    private final Context context;
    private final ArrayList<Confirmation> confirmations;
    private onUserClickListener listener;
    private final DatabaseReference reference;


    public ConfirmationAdapter(Context context, ArrayList<Confirmation> confirmations) {
        this.context = context;
        this.confirmations = confirmations;
        reference = FirebaseDatabase.getInstance("https://graduation-project-6b165-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Status").child("Doctors");
    }

    @NonNull
    @Override
    public ConfirmationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctors_confirmation_layout,parent,false);
        return new ConfirmationHolder(view,listener);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ConfirmationHolder holder, int position) {
        Confirmation mConfirmation = confirmations.get(position);
        holder.email.setText(mConfirmation.getEmail());
        holder.confirm.setText(mConfirmation.getConfirm());


        holder.reject.setOnClickListener(v -> {
            setDoctorAccountConfirmationStatus(mConfirmation.getUID(),"No");
            Snackbar.make(v, "the request = REJECT ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            confirmations.clear();

        });
        holder.accept.setOnClickListener(v -> {
            setDoctorAccountConfirmationStatus(mConfirmation.getUID(),"Yes");
            Snackbar.make(v, "the request = ACCEPT ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            confirmations.clear();
        });
    }

    @Override
    public int getItemCount() {
        return confirmations.size();
    }

    static class ConfirmationHolder extends RecyclerView.ViewHolder{
        public TextView email;
        public TextView confirm;
        public Button accept;
        public Button reject;
        public ConfirmationHolder(@NonNull View itemView,onUserClickListener listener) {
            super(itemView);
            email = itemView.findViewById(R.id.doctor_email);
            confirm = itemView.findViewById(R.id.doctor_status);
            accept = itemView.findViewById(R.id.confirm);
            reject = itemView.findViewById(R.id.reject);


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

    private void setDoctorAccountConfirmationStatus(String UID,String STATUS){
        try {
            reference.child(UID).child("confirm").setValue(STATUS);
        }
        catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
