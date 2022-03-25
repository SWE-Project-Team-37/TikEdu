package tikedu.app.tikedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class StudentCommentActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_comment);

        ImageButton postButton = findViewById(R.id.post_button);
        postButton.setOnClickListener(view -> {
            Intent intent = new Intent(StudentCommentActivity.this, ChooseVideoActivity.class);
            startActivity(intent);
        });
    }
}