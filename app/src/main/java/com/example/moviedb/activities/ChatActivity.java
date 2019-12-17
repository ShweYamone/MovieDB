package com.example.moviedb.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.example.moviedb.DB.FirebaseDB;
import com.example.moviedb.R;
import com.example.moviedb.adapters.ChatMsgAdapter;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.custom_control.MyanBoldTextView;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_text)
    MyanBoldTextView toolbar_text;

    @BindView(R.id.txt_input)
    EditText txt_input;

    @BindView(R.id.fab)
    ImageView btnSend;

    @BindView(R.id.rv_chatmsg)
    RecyclerView rv_chatmsg;


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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar_text.setMyanmarText("MovieDB Chat Room");


        mSharePreferenceHelper = new SharePreferenceHelper(this);
        mReference = FirebaseDB.getFirebaseDB();
        mPresenter=new ChatPresenterImpl(new ChatMessageInteractor());
        init();
    }

    public void init(){
        chatMsgAdapter = new ChatMsgAdapter(mPresenter.getAllMsgs());
      //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        rv_chatmsg.setLayoutManager(linearLayoutManager);
        rv_chatmsg.setHasFixedSize(true);
        rv_chatmsg.setItemAnimator(new DefaultItemAnimator());
        rv_chatmsg.setAdapter(chatMsgAdapter);

        rv_chatmsg.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    rv_chatmsg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rv_chatmsg.smoothScrollToPosition(
                                    rv_chatmsg.getAdapter().getItemCount() - 1);
                        }
                    }, 100);
                }
            }
        });

        // Scroll to bottom on new messages
        chatMsgAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = chatMsgAdapter.getItemCount();
                int lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    rv_chatmsg.scrollToPosition(positionStart);
                }
            }
        });

        txt_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    btnSend.setClickable(true);
                    Glide.with(getApplicationContext())
                            .load(R.drawable.sent)
                            .into(btnSend);
                } else {
                    btnSend.setClickable(false);
                    Glide.with(getApplicationContext())
                            .load(R.drawable.send)
                            .into(btnSend);
                }
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
