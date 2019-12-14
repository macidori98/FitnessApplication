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
        holder.vv_exercise_video.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
        holder.img_btn_play_pause.setImageResource(android.R.drawable.ic_media_play);
        holder.tv_title.setText(videoList.get(position).getTitle());
        for (int i = 0; i < Constant.TRAINERS_LIST.size(); ++i){
            if (Constant.TRAINERS_LIST.get(i).getId().equals(videoList.get(position).getTrainer_id())){
                holder.tv_trainer.setText(Constant.TRAINERS_LIST.get(i).getTrainer_name());
                break;
            }
        }
        holder.vv_exercise_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.vv_exercise_video.isPlaying()) {
                    holder.vv_exercise_video.pause();
                    Constant.VIDEO_STATUS = "pause";
                    holder.img_btn_play_pause.setImageResource(android.R.drawable.ic_media_play);
                    Constant.VIDEO_POSITION = holder.vv_exercise_video.getCurrentPosition();
                    holder.iv_media_play.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "pause", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.VIDEO_STATUS.equals("pause")) {
                        Constant.VIDEO_STATUS = "resume";
                        holder.img_btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
                        holder.iv_media_play.setVisibility(View.INVISIBLE);
                        holder.vv_exercise_video.seekTo(Constant.VIDEO_POSITION);
                        holder.vv_exercise_video.start();
                        Toast.makeText(context, "resume", Toast.LENGTH_SHORT).show();
                    }
                }

                if (Constant.VIDEO_STATUS.equals("")) {
                    holder.iv_exercise_image.setVisibility(View.INVISIBLE);
                    holder.iv_media_play.setVisibility(View.INVISIBLE);
                    holder.img_btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
                    holder.vv_exercise_video.seekTo(1);
                    holder.vv_exercise_video.start();
                    Toast.makeText(context, "start", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.img_btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.vv_exercise_video.isPlaying()) {
                    holder.vv_exercise_video.pause();
                    Constant.VIDEO_STATUS = "pause";
                    holder.img_btn_play_pause.setImageResource(android.R.drawable.ic_media_play);
                    Constant.VIDEO_POSITION = holder.vv_exercise_video.getCurrentPosition();
                    holder.iv_media_play.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "pause", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.VIDEO_STATUS.equals("pause")) {
                        Constant.VIDEO_STATUS = "resume";
                        holder.img_btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
                        holder.iv_media_play.setVisibility(View.INVISIBLE);
                        holder.vv_exercise_video.seekTo(Constant.VIDEO_POSITION);
                        holder.vv_exercise_video.start();
                        Toast.makeText(context, "resume", Toast.LENGTH_SHORT).show();
                    }
                }

                if (Constant.VIDEO_STATUS.equals("")) {
                    holder.iv_exercise_image.setVisibility(View.INVISIBLE);
                    holder.iv_media_play.setVisibility(View.INVISIBLE);
                    holder.img_btn_play_pause.setImageResource(android.R.drawable.ic_media_pause);
                    holder.vv_exercise_video.seekTo(1);
                    holder.vv_exercise_video.start();
                    Toast.makeText(context, "start", Toast.LENGTH_SHORT).show();
                }

               /* DisplayMetrics metrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                display.getMetrics(metrics);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.vv_exercise_video.getLayoutParams();
                params.width = metrics.widthPixels;
                params.height = metrics.heightPixels;
                params.leftMargin = 0;
                holder.vv_exercise_video.setLayoutParams(params);*/
            }
        });
        loadImage(Glide.with(context), videoList.get(position).getUrl(), holder.iv_exercise_image, holder);
    }

    private void loadImage(RequestManager glide, String url, ImageView view, @NonNull final TraineeMuscleGroupExercisesAdapter.MyViewHolder holder) {
        glide.load(url).into(view);
        int height = holder.iv_exercise_image.getHeight();
        holder.vv_exercise_video.setMinimumHeight(height);
        int width = holder.iv_exercise_image.getWidth();
        holder.vv_exercise_video.setMinimumWidth(width);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title, tv_trainer;
        private VideoView vv_exercise_video;
        private ImageView iv_exercise_image, iv_media_play;
        private ImageButton img_btn_play_pause;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_btn_play_pause = itemView.findViewById(R.id.button_recyclerview_trainee_muscle_group_exercise_media_play_pause);
            iv_media_play = itemView.findViewById(R.id.imageView_recyclerview_trainee_muscle_group_exercise_media_play);
            iv_exercise_image = itemView.findViewById(R.id.imageView_recyclerview_trainee_muscle_group_exercise_image);
            tv_trainer = itemView.findViewById(R.id.textView_recyclerview_trainee_muscle_group_exercise_video_trainer);
            tv_title = itemView.findViewById(R.id.textView_recyclerview_trainee_muscle_group_exercise_video_title);
            vv_exercise_video = itemView.findViewById(R.id.videoView_recyclerview_trainee_muscle_group_exercise_video);
        }
    }
}
