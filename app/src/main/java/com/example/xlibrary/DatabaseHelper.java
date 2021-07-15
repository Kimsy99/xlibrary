package com.example.xlibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    public DatabaseHelper(@Nullable Context context) {
        super(context, "xlib.db", null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS books;\n");
        sqLiteDatabase.execSQL("CREATE TABLE books\n" +
                "(\n" +
                "    book_id INTEGER primary key autoincrement ,\n" +
                "    book_title TEXT not null ,\n" +
                "    book_author TEXT not null ,\n" +
                "    book_category TEXT not null ,\n" +
                "    book_pages INT not null,\n" +
                "    book_language TEXT default 0 not null,\n" +
                "    book_release_year int not null,\n" +
                "    book_description TEXT not null,\n" +
                "    borrowed int not null,\n" +
                "    created_date int not null,\n" +
                "    book_image BLOB\n" +
                ");");
        String sql = ("insert into books(book_id, book_title, book_author, book_category,book_pages, book_language, book_release_year, book_description, borrowed, created_date, book_image) values\n" +
                "(1,'How to be the best Software Engineer', 'Sheng Yong', 'Careers',2222, 'English', 2018, 'This is the best book we ever had. THis book would guide you', 0, 1626253530, ?),\n" +
                "(2,'Guide for programmer', 'Sheng Yong', 'Self Improvement',2222, 'English', 2018, 'This is the best book we ever had. THis book would guide you', 0,1626253560,?),\n" +
                "(3,'Happy Morter', 'JK Bowling', 'Friction',2222, 'English', 2018, 'This is the best book we ever had. THis book would guide you', 0, 1626253360,?),\n" +
                "(4,'Namoto', 'Satoshi', 'Comic',2222, 'Japanese', 2018, 'This is the best book we ever had. THis book would guide you', 0, 1626253460,?);\n");
//        AndroidUtils.getBlobFromResource(context.getResources(), R.drawable.car1)
        SQLiteStatement insert = sqLiteDatabase.compileStatement(sql);
        insert.bindBlob(1, getBitmapAsByteArray(BitmapFactory.decodeResource(context.getResources(), R.drawable.car1)));
        insert.bindBlob(2, getBitmapAsByteArray(BitmapFactory.decodeResource(context.getResources(), R.drawable.car1)));
        insert.bindBlob(3, getBitmapAsByteArray(BitmapFactory.decodeResource(context.getResources(), R.drawable.car1)));
        insert.bindBlob(4, getBitmapAsByteArray(BitmapFactory.decodeResource(context.getResources(), R.drawable.car1)));
        insert.executeInsert();
        insert.clearBindings();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user;");
        sqLiteDatabase.execSQL("CREATE TABLE user\n" +
                "(\n" +
                "    uid INTEGER primary key autoincrement ,\n" +
                "    username TEXT not null,\n" +
                "    password TEXT not null,\n" +
                "    email TEXT not null\n" +
                ");");
        sqLiteDatabase.execSQL("insert into user(username, password, email) VALUES\n" +
                "('kimsy', 'kimsy', 'kimsy@gmail.com'),\n" +
                "('test', 'test', 'test@gmail.com');");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS borrowed_books;\n");
        sqLiteDatabase.execSQL("CREATE TABLE borrowed_books\n" +
                "(\n" +
                "    borrowed_id INTEGER primary key autoincrement ,\n" +
                "    uid int not null,\n" +
                "    book_id int not null );");
        sqLiteDatabase.execSQL("insert into borrowed_books(uid, book_id) VALUES\n" +
                "(1, 1),\n" +
                "(1, 2),\n" +
                "(2, 3);\n");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
    public Cursor getAllBooks(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT book_id, book_title, book_category, book_image, book_author FROM books", null);
    }
    public Cursor getNewBooks(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT book_id, book_title, book_category, book_image FROM books ORDER BY created_date DESC LIMIT 3;", null);
    }
    public Cursor getBorrowedBooks(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select books.book_id, books.book_title, books.book_category, books.book_image from books left join borrowed_books on books.book_id = borrowed_books.book_id where borrowed_books.uid=1 ORDER BY books.created_date desc;", null);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
        return out.toByteArray();
    }
}
