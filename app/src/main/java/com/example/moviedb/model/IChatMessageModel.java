package com.example.moviedb.model;

import com.firebase.ui.database.FirebaseRecyclerOptions;

public interface IChatMessageModel {
    FirebaseRecyclerOptions<ChatMessage> getAllMsgsFromFirebase();
}
