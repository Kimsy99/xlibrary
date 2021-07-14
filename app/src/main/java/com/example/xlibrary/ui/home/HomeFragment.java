package com.example.xlibrary.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private ArrayList<BookPreviewModel> newBookLists;
    private BookPreviewsAdapter bookPreviewsAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        newBooksRecyclerView = root.findViewById(R.id.home_RecyclerView_books);
        newBookLists  = new ArrayList<>();
        BookPreviewModel temp = new BookPreviewModel(1, "Be the best SdE", "Career", 1 );
        BookPreviewModel temp2 = new BookPreviewModel(2, "Be the best SdE", "Career", 1 );
        BookPreviewModel temp3 = new BookPreviewModel(3, "Be the best SdE", "Career", 1 );
        newBookLists.add(temp);
        newBookLists.add(temp2);
        newBookLists.add(temp3);
        System.out.println(newBookLists);
        loadRecyclerCards(newBooksRecyclerView, newBookLists);
        return root;
    }
    private void loadRecyclerCards(RecyclerView recyclerView, ArrayList<BookPreviewModel> cardModels)
    {
        bookPreviewsAdapter= new BookPreviewsAdapter(cardModels, this);
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

    }
}
