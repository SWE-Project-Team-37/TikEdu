package tikedu.app.tikedu;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import tikedu.app.tikedu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        // ** ALWAYS REMOVE TITLE BAR FROM ACTIVITY FIRST **

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}