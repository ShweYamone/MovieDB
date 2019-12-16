package com.example.moviedb.activities;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.moviedb.DB.FirebaseDB;
import com.example.moviedb.R;
import com.example.moviedb.adapters.ChatMsgAdapter;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.interactor.ChatMessageInteractor;
import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.mvp.presenter.ChatPresenterImpl;
import com.example.moviedb.mvp.view.ChatView;
import com.example.moviedb.util.SharePreferenceHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;

public class ChatActivity extends BaseActivity implements ChatView {
    private static final String TAG = "ChatActivity";
    @BindView(R.id.txt_input)
    EditText txt_input;

    @BindView(R.id.fab)
    FloatingActionButton btnSend;

    @BindView(R.id.rv_chatmsg)
    RecyclerView rv_chatmsg;

//    @BindView(R.id.chat_scrollview)
//    ScrollView scrollView;

    private ChatPresenterImpl mPresenter;
    private ChatMsgAdapter chatMsgAdapter;
    private DatabaseReference mReference;
    private SharePreferenceHelper mSharePreferenceHelper;

    private int itemPos = 0;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_chat;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        mSharePreferenceHelper = new SharePreferenceHelper(this);
        mReference = FirebaseDB.getFirebaseDB();
        mPresenter=new ChatPresenterImpl(new ChatMessageInteractor());
        init();
    }

    public void init(){
        chatMsgAdapter = new ChatMsgAdapter(mPresenter.getAllMsgs());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_chatmsg.setLayoutManager(mLayoutManager);
        rv_chatmsg.setHasFixedSize(true);
        rv_chatmsg.setItemAnimator(new DefaultItemAnimator());
        rv_chatmsg.setAdapter(chatMsgAdapter);

        // Scroll to bottom on new messages
        chatMsgAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                Log.i(TAG, "onItemRangeInserted: "+chatMsgAdapter.getItemCount());
                rv_chatmsg.smoothScrollToPosition(rv_chatmsg.getAdapter().getItemCount());
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("HH:mm, d MMM yyyy");
                String time = df.format(Calendar.getInstance().getTime());

                addMsg(mReference, new ChatMessage(
                        txt_input.getText().toString(),
                        mSharePreferenceHelper.getUserName(),
                        time,
                        Long.valueOf(mSharePreferenceHelper.getUserId()+"")));

                txt_input.setText("");
            }
        });

//        txt_input.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //make the view scroll down to the bottom
//                scrollView.scrollTo(0, scrollView.getBottom());
//            }
//        });

        mPresenter.onAttachView(this);
        mPresenter.onUIReady();
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatMsgAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatMsgAdapter.stopListening();
    }

    @Override

    public void addMsg(DatabaseReference mReference, ChatMessage msg) {
        mPresenter.addMsg(mReference,msg);

    }
}
