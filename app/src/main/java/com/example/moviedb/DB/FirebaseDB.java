package com.example.moviedb.DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDB {
    private static DatabaseReference INSTANCE;

    public static DatabaseReference getFirebaseDB(){
        if(INSTANCE == null){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            INSTANCE = FirebaseDatabase.getInstance().getReference();
        }
        return INSTANCE;

    }
}
