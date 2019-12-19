package com.example.moviedb.model;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;


public interface IChatMessageModel {

    FirebaseRecyclerOptions<ChatMessage> getMsgsFromFirebase();
    boolean addMsg(Context context, DatabaseReference reference, ChatMessage msg);
}
