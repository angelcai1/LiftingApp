package com.example.liftingapp.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Text;

import java.util.Objects;

public class HomeScreenFragment extends Fragment {
    InterstitialAd ad;
    FragmentHomeScreenBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MaxesViewModel viewModel = new ViewModelProvider(getActivity()).get(MaxesViewModel.class);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail().toString();

        NavController controller = NavHostFragment.findNavController(this);
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        try {
            ObservableArrayList<Maxes> m = viewModel.getRecentMaxes(email);
            for (Maxes maxes : m) {
                binding.bench.setText(maxes.getBench());
                binding.squat.setText(maxes.getSquat());
                binding.deadlift.setText(maxes.getDeadlift());
                int intDeadlift = Integer.parseInt(maxes.getDeadlift());
                int intBench = Integer.parseInt(maxes.getBench());
                int intSquat = Integer.parseInt(maxes.getSquat());
                String totalString = String.valueOf(intDeadlift + intBench + intSquat);
                binding.Total.setText(totalString);
            }
        }
        catch (Exception e){
            Log.d("__FIRESTORE", "assssss1 " + e.getMessage());
        }
        try {
            viewModel.getRecentMaxes(email).addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Maxes>>() {
                @Override
                public void onChanged(ObservableList<Maxes> sender) {

                }

                @Override
                public void onItemRangeChanged(ObservableList<Maxes> sender, int positionStart, int itemCount) {

                }

                @Override
                public void onItemRangeInserted(ObservableList<Maxes> sender, int positionStart, int itemCount) {
                    for (Maxes maxes : sender) {
                        binding.bench.setText(maxes.getBench());
                        binding.squat.setText(maxes.getSquat());
                        binding.deadlift.setText(maxes.getDeadlift());
                        int intDeadlift = Integer.parseInt(maxes.getDeadlift());
                        int intBench = Integer.parseInt(maxes.getBench());
                        int intSquat = Integer.parseInt(maxes.getSquat());
                        String totalString = String.valueOf(intDeadlift + intBench + intSquat);
                        binding.Total.setText(totalString);
                    }
                }

                @Override
                public void onItemRangeMoved(ObservableList<Maxes> sender, int fromPosition, int toPosition, int itemCount) {

                }

                @Override
                public void onItemRangeRemoved(ObservableList<Maxes> sender, int positionStart, int itemCount) {

                }
            });
        }
        catch (Exception e){
            Log.d("__FIRESTORE", "assssss2 " + e.getMessage());
        }
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
                            controller.navigate(R.id.action_homeScreenFragment_to_weightPercentFragment);
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.d("__AD", "AD failed");
                }
            });

        // banner ad
        binding.bannerAd.loadAd(new AdRequest.Builder().build());
        // weight percentages button
        binding.weightPercentageButton.setOnClickListener(view -> {
            if (ad != null) {
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
                viewModel.saveMaxes(b, d, s, email);

            }
        });

        // sign out button
        binding.signOutButton.setOnClickListener(view -> {
            auth.signOut();
            controller.navigate(R.id.action_homeScreenFragment_to_logInFragment);
        });
//        if (binding.Total.getText().toString().equals("0")){
//            auth.signOut();
//            controller.navigate(R.id.action_homeScreenFragment_to_logInFragment);
//        }
        return binding.getRoot();
    }


}
