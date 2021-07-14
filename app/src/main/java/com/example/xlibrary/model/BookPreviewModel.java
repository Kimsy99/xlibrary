package com.example.xlibrary.model;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class BookPreviewModel {
    public final int id;
    public final String title;
    public final String category;
//    public final @Nullable Bitmap bookImage;
    public final @Nullable int bookImage;

    public BookPreviewModel(int id,String title, String category,int bookImage) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.bookImage = bookImage;
    }
}
