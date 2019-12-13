package com.example.moviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.model.ChatMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter  extends BaseAdapter {

        @Override
        protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
            return new ChatAdapter.ViewHolder(view);
        }

        @Override
        protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ChatAdapter.ViewHolder)holder).bindView((ChatMessage)getItemsList().get(position),position);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.txt_user)
            TextView txt_user;

            @BindView(R.id.txt_date)
            TextView txt_date;

            @BindView(R.id.txt_message)
            TextView txt_message;

            private Context context;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                context = itemView.getContext().getApplicationContext();
                ButterKnife.bind(this, itemView);
            }

            public void bindView(ChatMessage model, int position) {
                txt_user.setText(model.getMessageUser());
                txt_message.setText(model.getMessageText());
                txt_date.setText(model.getMessageTime() + "");
          }

        }
    }