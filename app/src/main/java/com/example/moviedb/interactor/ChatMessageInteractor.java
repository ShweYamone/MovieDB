package com.example.moviedb.interactor;

import android.content.Context;

import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.model.ChatMessageModelImpl;
import com.example.moviedb.model.IChatMessageModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class ChatMessageInteractor {
    private IChatMessageModel chatMessageModel;

    public ChatMessageInteractor() {
        chatMessageModel = new ChatMessageModelImpl();
    }

    public FirebaseRecyclerOptions<ChatMessage> getAllMsgs() {
        return this.chatMessageModel.getMsgsFromFirebase();
    }

    public Boolean addMsg(Context context,DatabaseReference reference, ChatMessage msg) {
        return this.chatMessageModel.addMsg(context,reference, msg);
    }
}
