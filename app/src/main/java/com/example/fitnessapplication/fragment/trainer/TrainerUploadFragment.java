package com.example.fitnessapplication.fragment.trainer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.MainActivity;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class TrainerUploadFragment extends Fragment {

    public final static String TAG = TrainerUploadFragment.class.getSimpleName();

    private View view;
    private EditText etUploadTitle, etUploadDescription;
    private Button btnUpload;
    private Spinner spMuscleGroup;
    private final int PICK_IMAGE_REQUEST = 1;
    private ArrayAdapter<CharSequence> adapter;
    private Button btnAddImage;
    private Uri selectedImage;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainer_upload, container, false);
        initializeViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpload();
            }
        });
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    private void initializeViewElements(View view) {
        etUploadTitle = view.findViewById(R.id.et_trainer_upload_title);
        etUploadDescription = view.findViewById(R.id.et_trainer_upload_description);
        spMuscleGroup = view.findViewById(R.id.sp_upload_content);
        btnUpload = view.findViewById(R.id.btn_trainer_upload_upload);
        btnAddImage = view.findViewById(R.id.btn_upload_add_video);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        setupSpinner(view);
    }

    private void onClickUpload(){
        FirebaseDatabase p = FirebaseDatabase.getInstance();
        final DatabaseReference ref = p.getReference(Constant.EXERCISE_VIDEO);
        final String key = ref.push().getKey();
        final String title = etUploadTitle.getText().toString();
        final String description = etUploadDescription.getText().toString();
        final int idx = (int) spMuscleGroup.getSelectedItemId();
        final String muscleGroupSelected = adapter.getItem(idx).toString();


        if (selectedImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference imgref = storageReference.child("video-"+ UUID.randomUUID().toString());
            imgref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (taskSnapshot.getMetadata() != null) {
                                if (taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            progressDialog.dismiss();
                                            String imageUrl = uri.toString();
                                            //createNewPost(imageUrl);
                                            Toast.makeText(getContext(), R.string.upload_successful, Toast.LENGTH_SHORT).show();
                                            ExerciseVideo exerciseVideo = new ExerciseVideo(key,Constant.CURRENT_USER.getId(),imageUrl,title,muscleGroupSelected,description);
                                            ref.child(key).setValue(exerciseVideo);
                                        }
                                    });
                                }
                            }
                        }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity().getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void setupSpinner(View view){
        spMuscleGroup = view.findViewById(R.id.sp_upload_content);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trainee_muscle_groups, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spMuscleGroup.setAdapter(adapter);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 1:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    Toast.makeText(getContext(), R.string.video_selected, Toast.LENGTH_SHORT).show();
                    break;
            }
    }
}
