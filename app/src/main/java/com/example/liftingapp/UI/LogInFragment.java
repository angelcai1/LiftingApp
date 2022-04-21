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
import com.example.liftingapp.databinding.FragmentLogInBinding;
import com.example.liftingapp.viewmodels.MaxesViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class LogInFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLogInBinding binding = FragmentLogInBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText emailText = binding.emailInput;
        EditText passText = binding.passwordInput;
        if (auth.getCurrentUser() != null){
            Log.d("__FIREBASE", auth.getCurrentUser().getEmail().toString());
            controller.navigate(R.id.action_logInFragment_to_homeScreenFragment);
        }
        else {
            Log.d("__FIREBASE", "Current User = NONE");
        }
        binding.signUpButton.setOnClickListener(view-> {
            controller.navigate(R.id.action_logInFragment_to_signUpFragment);
        });
        binding.logInButton.setOnClickListener(view-> {
            if (emailText.getText().toString().equals("") || passText.getText().toString().equals("")){
                binding.errorLog.setText("Error: Missing email/password");
            }
            else {
                auth.signInWithEmailAndPassword(
                        emailText.getText().toString(),
                        passText.getText().toString()
                ).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {

                        controller.navigate(R.id.action_logInFragment_to_homeScreenFragment);
                    } else {
                        binding.errorLog.setText(task.getException().getLocalizedMessage());
                    }
                });
            }
        });
        return binding.getRoot();
    }
}