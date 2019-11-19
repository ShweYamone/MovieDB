package com.example.moviedb.custom_control;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;

public class MyanProgressDialog {

    private Dialog dialog;

    public MyanProgressDialog(Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        ImageView imageView = dialog.findViewById(R.id.ivImage);
//
//        Glide.with(context)
//                .load(R.drawable.popcorngif)
//                .into(imageView);
    }

    public void showDialog()
    {
        dialog.show();
    }

    public void hideDialog()
    {
        dialog.dismiss();
    }

}
