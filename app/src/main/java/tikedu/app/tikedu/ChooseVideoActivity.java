package tikedu.app.tikedu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.VideoView;

import java.util.Objects;

public class ChooseVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **
        setContentView(R.layout.activity_choose_video);

        ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        Uri selectedVideo = data.getData();
                        VideoView chosenVideoView = findViewById(R.id.chosenVideoView);
                        chosenVideoView.setVideoURI(selectedVideo);
                        chosenVideoView.start();
                    }
                }
        );

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        launchGallery.launch(intent);

        ImageButton homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseVideoActivity.this, StudentHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}