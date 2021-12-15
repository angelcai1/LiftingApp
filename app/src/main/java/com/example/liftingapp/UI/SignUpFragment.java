package com.example.liftingapp.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.liftingapp.R;
import com.example.liftingapp.databinding.FragmentSignUpBinding;
import com.example.liftingapp.viewmodels.UsersViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FragmentSignUpBinding binding = FragmentSignUpBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);

        EditText emailField = binding.signUpEmailInput;
        EditText passField = binding.signUpPassInput;
        EditText emailComField = binding.signUpEmailInput2;
        EditText passComField = binding.signUpPassInput2;
        UsersViewModel viewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        binding.signUpButton.setOnClickListener((view) ->{
            if (emailField.getText().toString().equals("") || passField.getText().toString().equals("")){
                binding.errorText.setText("Error: Missing email/password");
            }
            else if (!(emailField.getText().toString().equals(emailComField.getText().toString()))){
                binding.errorText.setText("Error: Email does not match");
            }

            else if (!(passField.getText().toString().equals(passComField.getText().toString()))){
                binding.errorText.setText("Error: Password does not match");
            }
            else if (passField.getText().toString().length() < 9){
                binding.errorText.setText("Error: Password requires more 8 letters");
            }
            else {

                auth.createUserWithEmailAndPassword(
                        emailField.getText().toString(),
                        passField.getText().toString()
                ).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        Log.d("__FIREBASE", "Successful Sign Up");
                        viewModel.saveUsers(emailField.getText().toString(), passField.getText().toString());
                        controller.navigate(R.id.action_signUpFragment_to_logInFragment);
                    } else {
                        Log.d("__FIREBASE", "Error Signing Up: " + task.getException().getLocalizedMessage().toString());
                        binding.errorText.setText("Error: " + task.getException().getLocalizedMessage());
                    }
                });
            }
        });


        return binding.getRoot();
    }
}