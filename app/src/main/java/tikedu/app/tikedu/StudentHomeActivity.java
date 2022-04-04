package tikedu.app.tikedu;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class StudentHomeActivity extends AppCompatActivity
{
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

        // post a video
        ImageButton postButton = findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentHomeActivity.this, ChooseVideoActivity.class);
                startActivity(intent);
            }
        });
    }
}
