package com.example.moviedb.model;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;


public interface IChatMessageModel {

    FirebaseRecyclerOptions<ChatMessage> getMsgsFromFirebase();
    boolean addMsg(DatabaseReference reference, ChatMessage msg);
}
