package com.example.xlibrary.ui.books.bookdetails;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.BookModel;
import com.example.xlibrary.model.UserSession;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseHelper db;
    BookModel bookModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UserSession userSession;

    public BookDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetailFragment newInstance(String param1, String param2) {
        BookDetailFragment fragment = new BookDetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getContext());
        userSession = db.getCurrentUserCreds();
        BookDetailFragmentArgs args = BookDetailFragmentArgs.fromBundle(getArguments());
        System.out.println("args: " + args.getBookId());
        System.out.println("args: " + args.getBookId());
        Cursor cursor = db.getBookDetails(args.getBookId());
        if(cursor.moveToFirst()){
//            bookModel.id = cursor.getInt(0);
//            bookModel.title = cursor.getString(1);
//            bookModel.author = cursor.getString(2);
//            bookModel.category = cursor.getString(3);
//            bookModel.pages = cursor.getInt(4);
//            bookModel.language = cursor.getString(5);
//            bookModel.release_year = cursor.getInt(6);
//            bookModel.description = cursor.getString(7);
//            bookModel.borrowed= cursor.getInt(8);
            byte[] bookImageBytes = cursor.getBlob(9);
            System.out.println("borrowed? " + cursor.getInt(8));
            Bitmap bitmap = bookImageBytes != null ? BitmapFactory.decodeByteArray(bookImageBytes, 0, bookImageBytes.length) : null;
            bookModel = new BookModel(cursor.getInt(0), cursor.getString(1), cursor.getString(3),cursor.getString(2),cursor.getInt(4),cursor.getString(5),cursor.getInt(6),cursor.getString(7),cursor.getInt(8),bitmap, cursor.getInt(10),cursor.getInt(11), cursor.getInt(12));
        }
        cursor.close();
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_book_detail, container, false);
        ImageView bookImage = view.findViewById(R.id.book_image);
        TextView title = view.findViewById(R.id.titleTextView);
        TextView author = view.findViewById(R.id.author);
        TextView page = view.findViewById(R.id.pages);
        TextView language = view.findViewById(R.id.language);
        TextView year = view.findViewById(R.id.year);
        TextView book_desc = view.findViewById(R.id.book_desc);
        Button btn = view.findViewById(R.id.submit_btn);
        System.out.println("bookModel: " + bookModel.return_date);
        bookImage.setImageBitmap(bookModel.bookImage);
        title.setText(bookModel.title);
        author.setText(bookModel.author);
        page.setText(Integer.toString(bookModel.pages));
        language.setText(bookModel.language);
        year.setText(Integer.toString(bookModel.release_year));
        book_desc.setText(bookModel.description);
        //mock user_id = 1;
        int user_id = userSession.uid;
        if(userSession.admin == 1){
            Button btnDelete = view.findViewById(R.id.delete_book_btn);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(v -> {
                boolean res = db.deleteBook(bookModel.id);
                if(res){
                    Toast.makeText(getContext(), "Book had been deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(bookModel.borrowed == 1){
            if(bookModel.borrower_id == user_id && bookModel.return_date == 0){
                btn.setText("RETURN");
                btn.setBackgroundColor(Color.parseColor("#fc3158"));
                btn.setOnClickListener(v -> {
                    db.returnBook(bookModel.book_borrow_id, bookModel.id);
                    Toast.makeText(getContext(), "You had returned successfully. Thank you!", Toast.LENGTH_SHORT).show();
                    btn.setText("BORROW");
                btn.setBackgroundColor(Color.parseColor("#32BC69"));
                });
            }else{
                btn.setText("Unavailable");
                btn.setBackgroundColor(Color.parseColor("#F3F3F3"));
                btn.setTextColor(Color.parseColor("#000000"));
                btn.setEnabled(false);
            }
        }else{
            btn.setText("BORROW");
            btn.setOnClickListener(v ->{
                db.borrowBook(bookModel.id, user_id);
                Toast.makeText(getContext(), "You had successfully borrowed the book! Pick up at XLibrary anytime from now!!", Toast.LENGTH_SHORT).show();
            btn.setText("RETURN");
            btn.setBackgroundColor(Color.parseColor("#fc3158"));
            });
        }
        return view;
    }
}