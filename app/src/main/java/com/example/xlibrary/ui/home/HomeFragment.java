package com.example.xlibrary.ui.home;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.BookPreviewModel;
import com.example.xlibrary.model.UserSession;

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
    UserSession userSession;
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

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databaseHelper = new DatabaseHelper(getContext());
        userSession = databaseHelper.getCurrentUserCreds();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView welcomeText = root.findViewById(R.id.welcome_text);
        String welcome = "Welcome back, " + userSession.username;
        System.out.println("welcome: " + welcome);
        welcomeText.setText(welcome);
        TextView bookNo = root.findViewById(R.id.bookNo);
        TextView borrowedBookNo = root.findViewById(R.id.borrowedBookNo);
        TextView currBorrowedBookNo = root.findViewById(R.id.curr_borrowed_book_no);
        bookNo.setText(Integer.toString(getBookCount()));
        borrowedBookNo.setText(Integer.toString(getBorrowedBookCount()));
        currBorrowedBookNo.setText(Integer.toString(getCurrBorrowedBookCount()));
        newBooksRecyclerView = root.findViewById(R.id.home_RecyclerView_books);
        newBorrowedBooksRecyclerView = root.findViewById(R.id.home_RecyclerView_borrow_books);
        getNewBooks();
        getBorrowedBooks();
        System.out.println("newBookLists: "+ newBookLists);
        System.out.println("borrowedBookLists: "+ borrowedBookLists);

//        loadRecyclerCards(newBorrowedBooksRecyclerView, newBookLists);

        //play video
        VideoView videoView = root.findViewById(R.id.promotional_video);
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.lib_promo_vid;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        System.out.println("navController: " + navController);
//        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//        getParentFragmentManager()
        loadRecyclerCards(newBooksRecyclerView, newBookLists);
        loadRecyclerCards(newBorrowedBooksRecyclerView, borrowedBookLists);
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
        Cursor cursor = databaseHelper.getBorrowedBookCount(userSession.uid);
        if(cursor.moveToFirst()){
            System.out.println("borrowed book: " + cursor.getInt(0));
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }private int getCurrBorrowedBookCount(){
        Cursor cursor = databaseHelper.getCurrBorrowedBookCount(userSession.uid);
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

        Cursor borrowedBooks = databaseHelper.getBorrowedBooks(userSession.uid);
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
        System.out.println("navController again: " + navController);
        bookPreviewsAdapter= new BookPreviewsAdapter(cardModels, navController, this);
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
