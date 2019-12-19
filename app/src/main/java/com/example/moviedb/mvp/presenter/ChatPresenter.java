package com.example.moviedb.mvp.presenter;

import android.content.Context;

import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.mvp.view.ChatView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public interface ChatPresenter {
    void onUIReady();
    void onAttachView(ChatView chatView);
    FirebaseRecyclerOptions<ChatMessage> getAllMsgs();
    Boolean addMsg(Context context,DatabaseReference reference, ChatMessage msg);
}
