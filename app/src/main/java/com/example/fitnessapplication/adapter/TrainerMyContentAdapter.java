package com.example.fitnessapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import com.example.fitnessapplication.MainActivity;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.fragment.changes_dialogs.DeleteContentDialog;
import com.example.fitnessapplication.fragment.changes_dialogs.UpdateContentDialog;
import com.example.fitnessapplication.interfaces.RefreshListener;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TrainerMyContentAdapter extends RecyclerView.Adapter<TrainerMyContentAdapter.MyViewHolder> implements RefreshListener {

    private List<ExerciseVideo> videoList;
    private Context context;

    public TrainerMyContentAdapter(List<ExerciseVideo> videoList, Context context){
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public TrainerMyContentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_trainer_my_content_elements, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.vvExerciseVideo.setVideoURI(Uri.parse(videoList.get(position).getUrl()));
        holder.tvTitle.setText(videoList.get(position).getTitle());
        holder.tvMuscleGroup.setText(videoList.get(position).getMuscleGroup());
        holder.tvDescription.setText(videoList.get(position).getDescription());
        holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_play);
        holder.video_id = videoList.get(position).getId();
        holder.imgBtnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playStopButton(holder);
            }
        }); 
        holder.vvExerciseVideo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "long", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        holder.btnEditMyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditContent(position);
            }
        });
        holder.btnDeleteMyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContent(position);
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

    @Override
    public void refreshAdapter(int position,ExerciseVideo exerciseVideo) {
        videoList.set(position,exerciseVideo);
        notifyItemChanged(position);
    }

    @Override
    public void deleteFromAdapter(int position) {
        videoList.remove(position);
        notifyItemChanged(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvMuscleGroup, tvDescription;
        VideoView vvExerciseVideo;
        private ImageView ivTrainerImage, ivStartIcon;
        private ImageButton imgBtnPlayPause, imgBtnFullScreen;
        private Button btnEditMyContent;
        private Button btnDeleteMyContent;
        private String video_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textView_recyclerview_my_content_video_title);
            tvMuscleGroup = itemView.findViewById(R.id.textView_recyclerview_my_content_video_muscleGroup);
            tvDescription = itemView.findViewById(R.id.textView_recyclerview_my_content_video_description);
            vvExerciseVideo = itemView.findViewById(R.id.videoView_recyclerview_my_content_video);
            imgBtnFullScreen = itemView.findViewById(R.id.imageButton_recyclerview_trainer_my_content_full_screen);
            imgBtnPlayPause = itemView.findViewById(R.id.imageButton_recyclerview_trainer_my_content_media_play_pause);
            ivStartIcon = itemView.findViewById(R.id.imageView_recyclerview_trainer_my_content_media_play);
            ivTrainerImage = itemView.findViewById(R.id.imageView_recyclerview_trainer_my_content_image);
            btnEditMyContent = itemView.findViewById(R.id.button_recyclerview_edit_my_content);
            btnDeleteMyContent = itemView.findViewById(R.id.button_delete);
        }
    }

    //Starts the edit content dialog
    private void EditContent(int position){
        UpdateContentDialog updateContentDialog = new UpdateContentDialog(videoList.get(position),position);
        updateContentDialog.setListener(this);
        updateContentDialog.show(((MainActivity)context).getSupportFragmentManager(), "Change Content");
    }

    //Starts the delete content dialog
    private void deleteContent(int position){
        DeleteContentDialog deleteContentDialog = new DeleteContentDialog(videoList.get(position),position);
        deleteContentDialog.setListener(this);
        deleteContentDialog.show(((MainActivity)context).getSupportFragmentManager(), "Delete Content");
    }

    private void fullScreenButton(@NonNull final TrainerMyContentAdapter.MyViewHolder holder){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.vvExerciseVideo.getLayoutParams();
        if (Constant.FULL_SCREEN_STATUS.equals("mini")){
            params.width = metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            holder.vvExerciseVideo.setLayoutParams(params);
            if (Constant.VIDEO_STATUS.equals("")){
                holder.ivTrainerImage.setLayoutParams(params);
            }
            Constant.FULL_SCREEN_STATUS="full";
        } else {
            Constant.FULL_SCREEN_STATUS="mini";
            params.width = (int) (400*metrics.density);
            params.height = (int) (200*metrics.density);
            params.leftMargin = 0;
            holder.vvExerciseVideo.setLayoutParams(params);
        }
    }

    private void playStopButton(@NonNull final TrainerMyContentAdapter.MyViewHolder holder){
        if (holder.vvExerciseVideo.isPlaying()) {
            holder.vvExerciseVideo.pause();
            Constant.VIDEO_STATUS = "pause";
            holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            Constant.VIDEO_POSITION = holder.vvExerciseVideo.getCurrentPosition();
            holder.ivStartIcon.setVisibility(View.VISIBLE);
            Toast.makeText(context, "pause", Toast.LENGTH_SHORT).show();
        } else {
            if (Constant.VIDEO_STATUS.equals("pause")) {
                holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                Constant.VIDEO_STATUS = "resume";
                holder.ivStartIcon.setVisibility(View.INVISIBLE);
                holder.vvExerciseVideo.seekTo(Constant.VIDEO_POSITION);
                holder.vvExerciseVideo.start();
                Toast.makeText(context, "resume", Toast.LENGTH_SHORT).show();
            }
            if (Constant.VIDEO_STATUS.equals("")) {
                holder.ivTrainerImage.setVisibility(View.INVISIBLE);
                holder.ivStartIcon.setVisibility(View.INVISIBLE);
                holder.imgBtnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                holder.vvExerciseVideo.seekTo(1);
                holder.vvExerciseVideo.start();
                Toast.makeText(context, "start", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
