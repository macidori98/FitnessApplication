package com.example.fitnessapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.ExerciseVideo;

import java.util.List;

public class TraineeMuscleGroupExercisesAdapter extends RecyclerView.Adapter<TraineeMuscleGroupExercisesAdapter.MyViewHolder> {

    private List<ExerciseVideo> videoList;
    private Context context;

    public TraineeMuscleGroupExercisesAdapter(List<ExerciseVideo> videoList, Context context) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public TraineeMuscleGroupExercisesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_trainee_muscle_group_exercises_elements, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TraineeMuscleGroupExercisesAdapter.MyViewHolder holder, int position) {
        holder.vv_exercise_video.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
        holder.tv_title.setText(videoList.get(position).getTitle());
        holder.tv_trainer.setText(videoList.get(position).getTrainer_id());
        holder.vv_exercise_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iv_exercise_image.setVisibility(View.INVISIBLE);
                holder.vv_exercise_video.seekTo(1);
                holder.vv_exercise_video.start();
            }
        });
        loadImage(Glide.with(context), videoList.get(position).getUrl(), holder.iv_exercise_image);
    }

    private void loadImage(RequestManager glide, String url, ImageView view) {
        glide.load(url).into(view);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title, tv_trainer;
        private VideoView vv_exercise_video;
        private ImageView iv_exercise_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_exercise_image = itemView.findViewById(R.id.imageView__recyclerview_trainee_muscle_group_exercise_image);
            tv_trainer = itemView.findViewById(R.id.textView_recyclerview_trainee_muscle_group_exercise_video_trainer);
            tv_title = itemView.findViewById(R.id.textView_recyclerview_trainee_muscle_group_exercise_video_title);
            vv_exercise_video = itemView.findViewById(R.id.videoView_recyclerview_trainee_muscle_group_exercise_video);
        }
    }
}
