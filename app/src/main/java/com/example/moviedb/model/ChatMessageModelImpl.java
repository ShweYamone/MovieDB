package com.example.moviedb.model;

import androidx.annotation.NonNull;

import com.example.moviedb.DB.FirebaseDB;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

public class ChatMessageModelImpl implements IChatMessageModel {
    @Override
    public FirebaseRecyclerOptions<ChatMessage> getAllMsgsFromFirebase() {

        Query query = FirebaseDB.getFirebaseDB().child("ChatMessage");
        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(query, new SnapshotParser<ChatMessage>() {
                            @NonNull
                            @Override
                            public ChatMessage parseSnapshot(@NonNull DataSnapshot snapshot) {

                                ChatMessage chatMsg = new ChatMessage(
                                        snapshot.child("messageText").getValue().toString(),
                                        snapshot.child("messageUser").getValue().toString(),
                                        (Long)snapshot.child("messageTime").getValue());

                                return chatMsg;
                            }
                        })
                        .build();


        return options;
    }
}
