package com.example.xlibrary.model;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class BookCardModel {
    public final int id;
    public final String title;
    public final String category;
    public final String author;
    public final @Nullable
    Bitmap bookImage;
//    public final @Nullable int bookImage;

    public BookCardModel(int id,String title, String category,String author,Bitmap bookImage) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.author = author;
        this.bookImage = bookImage;
    }
}
