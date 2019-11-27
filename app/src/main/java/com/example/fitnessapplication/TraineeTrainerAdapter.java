package com.example.fitnessapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TraineeTrainerAdapter extends RecyclerView.Adapter<TraineeTrainerAdapter.MyViewHolder>  {

    private Context context;
    private List<Trainer> list_trainers;
    private OnItemClickListener listener;
    private int selectedItem;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_trainer_name, tv_trainer_username;

        public MyViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            tv_trainer_name = itemView.findViewById(R.id.textView_recyclerview_trainee_trainers_trainer_name);
            tv_trainer_username = itemView.findViewById(R.id.textView_recyclerview_trainee_trainers_trainer_username);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            selectedItem = position;
                        }
                    }
                }
            });
        }
    }


    public TraineeTrainerAdapter(Context context, List<Trainer> trainers) {
        this.context = context;
        this.list_trainers = trainers;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_trainer_name.setText(list_trainers.get(position).getUser_id());
        holder.tv_trainer_username.setText(list_trainers.get(position).getUsername());
    }

    @NonNull
    @Override
    public TraineeTrainerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_trainee_trainers_list_element, parent, false);
        return new TraineeTrainerAdapter.MyViewHolder(itemView,listener);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list_trainers.size();}

    public int getSelectedPosition(){
        return selectedItem;
    }

}
