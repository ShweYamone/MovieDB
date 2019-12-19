package com.example.moviedb.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
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


    public class ViewHolder extends RecyclerView.ViewHolder {

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

        }

        public void bindView(ChatMessage message, int position){

            if (message.getIsDeleted().equals("true")) {
                itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                itemView.setVisibility(View.GONE);
                layoutItemMessage.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                layoutItemMessage.setVisibility(View.GONE);
            }
            else {
                itemView.setVisibility(View.VISIBLE);
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
                Drawable background = layoutMessage.getBackground();
                if(mSharePreferenceHelper.getUserId() == message.getAccountId()) {
                    background.setTint(context.getResources().getColor(R.color.color_palette4));

                    layoutCircle.setVisibility(View.GONE);
                    lp.setMargins((int) (width * (1 / 3.0)), 27 , 0, 0);
                    layoutItemMessage.setLayoutParams(lp);
                    layoutItemMessage.setGravity(Gravity.END);
                    //For time
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.START;
                    tvTime.setLayoutParams(params);
                    //For Username
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.END;
                    tvUserName.setLayoutParams(params);

                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            chatDelegate.deleteChatMessage(message.getMessageId(),getAdapterPosition());
                            return true;
                        }
                    });

                }
                else {
                    background.setTint(context.getResources().getColor(R.color.color_grey_stroke));
               //     layoutMessage.setBackgroundColor(context.getResources().getColor(R.color.color_grey_stroke));
                    layoutCircle.setVisibility(View.VISIBLE);
                    lp.setMargins(0, 27, (int)(width * (1 / 3.0)), 0);
                    layoutItemMessage.setLayoutParams(lp);
                    layoutItemMessage.setGravity(Gravity.START);
                    //For time
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.END;
                    tvTime.setLayoutParams(params);
                    //For username
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.START;
                    tvUserName.setLayoutParams(params);

                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return true;
                        }
                    });
                }

            }
            }
    }

}
