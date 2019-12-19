package com.example.fitnessapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;

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
        holder.vvTrainerVideo.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
        holder.tvTrainerVideoTitle.setText(videoList.get(position).getTitle());
        holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_play);
        holder.tvTrainerVideoDescription.setText(videoList.get(position).getDescription());
        holder.tvTrainerVideoMuscleGroup.setText(videoList.get(position).getMuscleGroup());
        holder.vvTrainerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.imgBtnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playStopButton(holder);
            }
        });
        holder.imgBtnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullScreenButton(holder);
            }
        });
        loadImage(Glide.with(context), videoList.get(position).getUrl(), holder.ivTrainerImage);
    }

    private void loadImage(RequestManager glide, String url, ImageView view) {
        glide.load(url).into(view);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private VideoView vvTrainerVideo;
        private TextView tvTrainerVideoTitle, tvTrainerVideoMuscleGroup,
                tvTrainerVideoDescription;
        private ImageView ivTrainerImage, ivStartIcon;
        private ImageButton imgBtnPlayPause, imgBtnFullScreen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBtnFullScreen = itemView.findViewById(R.id.imageButton_recyclerview_trainee_trainer_my_content_full_screen);
            imgBtnPlayPause = itemView.findViewById(R.id.imageButton_recyclerview_trainee_trainer_my_content_media_play_pause);
            ivStartIcon = itemView.findViewById(R.id.imageView_recyclerview_trainee_trainer_my_content_media_play);
            ivTrainerImage = itemView.findViewById(R.id.imageView_recyclerview_trainee_trainer_my_content_image);
            vvTrainerVideo = itemView.findViewById(R.id.videoView_recyclerview_trainee_trainer_my_content_video);
            tvTrainerVideoMuscleGroup = itemView.findViewById(R.id.textView_recyclerview_trainee_trainer_my_content_muscle_group);
            tvTrainerVideoTitle = itemView.findViewById(R.id.textView_recyclerview_trainee_trainer_my_content_title);
            tvTrainerVideoDescription = itemView.findViewById(R.id.textView_recyclerview_trainee_trainer_my_content_description);
        }
    }

    private void fullScreenButton(@NonNull final TraineeTrainerMyContentAdapter.MyViewHolder holder){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.vvTrainerVideo.getLayoutParams();
        if (Constant.FULL_SCREEN_STATUS.equals(Constant.MINI)){
            params.width = metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            holder.vvTrainerVideo.setLayoutParams(params);
            if (Constant.VIDEO_STATUS.equals("")){
                holder.ivTrainerImage.setLayoutParams(params);
            }
            Constant.FULL_SCREEN_STATUS=Constant.FULL;
        } else {
            Constant.FULL_SCREEN_STATUS=Constant.MINI;
            params.width = (int) (Constant.WIDTH*metrics.density);
            params.height = (int) (Constant.HEIGHT*metrics.density);
            params.leftMargin = 0;
            holder.vvTrainerVideo.setLayoutParams(params);
        }
    }

    private void playStopButton(@NonNull final TraineeTrainerMyContentAdapter.MyViewHolder holder){
        if (holder.vvTrainerVideo.isPlaying()) {
            holder.vvTrainerVideo.pause();
            Constant.VIDEO_STATUS = Constant.PAUSE;
            holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            Constant.VIDEO_POSITION = holder.vvTrainerVideo.getCurrentPosition();
            holder.ivStartIcon.setVisibility(View.VISIBLE);
        } else {
            if (Constant.VIDEO_STATUS.equals(Constant.PAUSE)) {
                holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                Constant.VIDEO_STATUS = Constant.RESUME;
                holder.ivStartIcon.setVisibility(View.INVISIBLE);
                holder.vvTrainerVideo.seekTo(Constant.VIDEO_POSITION);
                holder.vvTrainerVideo.start();
            }

            if (Constant.VIDEO_STATUS.equals("")) {
                holder.ivTrainerImage.setVisibility(View.INVISIBLE);
                holder.ivStartIcon.setVisibility(View.INVISIBLE);
                holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                holder.vvTrainerVideo.seekTo(1);
                holder.vvTrainerVideo.start();
            }
        }
    }
}
