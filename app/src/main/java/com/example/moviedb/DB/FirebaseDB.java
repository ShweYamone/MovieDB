package com.example.moviedb.DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDB {
    private static DatabaseReference INSTANCE;

    public static DatabaseReference getFirebaseDB(){
        if(INSTANCE == null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
            INSTANCE = database.getReference();
        }
        return INSTANCE;
    }
}
