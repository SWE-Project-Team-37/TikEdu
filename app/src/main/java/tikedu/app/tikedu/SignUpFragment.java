package tikedu.app.tikedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import tikedu.app.tikedu.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {

private FragmentSignUpBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentSignUpBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Implement actual sign up functionality
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}