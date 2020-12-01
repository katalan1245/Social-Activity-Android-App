package com.example.smartness.Model;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler {
    private FirebaseFirestore db;

     public enum UserLoginMessage
    {
        USER_DOES_NOT_EXIST,
        USER_PASSWORD_DOES_NOT_MATCH,
        USER_LOGIN_SUCCESFULL;
    }
    public DatabaseHandler() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Boolean> addUser(final User user) {
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        db.collection("Users").document(user.getUsername()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().getData() == null) {
                            Map<String, Object> userFields = new HashMap<>();
                            userFields.put("password", user.getPassword());
                            userFields.put("username", user.getUsername());
                            userFields.put("id", user.getId());
                            userFields.put("type", user.getType());
                            db.collection("Users").document(user.getUsername()).set(userFields).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    tcs.setResult(true);
                                }
                            });
                        } else {
                            tcs.setResult(true);
                        }
                    }
                }
        );
        return tcs.getTask();
    }

    public Task<Boolean> uploadLocation(final UserLocation userLocation) {
         final String name = userLocation.getLatitude() + "";
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        db.collection("Locations").document(userLocation.getName()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().getData() == null) {
                            Map<String, Object> userFields = new HashMap<>();
                            userFields.put("Name", userLocation.getName());
                            userFields.put("Description", userLocation.getDesc());
                            userFields.put("Latitude", userLocation.getLatitude());
                            userFields.put("Longitude", userLocation.getLongitude());
                            db.collection("Locations").document(userLocation.getName()).set(userFields).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    tcs.setResult(true);
                                }
                            });
                        } else {
                            tcs.setResult(true);
                        }
                    }
                });
        return tcs.getTask();
    }

    public Task<UserLoginMessage> login(String username, final String password) {
        final TaskCompletionSource<UserLoginMessage> tcs = new TaskCompletionSource<>();
        db.collection("Users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()&&task.getResult().getData()!=null) {
                    String actualPassword = (String)task.getResult().get("password");
                    if(actualPassword.equals(password))
                    {
                        tcs.setResult(UserLoginMessage.USER_LOGIN_SUCCESFULL);
                    }
                    else
                    {
                        tcs.setResult(UserLoginMessage.USER_PASSWORD_DOES_NOT_MATCH);
                    }
                }
                else
                {
                    tcs.setResult(UserLoginMessage.USER_DOES_NOT_EXIST);
                }
            }

        });
        return tcs.getTask();
    }
}
