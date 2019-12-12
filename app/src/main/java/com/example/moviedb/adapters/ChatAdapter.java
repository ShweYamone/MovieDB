package com.example.moviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.model.ChatMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatAdapter.ChatViewHolder> {


    public ChatAdapter(FirebaseRecyclerOptions<ChatMessage> options) {
        super(options);

    }
    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i, @NonNull ChatMessage chatMessage) {
        chatViewHolder.bindView(chatMessage);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatAdapter.ChatViewHolder(view);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        ChatMessage chatMessage;
        public ChatViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }

        public void bindView(ChatMessage chatMessage){

            this.chatMessage= chatMessage;
        }


    }



}
