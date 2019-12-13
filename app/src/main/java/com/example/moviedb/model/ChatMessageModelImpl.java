package com.example.moviedb.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.util.LogTime;
import com.example.moviedb.DB.FirebaseDB;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Query;

public class ChatMessageModelImpl implements IChatMessageModel {
    private static final String TAG = "ChatMessageModelImpl";
    @Override
    public FirebaseRecyclerOptions<ChatMessage> getAllMsgsFromFirebase() {
        Log.e(TAG, "getAllMsgsFromFirebase: " );
        Query query = FirebaseDB.getFirebaseDB().child("ChatMessage");
        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(query, new SnapshotParser<ChatMessage>() {
                            @NonNull
                            @Override
                            public ChatMessage parseSnapshot(@NonNull DataSnapshot snapshot) {
                                Log.e(TAG, "parseSnapshot: "+snapshot );

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

    @Override
    public boolean addMsg(ChatMessage msg) {
        if (msg == null) {
            return false;
        }
        else {
            try {
                FirebaseDB.getFirebaseDB().child("ChatMessage").push().setValue(msg);
                return true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
