package com.example.moviedb.mvp.view;
import com.example.moviedb.model.ChatMessage;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public interface ChatView {
    void showAllMsgs(FirebaseRecyclerOptions<ChatMessage> msgs);
    void showNoMsgsInfo();
    void addMsg(ChatMessage msg);

}
