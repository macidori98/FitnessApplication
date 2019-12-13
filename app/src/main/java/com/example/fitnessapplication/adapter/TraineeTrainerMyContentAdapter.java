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

public class TraineeTrainerMyContentAdapter extends RecyclerView.Adapter<TraineeTrainerMyContentAdapter.MyViewHolder> {

    private Context context;
    private List<ExerciseVideo> videoList;

    public TraineeTrainerMyContentAdapter(Context context, List<ExerciseVideo> exerciseVideos) {
        this.context = context;
        this.videoList = exerciseVideos;
    }

    @NonNull
    @Override
    public TraineeTrainerMyContentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_trainee_trainer_my_content_element, parent, false);
        return new TraineeTrainerMyContentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TraineeTrainerMyContentAdapter.MyViewHolder holder, int position) {
        holder.vv_trainer_video.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
        holder.tv_trainer_video_title.setText(videoList.get(position).getTitle());
        holder.tv_trainer_video_description.setText(videoList.get(position).getDescription());
        holder.tv_trainer_video_muscle_group.setText(videoList.get(position).getMuscle_group());
        holder.vv_trainer_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iv_trainer_image.setVisibility(View.INVISIBLE);
                holder.vv_trainer_video.seekTo(1);
                holder.vv_trainer_video.start();
            }
        });
        loadImage(Glide.with(context), videoList.get(position).getUrl(), holder.iv_trainer_image);
    }

    private void loadImage(RequestManager glide, String url, ImageView view) {
        glide.load(url).into(view);
    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private VideoView vv_trainer_video;
        private TextView tv_trainer_video_title, tv_trainer_video_muscle_group,
                tv_trainer_video_description;
        private ImageView iv_trainer_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_trainer_image = itemView.findViewById(R.id.imageView_recyclerview_trainee_trainer_my_content_image);
            vv_trainer_video = itemView.findViewById(R.id.videoView_recyclerview_trainee_trainer_my_content_video);
            tv_trainer_video_muscle_group = itemView.findViewById(R.id.textView_recyclerview_trainee_trainer_my_content_muscle_group);
            tv_trainer_video_title = itemView.findViewById(R.id.textView_recyclerview_trainee_trainer_my_content_title);
            tv_trainer_video_description = itemView.findViewById(R.id.textView_recyclerview_trainee_trainer_my_content_description);
        }
    }
}
