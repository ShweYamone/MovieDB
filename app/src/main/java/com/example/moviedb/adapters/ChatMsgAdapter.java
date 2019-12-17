package com.example.moviedb.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
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
import com.example.moviedb.delegate.ChatDelegate;
import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.util.SharePreferenceHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMsgAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatMsgAdapter.ViewHolder> {
    private static onClickListner onclicklistner;
    private SharePreferenceHelper mSharePreferenceHelper;

    ChatDelegate chatDelegate;
    public ChatMsgAdapter(FirebaseRecyclerOptions<ChatMessage> options, ChatDelegate delegate) {
        super(options);
        this.chatDelegate=delegate;
    }



    @Override
    public ChatMsgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatMsgAdapter.ViewHolder(view,this);
    }

    @Override
    protected void onBindViewHolder(ChatMsgAdapter.ViewHolder holder, int position, ChatMessage model) {
        holder.bindView(model, position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{

        @BindView(R.id.layoutItemMessage)
        LinearLayout layoutItemMessage;

        @BindView(R.id.layoutCircle)
        RelativeLayout layoutCircle;

        @BindView(R.id.layoutMessage)
        LinearLayout layoutMessage;

        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.tvLetters)
        TextView tvLetters;

        @BindView(R.id.tv_message)
        TextView tvMessage;

        @BindView(R.id.tvTime)
        TextView tvTime;

        private Context context;
        public ChatMsgAdapter chatMsgAdapter;

        public ViewHolder(View view,ChatMsgAdapter chatMsgAdapter) {
            super(view);
            context = itemView.getContext();
            this.chatMsgAdapter=chatMsgAdapter;
            mSharePreferenceHelper = new SharePreferenceHelper(context);
            ButterKnife.bind(this, view);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindView(ChatMessage message, int position){
            //show User Info
            String userName = message.getMessageUser();
            String letters = userName.charAt(0) + "";
            int spaceIndex = userName.indexOf(" ");
            if(spaceIndex > 0) {
                letters += userName.charAt(spaceIndex + 1);
            }
            tvUserName.setText(message.getMessageUser());
            tvLetters.setText(letters.toUpperCase());
            tvMessage.setText(message.getMessageText());
            tvTime.setText(message.getMessageTime());

            Display display = ((Activity) layoutItemMessage.getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            try {
                display.getRealSize(size);
            } catch (NoSuchMethodError err) {
                display.getSize(size);
            }
            int width = size.x;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if(mSharePreferenceHelper.getUserId() == message.getAccountId()) {
                layoutCircle.setVisibility(View.GONE);

                if (position == 0) {
                    lp.setMargins((int) (width * (1 / 3.0)), 50, 0, 0);
                } else {
                    lp.setMargins((int) (width * (1 / 3.0)), 27 , 0, 0);

                }
                layoutItemMessage.setLayoutParams(lp);
                layoutItemMessage.setGravity(Gravity.END);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.START;
                tvTime.setLayoutParams(params);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        chatDelegate.deleteChatMessage(message.getMessageId());
                        return true;
                    }
                });

            }
            else {
                layoutCircle.setVisibility(View.VISIBLE);

                if (position == 0) {
                    lp.setMargins(0, 50, (int)(width * (1 / 3.0)), 0);

                } else {
                    lp.setMargins(0, 27, (int)(width * (1 / 3.0)), 0);

                }
                layoutItemMessage.setLayoutParams(lp);
                layoutItemMessage.setGravity(Gravity.START);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.END;
                tvTime.setLayoutParams(params);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return true;
                    }
                });
            }

        }

        @Override
        public void onClick(View v) {
            onclicklistner.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            onclicklistner.onItemLongClick(getAdapterPosition(), v);
            return true;
        }
    }



    public interface onClickListner {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(onClickListner onclicklistner) {
        ChatMsgAdapter.onclicklistner = onclicklistner;
    }



}
