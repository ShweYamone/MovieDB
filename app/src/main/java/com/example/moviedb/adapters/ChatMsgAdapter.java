package com.example.moviedb.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.R;
import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.util.SharePreferenceHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMsgAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatMsgAdapter.ViewHolder> {

    private SharePreferenceHelper mSharePreferenceHelper;


    public ChatMsgAdapter(FirebaseRecyclerOptions<ChatMessage> options) {
        super(options);

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

        @BindView(R.id.layoutItemMessage)
        LinearLayout layoutItemMessage;

        @BindView(R.id.layoutCircle)
        RelativeLayout layoutCircle;

        @BindView(R.id.layoutMessage)
        LinearLayout layoutMessage;

        @BindView(R.id.tvLetters)
        TextView tvLetters;

        @BindView(R.id.tv_message)
        TextView tvMessage;

        @BindView(R.id.tvTime)
        TextView tvTime;

        private Context context;

        public ViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            mSharePreferenceHelper = new SharePreferenceHelper(context);
            ButterKnife.bind(this, view);
        }

        public void bindView(ChatMessage message){
            //show User Info
            String userName = message.getMessageUser();
            String letters = userName.charAt(0) + "";
            int spaceIndex = userName.indexOf(" ");
            if(spaceIndex > 0) {
                letters += userName.charAt(spaceIndex + 1);
            }
            tvLetters.setText(letters.toUpperCase());
            tvMessage.setText(message.getMessageText());
            tvTime.setText(message.getMessageTime());

            if(mSharePreferenceHelper.getUserId() == message.getAccountId()) {
                layoutCircle.setVisibility(View.GONE);
                layoutItemMessage.setGravity(Gravity.END);
            }
            else {
                layoutCircle.setVisibility(View.VISIBLE);
                layoutItemMessage.setGravity(Gravity.START);
            }

        }
    }


}
