package com.example.myapplication.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class BindingUtils {

    @BindingAdapter(value = {"app:avatarUrl"})
    public static void setImage(ImageView iv, String avatarUrl) {
        Glide.with(iv.getContext())
//                .downloadOnly()
                .load(avatarUrl)
                .placeholder(R.drawable.ic_placeholder)
                .fallback(R.color.colorAccent)
                .into(iv);
    }
}
