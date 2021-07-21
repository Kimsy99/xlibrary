package com.example.xlibrary.ui.books;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.BookCardModel;
import com.example.xlibrary.model.BookPreviewModel;
import com.example.xlibrary.ui.books.placeholder.PlaceholderContent;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class BookListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    DatabaseHelper databaseHelper;
    public BookListRecyclerViewAdapter adapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BookListFragment newInstance(int columnCount) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list_list, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.book_list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
//                System.out.println("i am here");
//                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter=new BookListRecyclerViewAdapter(getBookList(), Navigation.findNavController(getActivity(), R.id.nav_host_fragment));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
    public ArrayList<BookCardModel> getBookList(){
        Cursor books = databaseHelper.getAllBooks("");
        ArrayList<BookCardModel> bookList = new ArrayList<>();
        while(books.moveToNext()){
            byte[] issueImageBytes = books.getBlob(3);
            Bitmap bitmap = issueImageBytes != null ? BitmapFactory.decodeByteArray(issueImageBytes, 0, issueImageBytes.length) : null;
            bookList.add(new BookCardModel(
                    books.getInt(0),
                    books.getString(1),
                    books.getString(2),
                    books.getString(4),
                    bitmap
            ));
        }
        books.close();
        return bookList;
    }
    public void searchBookList(String str, Activity context){
        adapter.mValues.clear();
        databaseHelper = new DatabaseHelper(context);
        Cursor books = databaseHelper.getAllBooks(str);
        System.out.println("search books");
        while(books.moveToNext()){
            System.out.println("title: "+ books.getString(1));
            byte[] issueImageBytes = books.getBlob(3);
            Bitmap bitmap = issueImageBytes != null ? BitmapFactory.decodeByteArray(issueImageBytes, 0, issueImageBytes.length) : null;
            adapter.mValues.add(new BookCardModel(
                    books.getInt(0),
                    books.getString(1),
                    books.getString(2),
                    books.getString(4),
                    bitmap
            ));
        }
        adapter.notifyDataSetChanged();
    }
}