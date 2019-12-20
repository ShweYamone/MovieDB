package com.example.moviedb.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.DB.FirebaseDB;
import com.example.moviedb.R;
import com.example.moviedb.adapters.ChatMsgAdapter;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.custom_control.MyanBoldTextView;
import com.example.moviedb.delegate.ChatDelegate;
import com.example.moviedb.fragment.ChatMessageDeleteFragmentSheet;
import com.example.moviedb.interactor.ChatMessageInteractor;
import com.example.moviedb.model.ChatMessage;
import com.example.moviedb.mvp.presenter.ChatPresenterImpl;
import com.example.moviedb.mvp.view.ChatView;
import com.example.moviedb.util.SharePreferenceHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;

public class ChatActivity extends BaseActivity implements ChatView, ChatMessageDeleteFragmentSheet.ItemClickListener, ChatDelegate {
    private static final String TAG = "ChatActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_text)
    MyanBoldTextView toolbar_text;

    @BindView(R.id.txt_input)
    EditText txt_input;

    @BindView(R.id.fab)
    ImageView btnSend;

    @BindView(R.id.btnMsgSend)
    Button btnMsgSend;

    @BindView(R.id.rv_chatmsg)
    RecyclerView rv_chatmsg;

    @BindView(R.id.ib_add_image)
    ImageButton addImage;

    @BindView(R.id.fl_image)
    LinearLayout flImage;

    @BindView(R.id.iv_image_cancel)
    ImageView imageCancel;

    @BindView(R.id.iv_send_image)
    ImageView sendImage;

    private String messageId;
    public int itemPos=0;
    private Uri selectedImage;
    private int PICK_IMAGE_REQUEST = 11;
    private ChatPresenterImpl mPresenter;
    private ChatMsgAdapter chatMsgAdapter;
    private DatabaseReference mReference;
    private SharePreferenceHelper mSharePreferenceHelper;

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
        chatMsgAdapter = new ChatMsgAdapter(mPresenter.getAllMsgs(),this);

      //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        rv_chatmsg.setLayoutManager(linearLayoutManager);

        rv_chatmsg.setHasFixedSize(true);
        rv_chatmsg.setItemAnimator(new DefaultItemAnimator());
        rv_chatmsg.setAdapter(chatMsgAdapter);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        imageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flImage.setVisibility(View.GONE);
                btnMsgSend.setClickable(false);
                Glide.with(getApplicationContext())
                        .load(R.drawable.icon_before_send)
                        .into(btnSend);
            }
        });

        rv_chatmsg.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    rv_chatmsg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (rv_chatmsg.getAdapter().getItemCount() > 0) {
                                rv_chatmsg.smoothScrollToPosition(
                                        rv_chatmsg.getAdapter().getItemCount() - 1);
                            }
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
                if (s.length() != 0 && !isStringNullOrWhiteSpace(s.toString())) {
                    btnMsgSend.setClickable(true);
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icon_after_send)
                            .into(btnSend);
                } else {
                    btnMsgSend.setClickable(false);
                    Glide.with(getApplicationContext())
                            .load(R.drawable.icon_before_send)
                            .into(btnSend);
                }
            }
        });

        btnMsgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SelectedImageView", selectedImage + "");
                if (txt_input.getText().toString() != null && !txt_input.getText().toString().equals("") && !isStringNullOrWhiteSpace(txt_input.getText().toString())) {
                    DateFormat df = new SimpleDateFormat("HH:mm, d MMM yyyy");
                    String time = df.format(Calendar.getInstance().getTime());
                    Log.i("SelectedImage1", selectedImage + "");
                    addMsg(mReference, new ChatMessage(
                            txt_input.getText().toString(),
                            mSharePreferenceHelper.getUserName(),
                            time,
                            "false",
                            Long.valueOf(mSharePreferenceHelper.getUserId()+""),
                            "0"));

                    txt_input.setText("");
                }
                else if(selectedImage != null){
                    Log.i("SelectedImage", selectedImage + "");
                    flImage.setVisibility(View.GONE);
                    DateFormat df = new SimpleDateFormat("HH:mm, d MMM yyyy");
                    String time = df.format(Calendar.getInstance().getTime());
                    Log.e("photo",selectedImage.toString());
                    addMsg(mReference, new ChatMessage(
                            selectedImage.toString(),
                            mSharePreferenceHelper.getUserName(),
                            time,
                            "false",
                            Long.valueOf(mSharePreferenceHelper.getUserId()+""),
                            "1"));

                    txt_input.setText("");

                }
            }
        });

        mPresenter.onAttachView(this);
        mPresenter.onUIReady();

    }

    //method to show file chooser
    private void showFileChooser() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);


/*
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
            flImage.setVisibility(View.VISIBLE);
            selectedImage = data.getData();
            if (selectedImage != null) {
                btnMsgSend.setClickable(true);
                Glide.with(getApplicationContext())
                        .load(R.drawable.icon_after_send)
                        .into(btnSend);
            }
            try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            Log.e("photo",bitmap.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(this)
                    .load(selectedImage)
                    .into(sendImage);

        }
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
        mPresenter.addMsg(this,mReference,msg);
    }



    @Override
    public void deleteChatMessage(String messageId,int position) {

        ChatMessageDeleteFragmentSheet addPhotoBottomDialogFragment =
                ChatMessageDeleteFragmentSheet.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                ChatMessageDeleteFragmentSheet.TAG);
        itemPos=position;
        this.messageId=messageId;

    }
    @Override
    public void onItemClick(String item) {
//        Toast.makeText(this,item+"is selected",Toast.LENGTH_SHORT).show();

        switch (item){
            case "delete": updateDeleteData(messageId);

        }
    }

    private void updateDeleteData(String messageId) {
        FirebaseDB.getFirebaseDB().child("ChatMessage").child(messageId).child("isDeleted").setValue("true");
    }

    private void deleteData(String messageId){
        Log.i(TAG, "deleteData: "+messageId);
        Query deleteQuery = FirebaseDB.getFirebaseDB().child("ChatMessage").child(messageId);
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot delData: dataSnapshot.getChildren()){
                    delData.getRef().removeValue();
                    chatMsgAdapter.notifyItemRemoved(itemPos);
                    chatMsgAdapter.notifyDataSetChanged();
                }
                Toast.makeText(ChatActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public static boolean isStringNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
