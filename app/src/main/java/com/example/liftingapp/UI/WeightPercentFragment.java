package com.example.liftingapp.UI;

import android.os.Bundle;

import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liftingapp.R;
import com.example.liftingapp.databinding.FragmentLogInBinding;
import com.example.liftingapp.databinding.FragmentWeightPercentBinding;
import com.example.liftingapp.models.Maxes;
import com.example.liftingapp.viewmodels.MaxesViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class WeightPercentFragment extends Fragment {
    FragmentWeightPercentBinding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeightPercentBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        MaxesViewModel viewModel = new ViewModelProvider(getActivity()).get(MaxesViewModel.class);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail().toString();

        ObservableArrayList<Maxes> m = viewModel.getRecentMaxes(email);
        for (Maxes maxes : m){
            int benchMax = Integer.parseInt(maxes.getBench());
            int squatMax = Integer.parseInt(maxes.getSquat());
            int deadliftMax = Integer.parseInt(maxes.getDeadlift());

            setBenchWP(benchMax);
            setDeadliftWP(deadliftMax);
            setSquatWP(squatMax);
        }

        //back button
        binding.backButton.setOnClickListener(view -> {
            controller.navigate(R.id.action_weightPercentFragment_to_homeScreenFragment);
        });
        return binding.getRoot();
    }
    public void setBenchWP(int benchMax){
        binding.benchWP.setText(benchMax + "");
        binding.benchWP2.setText((int)(benchMax * .95) + "");
        binding.benchWP3.setText((int)(benchMax * .90) + "");
        binding.benchWP4.setText((int)(benchMax * .85) + "");
        binding.benchWP5.setText((int)(benchMax * .80) + "");
        binding.benchWP6.setText((int)(benchMax * .75) + "");
        binding.benchWP7.setText((int)(benchMax * .70) + "");
        binding.benchWP8.setText((int)(benchMax * .65) + "");
        binding.benchWP9.setText((int)(benchMax * .60) + "");
    }

    public void setSquatWP(int squatMax){
        binding.squatWP.setText(squatMax + "");
        binding.squatWP2.setText((int)(squatMax * .95) + "");
        binding.squatWP3.setText((int)(squatMax * .90) + "");
        binding.squatWP4.setText((int)(squatMax * .85) + "");
        binding.squatWP5.setText((int)(squatMax * .80) + "");
        binding.squatWP6.setText((int)(squatMax * .75) + "");
        binding.squatWP7.setText((int)(squatMax * .70) + "");
        binding.squatWP8.setText((int)(squatMax * .65) + "");
        binding.squatWP9.setText((int)(squatMax * .60) + "");
    }

    public void setDeadliftWP(int deadliftMax){
        binding.deadliftWP.setText(deadliftMax + "");
        binding.deadliftWP2.setText((int)(deadliftMax * .95) + "");
        binding.deadliftWP3.setText((int)(deadliftMax * .90) + "");
        binding.deadliftWP4.setText((int)(deadliftMax * .85) + "");
        binding.deadliftWP5.setText((int)(deadliftMax * .80) + "");
        binding.deadliftWP6.setText((int)(deadliftMax * .75) + "");
        binding.deadliftWP7.setText((int)(deadliftMax * .70) + "");
        binding.deadliftWP8.setText((int)(deadliftMax * .65) + "");
        binding.deadliftWP9.setText((int)(deadliftMax * .60) + "");
    }
}