package tikedu.app.tikedu;

import android.content.Intent;
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

        // change like button color when it is pressed
        ImageButton likeButton = findViewById(R.id.like_button);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "selected" states are defined in like_button_tint_color.xml
                likeButton.setSelected(!likeButton.isSelected());
            }
        });

        // change midlike button color when it is pressed
        ImageButton midlikeButton = findViewById(R.id.midlike_button);
        midlikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "selected" states are defined in midlike_button_tint_color.xml
                midlikeButton.setSelected(!midlikeButton.isSelected());
            }
        });

        // change dislike button color when it is pressed
        ImageButton dislikeButton = findViewById(R.id.dislike_button);
        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "selected" states are defined in dislike_button_tint_color.xml
                dislikeButton.setSelected(!dislikeButton.isSelected());
            }
        });
    }
}
