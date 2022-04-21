package com.example.liftingapp.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.liftingapp.models.Maxes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MaxesViewModel extends ViewModel {
    ObservableArrayList<Maxes> maxes;
    //MutableLiveData<Maxes> recentMax;
    FirebaseFirestore db;
    public MaxesViewModel(){
        db = FirebaseFirestore.getInstance();
    }

    public void saveMaxes(String bench, String deadlift, String squat, String email) {
        FieldValue timestamp = FieldValue.serverTimestamp();
        Maxes newMaxes = new Maxes(bench,deadlift, squat, email, timestamp);
        db.collection("maxes").add(newMaxes).addOnCompleteListener((task) -> {
            if (task.isSuccessful()){
                maxes.add(newMaxes);
                //recentMax.setValue(newMaxes);
            } else {
                //display error
            }
        });
    }
    private void loadMaxes(String email) {
        db.collection("maxes")
                .whereEqualTo(email, email)
                .orderBy("created")
                .limit(1)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        maxes.addAll(task.getResult().toObjects(Maxes.class));
                        Log.d("__FIRESTORE", "DB found stuff\n");
                    }
                    else{
                        Log.d("__FIRESTORE", "DB found nothing\n");
                    }
        });
    }

    public ObservableArrayList<Maxes> getRecentMaxes(String email) {
        //if (recentMax == null){
        if (maxes == null){
            maxes = new ObservableArrayList<>();
            //recentMax = new MutableLiveData<>();
            loadMaxes(email);
        }
        return maxes;
        //return recentMax;
    }

}
