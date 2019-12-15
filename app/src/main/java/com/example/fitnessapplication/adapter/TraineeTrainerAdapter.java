package com.example.fitnessapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.interfaces.OnItemClickListener;
import com.example.fitnessapplication.model.Trainer;

import java.util.List;

public class TraineeTrainerAdapter extends RecyclerView.Adapter<TraineeTrainerAdapter.MyViewHolder> {

    private Context context;
    private List<Trainer> trainerList;
    private OnItemClickListener listener;

    public TraineeTrainerAdapter(Context context, List<Trainer> trainers) {
        this.context = context;
        this.trainerList = trainers;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTrainerName.setText(trainerList.get(position).getTrainerName());
        holder.tvTrainerUsername.setText(trainerList.get(position).getTrainerUsername());
    }

    @NonNull
    @Override
    public TraineeTrainerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_trainee_trainers_list_element, parent, false);
        return new TraineeTrainerAdapter.MyViewHolder(itemView, listener);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return trainerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTrainerName, tvTrainerUsername;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTrainerName = itemView.findViewById(R.id.textView_recyclerview_trainee_trainers_trainer_name);
            tvTrainerUsername = itemView.findViewById(R.id.textView_recyclerview_trainee_trainers_trainer_username);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
