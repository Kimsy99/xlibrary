package com.example.xlibrary.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xlibrary.R;
import com.example.xlibrary.model.BookPreviewModel;

import java.util.ArrayList;
import java.util.List;

public class BookPreviewsAdapter extends RecyclerView.Adapter<BookPreviewsAdapter.BookPreviewCardHolder> {
    private ArrayList<BookPreviewModel> bookPreviewCardList;
    private OnCardListener onCardListener;
    private NavController navController;

    public BookPreviewsAdapter(ArrayList<BookPreviewModel> bookPreviewCardList, NavController navController, OnCardListener onCardListener){
        this.bookPreviewCardList = bookPreviewCardList;
        this.onCardListener = onCardListener;
        this.navController = navController;
    }

    @NonNull
    @Override
    public BookPreviewsAdapter.BookPreviewCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_preview, parent, false);
        return new BookPreviewCardHolder(view, onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  BookPreviewsAdapter.BookPreviewCardHolder holder, int position) {
        final BookPreviewModel card = bookPreviewCardList.get(position);
        System.out.println("card: " + card);
        holder.title.setText(card.title);
        holder.category.setText(card.category);
//        holder.image.setImageBitmap(card.bookImage);
        holder.bookCardItemLayout.setOnClickListener(v -> {
           navController.navigate(HomeFragmentDirections.actionNavigationHomeToNavigationBookDetail());
        });
    }

    @Override
    public int getItemCount() {
        return bookPreviewCardList.size();
    }
    static class BookPreviewCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView title, category;
        public ImageView image;
        private LinearLayout bookCardItemLayout;
        OnCardListener onCardListener;

        public BookPreviewCardHolder(@NonNull View itemView, OnCardListener onCardListener)
        {
            super(itemView);
            title = itemView.findViewById(R.id.book_preview_title);
            category = itemView.findViewById(R.id.book_preview_category);
//            image = itemView.findViewById(R.id.card_book_preview_image);
            bookCardItemLayout = itemView.findViewById(R.id.book_card_preview_view);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
//            navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//            onCardListener.onCardClick(getAbsoluteAdapterPosition(), isAnnouncement);
//            navController.navigate(R.id.action_navigation_library_to_navigation_book_detail);
//            ActionFragmentHomeToFragmentCardDetails action = HomeFragmentDirections.actionFragmentHomeToFragmentCardDetails(position, isAnnouncement);
        }
    }
}
interface OnCardListener
{
    void onCardClick(int position, boolean isAnnouncement);
}
