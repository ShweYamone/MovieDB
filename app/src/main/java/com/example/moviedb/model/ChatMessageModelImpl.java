package com.example.moviedb.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moviedb.DB.FirebaseDB;
import com.example.moviedb.activities.ChatActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ChatMessageModelImpl implements IChatMessageModel {
    private static final String TAG = "ChatMessageModelImpl";

    private FirebaseRecyclerOptions<ChatMessage> options;
    FirebaseStorage storage;

    @Override
    public FirebaseRecyclerOptions<ChatMessage> getMsgsFromFirebase() {

        Query query = FirebaseDB.getFirebaseDB().child("ChatMessage");
        FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(query, new SnapshotParser<ChatMessage>() {
                            @NonNull
                            @Override
                            public ChatMessage parseSnapshot(@NonNull DataSnapshot snapshot) {


                                    Long accountId = -100l;
                                    if (!(snapshot.child("accountId").getValue() == null)) {
                                        accountId = (Long) (snapshot.child("accountId").getValue());
                                    }
                                        ChatMessage message = new ChatMessage(
                                                snapshot.child("messageText").getValue().toString(),
                                                snapshot.child("messageUser").getValue().toString(),
                                                snapshot.child("messageTime").getValue().toString(),
                                                snapshot.child("isDeleted").getValue().toString(),
                                                accountId,
                                                snapshot.child("photo").getValue().toString()
                                        );
                                        message.setMessageId(snapshot.getKey().toString());
                                        return message;

                            }
                        }).build();
        return options;

    }

    @Override
    public boolean addMsg(Context context,DatabaseReference reference, ChatMessage msg) {
        if (msg == null) {
            return false;
        }
        else {
            try {
                if(msg.isPhoto().equals("0")) {
                    reference.child("ChatMessage").push().setValue(msg);
                }
                else {
                   storage = FirebaseStorage.getInstance();
                   Uri filePath = Uri.parse(msg.getMessageText());
                   msg.setMessageText(filePath.getLastPathSegment());
                   uploadFile(context,filePath);

                   reference.child("ChatMessage").push().setValue(msg);

                }
                return true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    private void uploadFile(Context context,Uri filePath) {
        //if there is a file to upload
        if (filePath != null) {

            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = storage.getReferenceFromUrl("gs://moviedb-6ae09.appspot.com");
            StorageReference riversRef1 = riversRef.child("images/"+filePath.getLastPathSegment());
            riversRef1.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(context, "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

}
