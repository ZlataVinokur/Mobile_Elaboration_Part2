package ru.mirea.vinokurovazo.data.storage.network;

import com.google.firebase.auth.FirebaseAuth;


public class NetworkApi {
    private FirebaseAuth firebaseAuth;


    public NetworkApi() {
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

}