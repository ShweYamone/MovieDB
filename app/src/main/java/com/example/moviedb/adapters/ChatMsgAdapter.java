package com.example.moviedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.model.ChatMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMsgAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatMsgAdapter.ViewHolder> {
    private FirebaseRecyclerOptions<ChatMessage> msglist;
    public ChatMsgAdapter(FirebaseRecyclerOptions<ChatMessage> options) {
         super(options);
         msglist=options;
    }

    @Override
    public ChatMsgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatMsgAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(ChatMsgAdapter.ViewHolder holder, int position, ChatMessage model) {
        holder.bindView(model);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_user)
        TextView txt_user;

        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.txt_message)
        TextView txt_message;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(ChatMessage message){
            txt_user.setText(message.getMessageUser());
            txt_date.setText(message.getMessageTime());
            txt_message.setText(message.getMessageText());
        }
    }


}
