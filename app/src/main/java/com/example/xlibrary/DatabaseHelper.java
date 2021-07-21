package com.example.xlibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.xlibrary.model.UserSession;

import java.io.ByteArrayOutputStream;
import java.util.Date;

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
                "    borrowed int not null default 0,\n" +
                "    created_date int not null,\n" +
                "    book_image BLOB\n" +
                ");");
        String sql = ("insert into books(book_id, book_title, book_author, book_category,book_pages, book_language, book_release_year, book_description, borrowed, created_date, book_image) values\n" +
                "(1,'How to be the best Software Engineer', 'Sheng Yong', 'Careers',2222, 'English', 2018, 'This is the best book we ever had. THis book would guide you', 1, 1626253530, ?),\n" +
                "(2,'Guide for programmer', 'Sheng Yong', 'Self Improvement',2222, 'English', 2018, 'This is the best book we ever had. THis book would guide you', 1,1626253560,?),\n" +
                "(3,'Happy Morter', 'JK Bowling', 'Friction',2222, 'English', 2018, 'This is the best book we ever had. THis book would guide you', 1, 1626253360,?),\n" +
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
                "    email TEXT unique not null,\n" +
                "    admin INT default 0\n" +
                ");");
        sqLiteDatabase.execSQL("insert into user(username, password, email, admin) VALUES\n" +
                "('kimsy', 'kimsy', 'kimsy@gmail.com', 0),\n" +
                "('admin', 'admin', 'admin@gmail.com', 1);");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS borrowed_books;\n");
        sqLiteDatabase.execSQL("CREATE TABLE borrowed_books\n" +
                "(\n" +
                "    borrowed_id INTEGER primary key autoincrement ,\n" +
                "    uid int not null,\n" +
                "    book_id int not null,\n" +
                "    borrow_date int not null,\n" +
                "    return_date int default 0\n" +
                ");\n");
        sqLiteDatabase.execSQL("insert into borrowed_books(uid, book_id, borrow_date, return_date) VALUES\n" +
                "(1, 1, 1626253560, 0),\n" +
                "(1, 2,1626253560, 0),\n" +
                "(2, 3, 1626253560, 0);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
    public UserSession getCurrentUserCreds()
{
        SharedPreferences sp = context.getSharedPreferences("user", 0);
        return new UserSession(sp.getInt("uid", 0), sp.getString("username", ""), sp.getString("email", ""), sp.getString("password", ""), sp.getInt("admin", 0));
    }
    public Cursor getAllBooks(String str){
        SQLiteDatabase db = getWritableDatabase();
        if(str.isEmpty())
            return db.rawQuery("SELECT book_id, book_title, book_category, book_image, book_author FROM books", null);
        else{
            System.out.println("search here: " + str);
//            return db.rawQuery("SELECT book_id, book_title, book_category, book_image, book_author FROM books WHERE book_title LIKE " + "'&" + str + "%'" +" ORDER BY created_date DESC;", null);
            return db.rawQuery("SELECT book_id, book_title, book_category, book_image, book_author FROM books WHERE book_title LIKE "+"'%" + str +"%'"+" ORDER BY created_date DESC;", null);
            }
    }
    public Cursor getNewBooks(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT book_id, book_title, book_category, book_image FROM books ORDER BY created_date DESC LIMIT 3;", null);
    }
    public Cursor getBorrowedBooks(int uid){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select books.book_id, books.book_title, books.book_category, books.book_image from books left join borrowed_books on books.book_id = borrowed_books.book_id where borrowed_books.return_date = 0 AND borrowed_books.uid= " + uid +" ORDER BY books.created_date desc;", null);
    }
    public Cursor getBookDetails(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT books.book_id, book_title, book_author, book_category, book_pages, book_language, book_release_year, book_description, borrowed, book_image, uid as borrower_id, borrowed_id, return_date  FROM books LEFT JOIN borrowed_books as bb ON books.book_id = bb.book_id WHERE books.book_id = " + id + " ORDER BY borrow_date DESC;", null);
    }
    public Cursor getBookCount(){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT count(*) as bookNo FROM books", null);
    }
    public Cursor getBorrowedBookCount(int uid){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select count(*) from books left join borrowed_books on books.book_id = borrowed_books.book_id where borrowed_books.uid=" + uid +" ORDER BY books.created_date desc;", null);
    }
    public Cursor getCurrBorrowedBookCount(int uid){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select count(*) from books left join borrowed_books on books.book_id = borrowed_books.book_id where borrowed_books.return_date = 0 AND borrowed_books.uid=" + uid +" ORDER BY books.created_date desc;", null);
    }
    public boolean addUser(String username, String email, String password){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO user(username, password, email) values (?, ?, ?)", new Object[] {username, password, email});
        return true;
    }
    public Cursor getUser(String email, String password){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT uid, username, password, email, admin FROM user WHERE email='" + email + "' AND password='" + password + "'", null);
    }
    public boolean editProfile(int uid, String username, String email){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE user SET username = '" + username  + "', email = '" + email + "' WHERE uid = " +uid);
        return true;
    }
    public boolean editPassword(int uid, String password){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE user SET password = '" + password  + "' WHERE uid = " +uid);
        return true;
    }
    public boolean returnBook(int borrow_id, int book_id){
        SQLiteDatabase db = getWritableDatabase();
        long currWhole = new Date().getTime();
        long curr = Long.parseLong((""+currWhole).substring(0,10));
        db.execSQL("UPDATE borrowed_books SET return_date = " + curr + " WHERE borrowed_id=" + borrow_id);
        db.execSQL("UPDATE books SET borrowed = 0 WHERE book_id=" + book_id);
        return true;
    }
    public boolean borrowBook(int book_id, int user_id){
        SQLiteDatabase db = getWritableDatabase();
        long currWhole = new Date().getTime();
        long curr = Long.parseLong((""+currWhole).substring(0,10));
        db.execSQL("INSERT INTO borrowed_books(uid, book_id, borrow_date) VALUES (?,?,?)", new Object[] {user_id, book_id, curr});
        db.execSQL("UPDATE books SET borrowed = 1 WHERE book_id=" + book_id);
        return true;
    }
    public void addBooks(String title, String description, String author, String pages, String language, String release_year, String category, Bitmap bookImage){
        SQLiteDatabase db = getWritableDatabase();
        long currWhole = new Date().getTime();
        long curr = Long.parseLong((""+currWhole).substring(0,10));
        String sql = "INSERT INTO books(book_title, book_author, book_category,book_pages, book_language, book_release_year, book_description, borrowed, created_date, book_image) " +
                "values (?, ?,?, " + Integer.parseInt(pages) +",?," + Integer.parseInt(release_year) +",?,0, "+ curr+",?)";
        SQLiteStatement insert = db.compileStatement(sql);
        insert.bindString(1, title);
        insert.bindString(2, author);
        insert.bindString(3, category);
        insert.bindString(4, language);
        insert.bindString(5, description);
        insert.bindBlob(6, getBitmapAsByteArray(bookImage));
        insert.executeInsert();
        insert.clearBindings();
    }
    public boolean deleteBook(int book_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM books WHERE book_id = " + book_id);
        db.execSQL("DELETE FROM borrowed_books WHERE book_id = " + book_id);
        return true;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
        return out.toByteArray();
    }
}
