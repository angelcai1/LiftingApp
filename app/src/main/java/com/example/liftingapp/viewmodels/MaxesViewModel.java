package com.example.liftingapp.viewmodels;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.liftingapp.models.Maxes;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MaxesViewModel extends ViewModel {
    ObservableArrayList<Maxes> maxes = new ObservableArrayList<>();
    FirebaseFirestore db;
    public MaxesViewModel(){
        db = FirebaseFirestore.getInstance();
    }

    public void saveMaxes(String bench, String deadlift, String squat, String email, FieldValue created) {
        Maxes newMaxes = new Maxes(bench,deadlift, squat, email, created);
        db.collection("maxes").add(newMaxes).addOnCompleteListener((task) -> {
            if (task.isSuccessful()){
                maxes.add(newMaxes);
            } else {
                //display error
            }
        });
    }
    public void loadMaxes() {
        db.collection("maxes").get().addOnCompleteListener((task)->{
            if (task.isSuccessful()){
                QuerySnapshot collection = task.getResult();
                ArrayList<Maxes> maxesArrayList = (ArrayList<Maxes>) collection.toObjects(Maxes.class);
                maxes.addAll(maxesArrayList);
            }
            else {
                //error
            }
        });
    }

    public ObservableArrayList<Maxes> getMaxes() {
        return maxes;
    }
}
