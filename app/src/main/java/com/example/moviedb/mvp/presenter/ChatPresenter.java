package com.example.moviedb.mvp.presenter;

import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.mvp.view.ChatView;

public interface ChatPresenter {
    void onUIReady();
    void onAttachView(ChatView chatView);
    void getAllMsgs();
    Boolean addMsg(ChatMessage msg);
}
