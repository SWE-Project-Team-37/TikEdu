package tikedu.app.tikedu;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import tikedu.app.tikedu.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    //private fields for sign-up functionality
    private List<Pair<String,String>> userSignUpFields;
    private boolean hasEditedUsername = false;
    private boolean usernameIsValid = false;
    private boolean hasEditedPassword = false;
    private String usertype = "";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        userSignUpFields = new ArrayList<Pair<String,String>>();
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //clears the default example text so user doesn't have to manually delete or backspace


        //remove username validation hint textview when no longer editing username
        //clears the default example text so user doesn't have to manually delete or backspace
        binding.editTextSignUpUsername.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    binding.usernameValidationHint.setVisibility(View.GONE);
                }
                else if(hasFocus && !hasEditedUsername)
                {
                        hasEditedUsername = true;
                        binding.editTextSignUpUsername.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });

        //performs validation of the input username
        //i.e. make sure the username is not empty, does not exceed 16 characters, and consists only of alphanumeric characters
        binding.editTextSignUpUsername.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            //actually perform validation
            @Override
            public void afterTextChanged(Editable editable)
            {
                validateUsername(editable);
            }
        });


        //remove username validation hint textview when no longer editing username
        //also clears the default example text so user doesn't have to manually delete or backspace
        binding.editTextSignUpPassword.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    binding.passwordValidationHint.setVisibility(View.GONE);
                }
                else if(hasFocus && !hasEditedPassword)
                {
                    hasEditedPassword = true;
                    binding.editTextSignUpPassword.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });


        //performs validation of the input Password
        //i.e. make sure the Password is not empty, does not exceed 16 characters, and consists only of alphanumeric characters
        binding.editTextSignUpPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            //actually perform validation
            @Override
            public void afterTextChanged(Editable editable)
            {
                validatePassword(editable);
            }
        });

        //set up the spinner for usertype selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.usertype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.usertypeSpinner.setAdapter(adapter);
        binding.usertypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                usertype = (String) adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Implement actual sign up functionality
                String _username = binding.editTextSignUpUsername.getText().toString();
                String _password = binding.editTextSignUpPassword.getText().toString();
                String _usertype = usertype;

                if(_usertype != "")
                {
                    Log.d("Sign Up", "username:" + _username + "\n_password:" + _password + "\nusertype:" + usertype);
                }
            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void validateUsername(Editable editable)
    {
        String currentUsername = editable.toString();
        String usernameHints = new String();
        int length = currentUsername.length();

        if((length == 0) ||  length > 16)
        {
            usernameIsValid = false;
            usernameHints += "Username has to be between 1 and 16 characters\n";
        }
        else if(!currentUsername.matches("[A-Za-z0-9]+"))
        {
            usernameIsValid = false;
            usernameHints += "Username has to consist of alphanumeric characters (A-Z a-z 0-9) only\n";
        }
        else
        {
            usernameIsValid = true;
        }

        //set look of editTextView to reflect validity
        if(usernameIsValid)
        {
            binding.editTextSignUpUsername.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.validText)));
            binding.usernameValidationHint.setVisibility(View.GONE);
        }
        else
        {
            binding.editTextSignUpUsername.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.invalidText)));
        }

        if(!usernameHints.isEmpty())
        {
            binding.usernameValidationHint.setText(usernameHints);
            binding.usernameValidationHint.setVisibility(View.VISIBLE);
        }
    }

    private void validatePassword(Editable editable)
    {
        String currentPassword = editable.toString();

        //set look of editTextView to reflect validity
        try
        {
            if(!currentPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$"))
            {
                Log.d("Sign Up", "nani3");
                binding.passwordValidationHint.setVisibility(View.VISIBLE);
            }
            else
            {
                Log.d("Sign Up", "nani2");
                binding.passwordValidationHint.setVisibility(View.GONE);
            }
        }
        catch (Exception e)
        {
            Log.d("Sign Up", "NANI ", e);
        }

    }

}