package com.example.moviedb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.moviedb.DB.FirebaseDB;
import com.example.moviedb.R;
import com.example.moviedb.adapters.ChatAdapter;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.interactor.ChatMessageInteractor;
import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.mvp.presenter.ChatPresenterImpl;
import com.example.moviedb.mvp.view.ChatView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import butterknife.BindView;

public class ChatActivity extends BaseActivity implements ChatView {
    @BindView(R.id.txt_input)
    EditText txt_input;

    @BindView(R.id.fab)
    FloatingActionButton btnSend;

    @BindView(R.id.rv_chatmsg)
    RecyclerView rv_chatmsg;


    private ChatPresenterImpl mPresenter;
    private ChatAdapter mAdapter;
    private FirebaseRecyclerOptions<ChatMessage> options;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_chat;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        mPresenter=new ChatPresenterImpl(new ChatMessageInteractor());
        mAdapter = new ChatAdapter(options);
        init();
    }

    public void init(){

        mPresenter.onAttachView(this);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMsg(new ChatMessage(txt_input.getText().toString(),"Nann Su Mon Kyaw",0));
            }
        });

        mPresenter.onUIReady();
    }


    @Override
    public void showAllMsgs(FirebaseRecyclerOptions<ChatMessage> msgs) {
        options = msgs;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_chatmsg.setLayoutManager(mLayoutManager);
        rv_chatmsg.setItemAnimator(new DefaultItemAnimator());
        rv_chatmsg.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoMsgsInfo() {

    }

    @Override
    public void addMsg(ChatMessage msg) {
        mPresenter.addMsg(msg);
        mAdapter.notifyDataSetChanged();
    }
}
