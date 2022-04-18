package tikedu.app.tikedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class StudentPostActivity extends AppCompatActivity
{
    ArrayList<Pair<Uri, Boolean>> postedVideos;

    int i = 0;
    private int PICK_VIDEO_FROM_GALLERY_REQUEST = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_post);
        postedVideos = TikEduApplication.getInstance().getPostedVideos();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

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

        try
        {
            androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayoutPosts);
            gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
            int numOfThumbnails = postedVideos.size();

            gridLayout.setColumnCount(3);
            gridLayout.setRowCount(3);

            Log.d("Sign Up", String.valueOf(numOfThumbnails));
            for(int j = 0; j < postedVideos.size(); j++)
            {
                Pair<Uri, Boolean> realPair = postedVideos.get(j);
                if(!realPair.second.booleanValue())
                {
                    postedVideos.set(j, new Pair<Uri, Boolean>(realPair.first, Boolean.TRUE));
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.height = 350;
                    layoutParams.width = 350;
                    int currentCol = i % 3;
                    int currentRow = i / 3;
                    layoutParams.columnSpec = GridLayout.spec(currentCol, 1, 1);
                    layoutParams.rowSpec = GridLayout.spec(currentRow, 1, 1);

                    Log.d("Sign up", "Test");
                    MediaMetadataRetriever media = new MediaMetadataRetriever();
                    media.setDataSource(FileUtils.getPath(this, realPair.first));
                    Bitmap extractedImage = media.getFrameAtTime(0);
                    ImageView imageView = new ImageView(this);
                    imageView.setImageBitmap(extractedImage);

                    gridLayout.addView(imageView, layoutParams);
                    ++i;
                }
            }

        } catch (Exception e)
        {
            Log.d("Sign Up", "", e);
        }

    }

    /*public void addThumbnail( Uri uri)
    {
        androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayoutPosts);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.height = 200;
        layoutParams.width = 200;
        int currentCol = i % 3;
        int currentRow = i / 3;
        layoutParams.columnSpec = GridLayout.spec(currentCol, 1, 1);
        layoutParams.rowSpec = GridLayout.spec(currentRow, 1, 1);

        Log.d("Sign up", "Test");
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(FileUtils.getPath(this, uri));
        Bitmap extractedImage = media.getFrameAtTime(0);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(extractedImage);

        gridLayout.addView(imageView, layoutParams);
        ++i;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            TikEduApplication.getInstance().addPostedVideo(uri, Boolean.FALSE);
            //addThumbnail(uri);
        }
    }

}