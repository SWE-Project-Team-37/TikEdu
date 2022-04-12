package tikedu.app.tikedu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentHomeActivity extends AppCompatActivity
{
    private int PICK_VIDEO_FROM_GALLERY_REQUEST = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **
        setContentView(R.layout.activity_student_home);

        // go to "class" screen after pressing class(computer) button
        ImageButton classActivity = findViewById(R.id.computer_class_button);
        classActivity.setOnClickListener(view -> {
            // intent = go to class-selection screen
            Intent startIntent = new Intent(getApplicationContext(), StudentClassesActivity.class);
            startActivity(startIntent);
        });

        // find all "reaction" buttons
        ImageButton likeButton = findViewById(R.id.like_button);
        ImageButton midlikeButton = findViewById(R.id.midlike_button);
        ImageButton dislikeButton = findViewById(R.id.dislike_button);
        // change like button color when it is pressed
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "selected" states are defined in like_button_tint_color.xml
                likeButton.setSelected(!likeButton.isSelected());
                // if the like button is selected, deselect the others
                if (likeButton.isSelected())
                {
                    midlikeButton.setSelected(false);
                    dislikeButton.setSelected(false);
                }
            }
        });

        // change midlike button color when it is pressed
        midlikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "selected" states are defined in midlike_button_tint_color.xml
                midlikeButton.setSelected(!midlikeButton.isSelected());
                // if the midlike button is selected, deselect the others
                if (midlikeButton.isSelected())
                {
                    likeButton.setSelected(false);
                    dislikeButton.setSelected(false);
                }
            }
        });

        // change dislike button color when it is pressed
        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "selected" states are defined in dislike_button_tint_color.xml
                dislikeButton.setSelected(!dislikeButton.isSelected());
                // if the dislike button is selected, deselect the others
                if (dislikeButton.isSelected())
                {
                    likeButton.setSelected(false);
                    midlikeButton.setSelected(false);
                }
            }
        });



        /*ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        // select video
                        Intent data = result.getData();
                        Uri selectedVideo = data.getData();
                        String path = selectedVideo.getPath();
                        System.out.println("Selected video path: " + path);

                        //uploadVideo(path);
                    }
                }
        );*/

        // post a video
        ImageButton postButton = findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////Intent intent = new Intent(StudentHomeActivity.this, ChooseVideoActivity.class);
                ////startActivity(intent);

                //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                //launchGallery.launch(intent);
                Intent intent = new Intent();
                // only show videos
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // choose video
                startActivityForResult(
                        Intent.createChooser(intent, "Select Video"),
                        PICK_VIDEO_FROM_GALLERY_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            uploadFile(uri);
        }
    }

    private void uploadFile(Uri fileUri) {
        //RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, "Some Random Description");


        RequestBody userIdPart = RequestBody.create(MultipartBody.FORM, "123");
        RequestBody classIdPart = RequestBody.create(MultipartBody.FORM, "456");

        File file = FileUtils.getFile(this, fileUri);

        RequestBody filePart = RequestBody.create(
                MediaType.parse(getContentResolver().getType(fileUri)),
                file);

        MultipartBody.Part newFile = MultipartBody.Part.createFormData("video", file.getName(), filePart);

        // Create Retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.0.78:8080/") // we replace this (http://10.0.2.2:3000/api/) with IP (153.33.76.164)? 153.33.76.164
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // get client and call object for request
        UserClient client = retrofit.create(UserClient.class);

        // execute the request
        Call<ResponseBody> call = client.uploadVideo(/*descriptionPart*/ userIdPart, classIdPart, newFile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(StudentHomeActivity.this, "Oh yeahhh", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(StudentHomeActivity.this, "No good", Toast.LENGTH_SHORT).show();
                Log.d("VideoError", "", throwable);
            }
        });
    }

}
