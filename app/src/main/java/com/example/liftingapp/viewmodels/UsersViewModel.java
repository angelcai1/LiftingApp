package com.example.liftingapp.viewmodels;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.liftingapp.models.Users;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class UsersViewModel extends ViewModel {
    ObservableArrayList<Users> users = new ObservableArrayList<>();
    FirebaseFirestore db;

    public UsersViewModel() {db = FirebaseFirestore.getInstance();}

    public ObservableArrayList<Users> getUsers() {
        return users;
    }

    public void saveUsers(String username, String password){
        Users newUser = new Users(username, password);
        db.collection("User").add(newUser).addOnCompleteListener((task) ->{
            if (task.isSuccessful()){
                users.add(newUser);
            }
            else {
                //display error
            }
        });

    }

    public void loadUsers(){
        db.collection("User")
                .get().addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                QuerySnapshot collection = task.getResult();
//                for (QueryDocumentSnapshot document : collection){
//                    Client client = document.toObject(Client.class);
//                    Log.d("__FIREBASE", "cLIENT NAME: " + client.getName() + " Client email: " + client.getEmail() + " client id: " + document.getId()));
//
//                }
                users.addAll(collection.toObjects(Users.class));
//                clientArrayList.forEach((client -> {
//                    Log.d("__FIREBASE", client2.getName());
//                }));
            } else {
                Log.d("__FIREBASE", "Error adding to firestore");
            }
        });
    }
}
