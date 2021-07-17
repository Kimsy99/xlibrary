package com.example.xlibrary.ui.home;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.BookPreviewModel;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, OnCardListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView newBooksRecyclerView;
    private RecyclerView newBorrowedBooksRecyclerView;
    private ArrayList<BookPreviewModel> newBookLists;
    private ArrayList<BookPreviewModel> borrowedBookLists;
    private BookPreviewsAdapter bookPreviewsAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseHelper databaseHelper;
    private NavController navController;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databaseHelper = new DatabaseHelper(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView bookNo = root.findViewById(R.id.bookNo);
        TextView borrowedBookNo = root.findViewById(R.id.borrowedBookNo);
        bookNo.setText(Integer.toString(getBookCount()));
        borrowedBookNo.setText(Integer.toString(getBorrowedBookCount()));
        newBooksRecyclerView = root.findViewById(R.id.home_RecyclerView_books);
        newBorrowedBooksRecyclerView = root.findViewById(R.id.home_RecyclerView_borrow_books);
        getNewBooks();
        getBorrowedBooks();
        System.out.println("newBookLists: "+ newBookLists);
        System.out.println("borrowedBookLists: "+ borrowedBookLists);
        loadRecyclerCards(newBooksRecyclerView, newBookLists);
        loadRecyclerCards(newBorrowedBooksRecyclerView, borrowedBookLists);
//        loadRecyclerCards(newBorrowedBooksRecyclerView, newBookLists);
        return root;
    }
    private int getBookCount(){
        Cursor cursor = databaseHelper.getBookCount();
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }
    private int getBorrowedBookCount(){
        Cursor cursor = databaseHelper.getBorrowedBookCount(1);
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }
    private void getNewBooks(){
        newBookLists  = new ArrayList<>();
        Cursor newBooks = databaseHelper.getNewBooks();
        while(newBooks.moveToNext()){
            byte[] issueImageBytes = newBooks.getBlob(3);
            Bitmap bitmap = issueImageBytes != null ? BitmapFactory.decodeByteArray(issueImageBytes, 0, issueImageBytes.length) : null;
            newBookLists.add(new BookPreviewModel(
                newBooks.getInt(0),
                newBooks.getString(1),
                newBooks.getString(2),
                    bitmap
            ));
        }

    }
    private void getBorrowedBooks(){
        borrowedBookLists  = new ArrayList<>();
        Cursor borrowedBooks = databaseHelper.getBorrowedBooks();
        while(borrowedBooks.moveToNext()){
            byte[] issueImageBytes = borrowedBooks.getBlob(3);
            Bitmap bitmap = issueImageBytes != null ? BitmapFactory.decodeByteArray(issueImageBytes, 0, issueImageBytes.length) : null;
            borrowedBookLists.add(new BookPreviewModel(
                    borrowedBooks.getInt(0),
                    borrowedBooks.getString(1),
                    borrowedBooks.getString(2),
                    bitmap
            ));
        }
    }
    private void loadRecyclerCards(RecyclerView recyclerView, ArrayList<BookPreviewModel> cardModels)
    {
        bookPreviewsAdapter= new BookPreviewsAdapter(cardModels, Navigation.findNavController(getActivity(), R.id.nav_host_fragment), this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bookPreviewsAdapter);

        // to mimic viewpager
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCardClick(int position, boolean isAnnouncement) {
//        HomeFragmentDirections.ActionNavigationHomeToNavigationBookDetail action = HomeFragmentDirections.actionNavigationHomeToNavigationBookDetail();

    }
}
