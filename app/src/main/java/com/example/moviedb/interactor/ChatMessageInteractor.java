package com.example.moviedb.interactor;

import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.model.ChatMessageModelImpl;
import com.example.moviedb.model.IChatMessageModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChatMessageInteractor {
    private IChatMessageModel chatMessageModel;

    public ChatMessageInteractor() {
        chatMessageModel = new ChatMessageModelImpl();
    }

    public FirebaseRecyclerOptions<ChatMessage> getAllMsgs() {
        return this.chatMessageModel.getAllMsgsFromFirebase();
    }

    public Boolean addMsg(ChatMessage msg) {
        return this.chatMessageModel.addMsg(msg);
    }
}
