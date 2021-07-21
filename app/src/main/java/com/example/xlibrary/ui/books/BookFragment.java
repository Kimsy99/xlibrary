package com.example.xlibrary.ui.books;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.model.UserSession;
import com.example.xlibrary.ui.books.addbooks.CreateBookActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    UserSession userSession;
    public BookFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        userSession = databaseHelper.getCurrentUserCreds();
        if(userSession.admin == 1){
            TextView addBooks = view.findViewById(R.id.add_books);
            addBooks.setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.add_books).setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), CreateBookActivity.class);
            startActivity(intent);
        });
        BookListFragment bookListFragment = (BookListFragment)getChildFragmentManager().findFragmentById(R.id.all_book_list);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) view.findViewById(R.id.search_book);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("submitText: " + query);
                bookListFragment.searchBookList(query, getActivity());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("searchText: " + newText);
                if(newText.isEmpty()){
                    bookListFragment.searchBookList("", getActivity());

                }
                return false;
            }
        });
        return view;
    }
    public boolean onQueryTextSubmit(String query) {
// do something on text submit
        return false;
    }
}