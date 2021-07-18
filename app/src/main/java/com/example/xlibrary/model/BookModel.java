package com.example.xlibrary.model;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class BookModel {
    public int id;
    public String title;
    public String category;
    public String author;
    public int pages;
    public String language;
    public int release_year;
    public String description;
    public int borrowed;
    public @Nullable Bitmap bookImage;
    public int borrower_id;
    public int book_borrow_id;
    public int return_date;
//    public final @Nullable int bookImage;

    public BookModel(int id,String title, String category,String author, int pages, String language, int release_year, String description,int borrowed,Bitmap bookImage, int borrower_id, int book_borrow_id, int return_date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.author = author;
        this.pages = pages;
        this.language = language;
        this.release_year = release_year;
        this.description = description;
        this.borrowed = borrowed;
        this.bookImage = bookImage;
        this.borrower_id = borrower_id;
        this.book_borrow_id = book_borrow_id;
        this.return_date = return_date;
    }
}
