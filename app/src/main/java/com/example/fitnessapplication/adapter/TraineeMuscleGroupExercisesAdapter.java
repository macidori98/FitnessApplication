package com.example.fitnessapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;

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
        holder.vvExerciseVideo.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
        holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_play);
        holder.tvTitle.setText(videoList.get(position).getTitle());
        for (int i = 0; i < Constant.TRAINERS_LIST.size(); ++i) {
            if (Constant.TRAINERS_LIST.get(i).getId().equals(videoList.get(position).getTrainerId())) {
                holder.tvTrainer.setText(Constant.TRAINERS_LIST.get(i).getTrainerName());
                break;
            }
        }
        holder.vvExerciseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.vvExerciseVideo.isPlaying()) {
                    holder.vvExerciseVideo.pause();
                    Constant.VIDEO_STATUS = "pause";
                    holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_play);
                    Constant.VIDEO_POSITION = holder.vvExerciseVideo.getCurrentPosition();
                    holder.ivMediaPlay.setVisibility(View.VISIBLE);
                    //Toast.makeText(context, "pause", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.VIDEO_STATUS.equals("pause")) {
                        Constant.VIDEO_STATUS = "resume";
                        holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                        holder.ivMediaPlay.setVisibility(View.INVISIBLE);
                        holder.vvExerciseVideo.seekTo(Constant.VIDEO_POSITION);
                        holder.vvExerciseVideo.start();
                        // Toast.makeText(context, "resume", Toast.LENGTH_SHORT).show();
                    }
                }

                if (Constant.VIDEO_STATUS.equals("")) {
                    holder.ivExerciseImage.setVisibility(View.INVISIBLE);
                    holder.ivMediaPlay.setVisibility(View.INVISIBLE);
                    holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                    holder.vvExerciseVideo.seekTo(1);
                    holder.vvExerciseVideo.start();
                    //Toast.makeText(context, "start", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imgBtnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.vvExerciseVideo.isPlaying()) {
                    holder.vvExerciseVideo.pause();
                    Constant.VIDEO_STATUS = "pause";
                    holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_play);
                    Constant.VIDEO_POSITION = holder.vvExerciseVideo.getCurrentPosition();
                    holder.ivMediaPlay.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "pause", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.VIDEO_STATUS.equals("pause")) {
                        Constant.VIDEO_STATUS = "resume";
                        holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                        holder.ivMediaPlay.setVisibility(View.INVISIBLE);
                        holder.vvExerciseVideo.seekTo(Constant.VIDEO_POSITION);
                        holder.vvExerciseVideo.start();
                        Toast.makeText(context, "resume", Toast.LENGTH_SHORT).show();
                    }
                }

                if (Constant.VIDEO_STATUS.equals("")) {
                    holder.ivExerciseImage.setVisibility(View.INVISIBLE);
                    holder.ivMediaPlay.setVisibility(View.INVISIBLE);
                    holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                    holder.vvExerciseVideo.seekTo(1);
                    holder.vvExerciseVideo.start();
                    Toast.makeText(context, "start", Toast.LENGTH_SHORT).show();
                }

               /* DisplayMetrics metrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                display.getMetrics(metrics);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.vvExerciseVideo.getLayoutParams();
                params.width = metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                holder.vvExerciseVideo.setLayoutParams(params);*/
            }
        });
        loadImage(Glide.with(context), videoList.get(position).getUrl(), holder.ivExerciseImage, holder);
    }

    private void loadImage(RequestManager glide, String url, ImageView view, @NonNull final TraineeMuscleGroupExercisesAdapter.MyViewHolder holder) {
        glide.load(url).into(view);
        int height = holder.ivExerciseImage.getHeight();
        holder.vvExerciseVideo.setMinimumHeight(height);
        int width = holder.ivExerciseImage.getWidth();
        holder.vvExerciseVideo.setMinimumWidth(width);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvTrainer;
        private VideoView vvExerciseVideo;
        private ImageView ivExerciseImage, ivMediaPlay;
        private ImageButton imgBtnPlayPause;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBtnPlayPause = itemView.findViewById(R.id.button_recyclerview_trainee_muscle_group_exercise_media_play_pause);
            ivMediaPlay = itemView.findViewById(R.id.imageView_recyclerview_trainee_muscle_group_exercise_media_play);
            ivExerciseImage = itemView.findViewById(R.id.imageView_recyclerview_trainee_muscle_group_exercise_image);
            tvTrainer = itemView.findViewById(R.id.textView_recyclerview_trainee_muscle_group_exercise_video_trainer);
            tvTitle = itemView.findViewById(R.id.textView_recyclerview_trainee_muscle_group_exercise_video_title);
            vvExerciseVideo = itemView.findViewById(R.id.videoView_recyclerview_trainee_muscle_group_exercise_video);
        }
    }
}
