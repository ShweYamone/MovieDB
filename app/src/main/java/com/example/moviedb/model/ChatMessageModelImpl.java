package com.example.moviedb.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviedb.DB.FirebaseDB;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
public class ChatMessageModelImpl implements IChatMessageModel {
    private static final String TAG = "ChatMessageModelImpl";

    private FirebaseRecyclerOptions<ChatMessage> options;

    @Override
    public FirebaseRecyclerOptions<ChatMessage> getMsgsFromFirebase() {

        Query query = FirebaseDB.getFirebaseDB().child("ChatMessage");
        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(query, new SnapshotParser<ChatMessage>() {
                            @NonNull
                            @Override
                            public ChatMessage parseSnapshot(@NonNull DataSnapshot snapshot) {

                                Long accountId = -100l;
                                if(!(snapshot.child("accountId").getValue() == null)) {
                                    accountId = (Long) (snapshot.child("accountId").getValue());
                                }
                                Log.i("Chat", snapshot.child("accountId").getValue() + "");

                                ChatMessage message = new ChatMessage(

                                        snapshot.child("messageText").getValue().toString(),
                                        snapshot.child("messageUser").getValue().toString(),
                                        snapshot.child("messageTime").getValue().toString(),
                                        accountId
                                );
                                return message;
                            }
                        }).build();
        return options;
    }

    @Override
    public boolean addMsg(DatabaseReference reference, ChatMessage msg) {
        if (msg == null) {
            return false;
        }
        else {
            try {
                reference.child("ChatMessage").push().setValue(msg);
                return true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
