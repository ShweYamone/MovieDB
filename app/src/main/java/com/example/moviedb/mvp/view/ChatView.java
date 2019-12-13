package com.example.moviedb.mvp.view;
import com.example.moviedb.model.ChatMessage;
import com.google.firebase.database.DatabaseReference;

public interface ChatView {
    void addMsg(DatabaseReference reference, ChatMessage msg);
}
