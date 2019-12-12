package com.example.moviedb.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.moviedb.R;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.mvp.presenter.ChatPresenterImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {
    @BindView(R.id.txt_input)
    EditText txt_input;

    @BindView(R.id.fab)
    FloatingActionButton btnSend;

    @BindView(R.id.rv_chatmsg)
    RecyclerView rv_chatmsg;


    private ChatPresenterImpl mPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_chat;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
      //  mPresenter=new ChatPresenterImpl();
        init();
    }

    public void init(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
