package tikedu.app.tikedu;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import tikedu.app.tikedu.databinding.FragmentSignInBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Implement actual sign in functionality
                String _username = binding.editTextSignInUsername.getText().toString();
                String _password = binding.editTextSignInPassword.getText().toString();

                if(usernameIsValid(_username) && passwordIsValid(_password))
                {
                    SignRepository signRepository = TikEduApplication.getInstance().getSignRepository();
                    signRepository.makeSignInRequest(_username, _password, new SignCallback()
                    {
                        @Override
                        public void onComplete(Pair<Boolean, String> result)
                        {
                            /*if (result.first.booleanValue())
                            {
                                //TODO: figure how to get usertype (and in general any field) from UserRepository asynchrously when it's not know if its finished getting that operation
                                if (_usertype == "Student")
                                {
                                    Intent intent = new Intent(SignUpFragment.this.getActivity(), StudentHomeActivity.class);
                                    startActivity(intent);
                                } else if (_usertype == "Teacher")
                                {
                                    Intent intent = new Intent(SignUpFragment.this.getActivity(), TeacherHomeActivity.class);
                                    startActivity(intent);
                                }
                            } else
                            {
                                Snackbar.make(getActivity().findViewById(R.id.main_activity_coordinatorLayout), result.second, Snackbar.LENGTH_SHORT).show();
                            }*/
                        }
                    });
                }
                else
                {
                    Snackbar.make(getActivity().findViewById(R.id.main_activity_coordinatorLayout), "Username and password combination is not valid", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static boolean usernameIsValid(String username)
    {
        return username.matches("[a-zA-Z0-9]{1,16}");
    }

    public static boolean passwordIsValid(String password)
    {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$");
    }
}