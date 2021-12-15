package com.example.liftingapp.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liftingapp.R;
import com.example.liftingapp.databinding.FragmentHomeScreenBinding;
import com.example.liftingapp.models.Maxes;
import com.example.liftingapp.viewmodels.MaxesViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Objects;

public class HomeScreenFragment extends Fragment {
    InterstitialAd ad;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MaxesViewModel viewModel = new ViewModelProvider(this.getActivity()).get(MaxesViewModel.class);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        NavController controller = NavHostFragment.findNavController(this);
        FragmentHomeScreenBinding binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        //viewModel.getRecent(auth.getCurrentUser().getEmail().toString());
//        if (recentMax != null){
//            binding.squat.setText(recentMax.getSquat());
//            binding.bench.setText(recentMax.getBench());
//            binding.deadlift.setText(recentMax.getDeadlift());
//            int intDeadlift = Integer.parseInt(recentMax.getSquat());
//            int intBench = Integer.parseInt(recentMax.getBench());
//            int intSquat = Integer.parseInt(recentMax.getDeadlift());
//            String totalString = String.valueOf(intDeadlift + intBench + intSquat);
//            binding.Total.setText(totalString);
//        }


        viewModel.loadMaxes();
        viewModel.getMaxes().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Maxes>>() {
            @Override
            public void onChanged(ObservableList<Maxes> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<Maxes> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Maxes> sender, int positionStart, int itemCount) {
                for (Maxes maxes : sender){
                    // update the UI using recycler view
                    //Log.d("__Firebase", maxes.getBench());
                }
            }

            @Override
            public void onItemRangeMoved(ObservableList<Maxes> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Maxes> sender, int positionStart, int itemCount) {

            }
        });
        //interstitial ad
        AdRequest request = new AdRequest.Builder().build();

        InterstitialAd.load(this.requireContext(), "ca-app-pub-3940256099942544/1033173712", request, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                ad = interstitialAd;
                ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("__AD","AD failed");
            }
        });

        // banner ad
        binding.bannerAd.loadAd(new AdRequest.Builder().build());
        // weight percentages button
        binding.weightPercentageButton.setOnClickListener(view -> {
            if (ad != null){
                ad.show(this.getActivity());
            }
        });
        // enter button
        binding.enterButton.setOnClickListener(view -> {
            String b = binding.benchInput.getText().toString();
            String d = binding.deadliftInput.getText().toString();
            String s = binding.squatInput.getText().toString();

            if (b.isEmpty()){

            }
            else if (d.isEmpty()){

            }
            else if (s.isEmpty()){

            }
            else {

                int intDeadlift = Integer.parseInt(binding.deadliftInput.getText().toString());
                binding.deadlift.setText(d);
                int intBench = Integer.parseInt(binding.deadliftInput.getText().toString());
                binding.bench.setText(b);
                int intSquat = Integer.parseInt(binding.squatInput.getText().toString());
                binding.squat.setText(s);
                String totalString = String.valueOf(intDeadlift + intBench + intSquat);
                binding.Total.setText(totalString);
                String email = auth.getCurrentUser().getEmail().toString();

                FieldValue created = FieldValue.serverTimestamp();
                viewModel.saveMaxes(b, d, s, email, created);
            }
        });

        // sign out button
        binding.signOutButton.setOnClickListener(view -> {
            auth.signOut();
            controller.navigate(R.id.action_homeScreenFragment_to_logInFragment);
        });
        return binding.getRoot(); }
}