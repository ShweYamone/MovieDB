package com.example.moviedb.mvp.presenter;

import android.util.Log;

import com.example.moviedb.interactor.ChatMessageInteractor;
import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.mvp.view.ChatView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ChatPresenterImpl extends BasePresenter implements ChatPresenter {
    private ChatView chatView = null;
    ChatMessageInteractor messageInteractor;

    public ChatPresenterImpl(ChatMessageInteractor messageInteractor) {
        this.messageInteractor = messageInteractor;
    }

    @Override
    public void onUIReady() {
        getAllMsgs();
    }

    @Override
    public void onAttachView(ChatView chatView) {
        this.chatView = chatView;
    }

    @Override
    public FirebaseRecyclerOptions<ChatMessage> getAllMsgs() {
        return this.messageInteractor.getAllMsgs();
    }

    @Override
    public Boolean addMsg(DatabaseReference mReference, ChatMessage msg) {
        return this.messageInteractor.addMsg(mReference, msg);
    }


}
