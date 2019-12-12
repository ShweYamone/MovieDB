package com.example.moviedb.mvp.presenter;

import com.example.moviedb.interactor.ChatMessageInteractor;
import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.mvp.view.ChatView;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
    public void getAllMsgs() {
        FirebaseRecyclerOptions<ChatMessage> msgs= this.messageInteractor.getAllMsgs();
        if (msgs.getSnapshots().isEmpty()) {
            chatView.showNoMsgsInfo();
        }
        else if (msgs.getSnapshots().size() == 0) {
            chatView.showNoMsgsInfo();
        }
        else {
            chatView.showAllMsgs(msgs);
        }
    }

    @Override
    public Boolean addMsg(ChatMessage msg) {
        return this.messageInteractor.addMsg(msg);
    }


}
