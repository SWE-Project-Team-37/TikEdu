package tikedu.app.tikedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class StudentClassesActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **
        setContentView(R.layout.activity_student_classes);

        // go to "home" screen after pressing home button
        ImageButton classActivity = findViewById(R.id.home_button);
        classActivity.setOnClickListener(view -> {
            // intent = go to home screen
            Intent startIntent = new Intent(getApplicationContext(), StudentHomeActivity.class);
            startActivity(startIntent);
        });
    }
}
